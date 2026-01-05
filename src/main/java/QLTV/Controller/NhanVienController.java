/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Controller;

import QLTV.Domain.NhanVien;
import QLTV.Model.NhanVienDAO;
import QLTV.Views.FormNhanVien;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */


public class NhanVienController {

    private final FormNhanVien view;
    private final NhanVienDAO dao = new NhanVienDAO();

    public NhanVienController(FormNhanVien view) {
        this.view = view;
        registerEvents();
        loadTable();
        view.setMaNV(dao.taoMaNVMoi());
    }

    private void registerEvents() {
        view.getBtnThem().addActionListener(e -> handleInsert());
        view.getBtnSua().addActionListener(e -> handleUpdate());
        view.getBtnXoa().addActionListener(e -> handleDelete());

        view.getBtnLamMoi().addActionListener(e -> {
            view.clearForm();
            view.setMaNV(dao.taoMaNVMoi());
        });

        view.getBtnSearch().addActionListener(e -> handleSearch());
        view.getTxtSearch().addActionListener(e -> handleSearch());

        view.getBtnNhapFile().addActionListener(e -> importCSVToTable());
        view.getBtnXuatFile().addActionListener(e -> exportTableToCSV());

        view.getTblNV().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) fillFormFromSelectedRow();
        });
    }

    private void loadTable() {
        fillTable(dao.findAll());
    }

    private void fillTable(List<NhanVien> list) {
        DefaultTableModel m = view.getModel();
        m.setRowCount(0);
        for (NhanVien nv : list) {
            m.addRow(new Object[]{
                nv.getMaNV(), nv.getTenNV(), nv.getQueQuan(), nv.getGioiTinh(),
                nv.getVaiTro(), nv.getEmail(), nv.getSdt()
            });
        }
    }

    private void handleSearch() {
        String key = view.getTxtSearch().getText().trim();
        if (key.isEmpty()) { loadTable(); return; }
        fillTable(dao.search(key));
    }

    private void fillFormFromSelectedRow() {
        int row = view.getTblNV().getSelectedRow();
        if (row < 0) return;

        DefaultTableModel m = view.getModel();
        view.setForm(
                String.valueOf(m.getValueAt(row, 0)),
                String.valueOf(m.getValueAt(row, 1)),
                String.valueOf(m.getValueAt(row, 2)),
                String.valueOf(m.getValueAt(row, 3)),
                String.valueOf(m.getValueAt(row, 4)),
                String.valueOf(m.getValueAt(row, 5)),
                String.valueOf(m.getValueAt(row, 6))
        );
    }

    private NhanVien readForm(String ma) {
        String ten = view.getTenNV();
        String que = view.getQueQuan();
        String gt = view.getGioiTinh();
        String vt = view.getVaiTro();
        String email = view.getEmail();
        String sdt = view.getSdt();

        if (ten.isEmpty() || que.isEmpty() || gt.isEmpty() || vt.isEmpty() || email.isEmpty() || sdt.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Không được để trống!");
            return null;
        }

        if (!Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$", email)) {
            JOptionPane.showMessageDialog(view, "Email không hợp lệ!");
            return null;
        }

        if (!Pattern.matches("^\\d{10,11}$", sdt)) {
            JOptionPane.showMessageDialog(view, "SĐT không hợp lệ!");
            return null;
        }

        return new NhanVien(ma, ten, que, gt, vt, email, sdt);
    }

    private void handleInsert() {
        String ma = view.getMaNV();
        if (ma.isEmpty()) ma = dao.taoMaNVMoi();

        NhanVien nv = readForm(ma);
        if (nv == null) return;
        
        String ten = view.getTenNV();
        if (!ten.isEmpty() && dao.checkTrungTenNV(ten)) {
            JOptionPane.showMessageDialog(view, "Tên nhân viên đã tồn tại!");
            return;
        }

        if (dao.existsEmail(nv.getEmail(), "")) {
            JOptionPane.showMessageDialog(view, "Email đã tồn tại!");
            return;
        }
        if (dao.existsSdt(nv.getSdt(), "")) {
            JOptionPane.showMessageDialog(view, "SĐT đã tồn tại!");
            return;
        }

        int ok = dao.insert(nv);
        if (ok > 0) {
            JOptionPane.showMessageDialog(view, "Thêm thành công!");
            loadTable();
            view.clearForm();
            view.setMaNV(dao.taoMaNVMoi());
        } else {
            JOptionPane.showMessageDialog(view, "Thêm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleUpdate() {
        String ma = view.getMaNV();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Chọn 1 dòng để sửa!");
            return;
        }
        
        String ten = view.getTenNV();
        if (!ten.isEmpty() && dao.checkTrungTenNVKhacMa(ten, ma)) {
            JOptionPane.showMessageDialog(view, "Tên nhân viên đã tồn tại!");
            return;
        }

        NhanVien nv = readForm(ma);
        if (nv == null) return;

        if (dao.existsEmail(nv.getEmail(), ma)) {
            JOptionPane.showMessageDialog(view, "Email đã tồn tại!");
            return;
        }
        if (dao.existsSdt(nv.getSdt(), ma)) {
            JOptionPane.showMessageDialog(view, "SĐT đã tồn tại!");
            return;
        }

        int ok = dao.update(nv);
        if (ok > 0) {
            JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
            loadTable();
        } else {
            JOptionPane.showMessageDialog(view, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDelete() {
        int row = view.getTblNV().getSelectedRow();
        String ma = view.getMaNV();

        if (ma.isEmpty() && row >= 0) ma = String.valueOf(view.getModel().getValueAt(row, 0));
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Chọn 1 dòng để xóa.");
            return;
        }

        int cf = JOptionPane.showConfirmDialog(view, "Xóa " + ma + " ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (cf != JOptionPane.YES_OPTION) return;

        int ok = dao.delete(ma);
        if (ok > 0) {
            JOptionPane.showMessageDialog(view, "Xóa thành công!");
            loadTable();
            view.clearForm();
            view.setMaNV(dao.taoMaNVMoi());
        } else {
            JOptionPane.showMessageDialog(view, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // CSV: MaNV,TenNV,QueQuan,GioiTinh,VaiTro,Email,Sdt
    private void importCSVToTable() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Chọn file CSV Nhân viên");
        if (fc.showOpenDialog(view) != JFileChooser.APPROVE_OPTION) return;

        List<NhanVien> dbList = dao.findAll();
        int insert = 0, skip = 0, dup = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(fc.getSelectedFile()))) {
            String line;
            boolean header = true;

            while ((line = br.readLine()) != null) {
                if (header) { header = false; continue; }
                if (line.trim().isEmpty()) continue;

                String[] p = line.split(",", -1);
                if (p.length < 7) continue;

                String ma = p[0].trim();
                String ten = p[1].trim();
                String que = p[2].trim();
                String gt = p[3].trim();
                String vt = p[4].trim();
                String email = p[5].trim();
                String sdt = p[6].trim();

                if (ma.isEmpty() || ten.isEmpty() || que.isEmpty() || gt.isEmpty() || vt.isEmpty()
                        || email.isEmpty() || sdt.isEmpty()) {
                    skip++;
                    continue;
                }

                if (!Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$", email)) { skip++; continue; }
                if (!Pattern.matches("^\\d{10,11}$", sdt)) { skip++; continue; }

                boolean same = false, dupMa = false;

                for (NhanVien nv : dbList) {
                    if (nv.getMaNV().equals(ma) && nv.getEmail().equals(email) && nv.getSdt().equals(sdt)) {
                        same = true; break; 
                    }
                    if (nv.getMaNV().equals(ma)) dupMa = true;
                }

                if (same) { skip++; continue; }

                if (dupMa || dao.existsEmail(email, "") || dao.existsSdt(sdt, "")) {
                    dup++;
                    continue;
                }

                NhanVien nv = new NhanVien(ma, ten, que, gt, vt, email, sdt);
                dao.insert(nv);
                dbList.add(nv);
                insert++;
            }

            loadTable();
            view.clearForm();
            view.setMaNV(dao.taoMaNVMoi());

            JOptionPane.showMessageDialog(view,
                    "Import xong!\nThêm: " + insert + "\nBỏ qua: " + skip + "\nTrùng: " + dup);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi nhập file!");
        }
    }

    private void exportTableToCSV() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Chọn nơi lưu CSV Nhân viên");
        int choose = fc.showSaveDialog(view);
        if (choose != JFileChooser.APPROVE_OPTION) return;

        java.io.File file = fc.getSelectedFile();
        if (!file.getName().toLowerCase().endsWith(".csv")) {
            file = new java.io.File(file.getAbsolutePath() + ".csv");
        }

        try (java.io.OutputStream os = new java.io.FileOutputStream(file);
             java.io.OutputStreamWriter osw = new java.io.OutputStreamWriter(os, java.nio.charset.StandardCharsets.UTF_8);
             java.io.BufferedWriter bw = new java.io.BufferedWriter(osw);
             java.io.PrintWriter pw = new java.io.PrintWriter(bw)) {

            pw.print('\uFEFF');
            DefaultTableModel m = view.getModel();

            for (int c = 0; c < m.getColumnCount(); c++) {
                pw.print(m.getColumnName(c));
                if (c < m.getColumnCount() - 1) pw.print(",");
            }
            pw.println();

            for (int r = 0; r < m.getRowCount(); r++) {
                for (int c = 0; c < m.getColumnCount(); c++) {
                    Object val = m.getValueAt(r, c);
                    String s = (val == null) ? "" : String.valueOf(val);

                    if (s.contains(",") || s.contains("\"")) {
                        s = s.replace("\"", "\"\"");
                        s = "\"" + s + "\"";
                    }

                    pw.print(s);
                    if (c < m.getColumnCount() - 1) pw.print(",");
                }
                pw.println();
            }

            JOptionPane.showMessageDialog(view, "Xuất CSV thành công!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Xuất CSV thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}