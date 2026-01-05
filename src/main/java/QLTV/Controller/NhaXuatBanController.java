/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Controller;

import QLTV.Domain.NhaXuatBan;
import QLTV.Model.NhaXuatBanDAO;
import QLTV.Views.FormNXB;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dinhd
 */
public class NhaXuatBanController {
    private final FormNXB view;
    private final NhaXuatBanDAO dao = new NhaXuatBanDAO();

    public NhaXuatBanController(FormNXB view) {
        this.view = view;
        registerEvents();
        loadTable();
        view.setMaNXB(dao.taoMaNXBMoi());
    }

    private void registerEvents() {
        view.getBtnThem().addActionListener(e -> handleInsert());
        view.getBtnSua().addActionListener(e -> handleUpdate());
        view.getBtnXoa().addActionListener(e -> handleDelete());
        view.getBtnLamMoi().addActionListener(e -> {
            view.clearForm();
            view.setMaNXB(dao.taoMaNXBMoi());
        });

        view.getBtnNhapFile().addActionListener(e -> importCSVToTable());
        view.getBtnXuatFile().addActionListener(e -> exportTableToCSV());

        view.getBtnSearch().addActionListener(e -> handleSearch());
        view.getTxtSearch().addActionListener(e -> handleSearch());

        view.getTblNXB().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) fillFormFromSelectedRow();
        });
    }

    private void loadTable() {
        fillTable(dao.findAll());
    }

    private void fillTable(List<NhaXuatBan> list) {
        DefaultTableModel m = view.getModel();
        m.setRowCount(0);
        for (NhaXuatBan nxb : list) {
            m.addRow(new Object[]{
                    nxb.getMaNXB(),
                    nxb.getTenNXB(),
                    nxb.getDiaChi(),
                    nxb.getSdt(),
                    nxb.getEmail()
            });
        }
    }

    private void handleSearch() {
        String key = view.getTxtSearch().getText().trim();
        if (key.isEmpty()) { loadTable(); return; }
        fillTable(dao.search(key));
    }
    
    private void handleInsert() {
        String ma = view.getMaNXB();
        if (ma.isEmpty()) ma = dao.taoMaNXBMoi();
        
        String TenNXB = view.getTenNXB();
        if (!TenNXB.isEmpty() && dao.checkTrungTenNXB(TenNXB)) {
            JOptionPane.showMessageDialog(view, "Tên nhà xuất bản đã tồn tại!");
            return;
        }

        NhaXuatBan nxb = readForm(ma);
        if (nxb == null) return;

        int ok = dao.insert(nxb);
        if (ok > 0) {
            JOptionPane.showMessageDialog(view, "Thêm NXB thành công!");
            loadTable();
            view.clearForm();
            view.setMaNXB(dao.taoMaNXBMoi());
        } else {
            JOptionPane.showMessageDialog(view, "Thêm thất bại! Có thể trùng mã hoặc dữ liệu không hợp lệ.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleUpdate() {
        String ma = view.getMaNXB();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Chọn 1 dòng để sửa!");
            return;
        }

        NhaXuatBan nxb = readForm(ma);
        if (nxb == null) return;

        int ok = dao.update(nxb);
        if (ok > 0) {
            JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
            loadTable();
        } else {
            JOptionPane.showMessageDialog(view, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDelete() {
        int row = view.getTblNXB().getSelectedRow();
        String ma = view.getMaNXB();

        if (ma.isEmpty() && row >= 0) ma = String.valueOf(view.getModel().getValueAt(row, 0));
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Chọn 1 dòng hoặc có Mã NXB để xóa.");
            return;
        }

        int cf = JOptionPane.showConfirmDialog(view, "Xóa NXB " + ma + " ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (cf != JOptionPane.YES_OPTION) return;

        int ok = dao.delete(ma);
        if (ok > 0) {
            JOptionPane.showMessageDialog(view, "Xóa thành công!");
            loadTable();
            view.clearForm();
            view.setMaNXB(dao.taoMaNXBMoi());
        } else {
            JOptionPane.showMessageDialog(view, "Xóa thất bại! NXB có thể đang được sách tham chiếu.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fillFormFromSelectedRow() {
        int row = view.getTblNXB().getSelectedRow();
        if (row < 0) return;

        DefaultTableModel m = view.getModel();
        view.setForm(
                String.valueOf(m.getValueAt(row, 0)),
                String.valueOf(m.getValueAt(row, 1)),
                String.valueOf(m.getValueAt(row, 2)),
                String.valueOf(m.getValueAt(row, 3)),
                String.valueOf(m.getValueAt(row, 4))
        );
    }

    private NhaXuatBan readForm(String ma) {
        String ten = view.getTenNXB();
        String dc = view.getDiaChi();
        String sdt = view.getSdt();
        String email = view.getEmail();

        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Tên NXB không được để trống!");
            return null;
        }
        return new NhaXuatBan(ma, ten, dc, sdt, email);
    }

    private void importCSVToTable() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Chọn file CSV NXB");

        int choose = fc.showOpenDialog(view);
        if (choose != JFileChooser.APPROVE_OPTION) return;

        int readCount = 0;
        int insertCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(fc.getSelectedFile()))) {
            String line;

            br.readLine();

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] p = line.split(",", -1);
                if (p.length < 5) continue;

                try {
                    String maNXB = p[0].trim();
                    String tenNXB = p[1].trim();
                    String diaChi = p[2].trim();
                    String sdt = p[3].trim();
                    String email = p[4].trim();

                    if (maNXB.isEmpty() || tenNXB.isEmpty()) continue;

                    if (dao.checkTrungTenNXB(tenNXB)) {
                        readCount++; 
                        continue;
                    }

                    NhaXuatBan nxb = new NhaXuatBan(maNXB, tenNXB, diaChi, sdt, email);

                    try {
                        int ok = dao.insert(nxb);
                        if (ok > 0) insertCount++;
                        readCount++;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(view,
                                "Lỗi ở dòng CSV:\n" + line + "\n\nChi tiết:\n" + ex.getMessage(),
                                "IMPORT THẤT BẠI", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                } catch (Exception rowErr) {
                    rowErr.printStackTrace();
                }
            }

            loadTable();
            view.setMaNXB(dao.taoMaNXBMoi());

            JOptionPane.showMessageDialog(view,
                    "Đọc hợp lệ: " + readCount + " dòng\n"
                  + "Đã lưu DB: " + insertCount + " dòng",
                    "OK", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view,
                    "Nhập CSV thất bại!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void exportTableToCSV() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Chọn nơi lưu file CSV NXB");

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

            pw.print('\uFEFF'); // BOM

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
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Xuất CSV thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}