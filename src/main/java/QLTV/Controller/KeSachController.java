/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Controller;

import QLTV.Domain.KeSach;
import QLTV.Model.KeSachDAO;
import QLTV.Views.FormKeSach;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Admin
 */
public class KeSachController {

    private final FormKeSach view;
    private final KeSachDAO dao = new KeSachDAO();

    public KeSachController(FormKeSach view) {
        this.view = view;
        registerEvents();
        loadTable();
        view.setMaViTri(dao.taoMaViTriMoi());
    }

    private void registerEvents() {
        view.getBtnThem().addActionListener(e -> handleInsert());
        view.getBtnSua().addActionListener(e -> handleUpdate());
        view.getBtnXoa().addActionListener(e -> handleDelete());

        view.getBtnLamMoi().addActionListener(e -> {
            view.clearForm();
            view.setMaViTri(dao.taoMaViTriMoi());
        });

        view.getBtnSearch().addActionListener(e -> handleSearch());
        view.getTxtSearch().addActionListener(e -> handleSearch());

        view.getBtnNhapFile().addActionListener(e -> importCSVToTable());
        view.getBtnXuatFile().addActionListener(e -> exportTableToCSV());

        view.getTblKe().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) fillFormFromSelectedRow();
        });
    }

    private void loadTable() {
        fillTable(dao.findAll());
    }

    private void fillTable(List<KeSach> list) {
        DefaultTableModel m = view.getModel();
        m.setRowCount(0);
        for (KeSach ks : list) {
            m.addRow(new Object[]{ks.getMaViTri(), ks.getTenKe(), ks.getMoTa()});
        }
    }

    private void handleSearch() {
        String key = view.getTxtSearch().getText().trim();
        if (key.isEmpty()) { loadTable(); return; }
        fillTable(dao.search(key));
    }

    private void fillFormFromSelectedRow() {
        int row = view.getTblKe().getSelectedRow();
        if (row < 0) return;

        DefaultTableModel m = view.getModel();
        view.setForm(
                String.valueOf(m.getValueAt(row, 0)),
                String.valueOf(m.getValueAt(row, 1)),
                String.valueOf(m.getValueAt(row, 2))
        );
    }

    private void handleInsert() {
        String ma = view.getMaViTri();
        if (ma.isEmpty()) ma = dao.taoMaViTriMoi();

        String ten = view.getTenKe();
        String moTa = view.getMoTa();

        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Không được để trống!");
            return;
        }
        
        // trùng tên kệ
        if (dao.existsTenKe(ten, "")) {
            JOptionPane.showMessageDialog(view, "Trùng tên kệ!");
            return;
        }

        int ok = dao.insert(new KeSach(ma, ten, moTa));
        if (ok > 0) {
            JOptionPane.showMessageDialog(view, "Thêm thành công!");
            loadTable();
            view.clearForm();
            view.setMaViTri(dao.taoMaViTriMoi());
        } else {
            JOptionPane.showMessageDialog(view, "Thêm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleUpdate() {
        String ma = view.getMaViTri();
        String ten = view.getTenKe();
        String moTa = view.getMoTa();

        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Chọn 1 dòng để sửa!");
            return;
        }
        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Không được để trống!");
            return;
        }

        if (dao.existsTenKe(ten, ma)) {
            JOptionPane.showMessageDialog(view, "Trùng tên kệ!");
            return;
        }

        int ok = dao.update(new KeSach(ma, ten, moTa));
        if (ok > 0) {
            JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
            loadTable();
        } else {
            JOptionPane.showMessageDialog(view, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDelete() {
        int row = view.getTblKe().getSelectedRow();
        String ma = view.getMaViTri();

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
            view.setMaViTri(dao.taoMaViTriMoi());
        } else {
            JOptionPane.showMessageDialog(view, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void importCSVToTable() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Chọn file CSV Kệ sách");
        if (fc.showOpenDialog(view) != JFileChooser.APPROVE_OPTION) return;

        List<KeSach> dbList = dao.findAll();
        int insert = 0, skip = 0, dup = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(fc.getSelectedFile()))) {
            String line;
            boolean header = true;

            while ((line = br.readLine()) != null) {
                if (header) { header = false; continue; }
                if (line.trim().isEmpty()) continue;

                String[] p = line.split(",", -1);
                if (p.length < 2) { skip++; continue; }

                String ma, ten, moTa;

                if (p.length >= 3) {
                    ma = p[0].trim();
                    ten = p[1].trim();
                    moTa = p[2].trim();
                } else {
                    ma = "";
                    ten = p[0].trim();
                    moTa = p[1].trim();
                }

                if (ten.isEmpty()) { skip++; continue; }
                if (ma.isEmpty()) ma = dao.taoMaViTriMoi();

                boolean same = false, dupMa = false, dupTen = false;

                for (KeSach ks : dbList) {
                    String dbMoTa = ks.getMoTa() == null ? "" : ks.getMoTa();
                    String inMoTa = moTa == null ? "" : moTa;

                    if (ks.getMaViTri().equals(ma) && ks.getTenKe().equals(ten) && dbMoTa.equals(inMoTa)) {
                        same = true; break;
                    }
                    if (ks.getMaViTri().equals(ma)) dupMa = true;
                    if (ks.getTenKe().equals(ten)) dupTen = true;
                }

                if (same) { skip++; continue; }

                if (dupMa || dupTen || dao.existsMaViTri(ma) || dao.existsTenKe(ten, "")) {
                    dup++;
                    JOptionPane.showMessageDialog(view, "Dòng bị trùng: " + ma + " - " + ten);
                    continue;
                }

                KeSach ks = new KeSach(ma, ten, moTa);
                dao.insert(ks);
                dbList.add(ks);
                insert++;
            }

            loadTable();
            view.clearForm();
            view.setMaViTri(dao.taoMaViTriMoi());

            JOptionPane.showMessageDialog(view,
                    "Import xong!\nThêm: " + insert + "\nBỏ qua: " + skip + "\nTrùng: " + dup);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi nhập file!");
        }
    }

    private void exportTableToCSV() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Chọn nơi lưu CSV Kệ sách");
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