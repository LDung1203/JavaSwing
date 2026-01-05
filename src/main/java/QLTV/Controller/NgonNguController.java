/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Controller;

import QLTV.Domain.NgonNgu;
import QLTV.Model.NgonNguDAO;
import QLTV.Views.FormNgonNgu;
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

public class NgonNguController {
    private final FormNgonNgu view;
    private final NgonNguDAO dao = new NgonNguDAO();

    public NgonNguController(FormNgonNgu view) {
        this.view = view;
        registerEvents();
        loadTable();
        view.setMaNN(dao.taoMaNNMoi());
    }

    private void registerEvents() {
        view.getBtnThem().addActionListener(e -> handleInsert());
        view.getBtnSua().addActionListener(e -> handleUpdate());
        view.getBtnXoa().addActionListener(e -> handleDelete());

        view.getBtnLamMoi().addActionListener(e -> {
            view.clearForm();
            view.setMaNN(dao.taoMaNNMoi());
        });

        view.getBtnSearch().addActionListener(e -> handleSearch());
        view.getTxtSearch().addActionListener(e -> handleSearch());

        view.getBtnNhapFile().addActionListener(e -> importCSVToTable());
        view.getBtnXuatFile().addActionListener(e -> exportTableToCSV());

        view.getTblNN().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) fillFormFromSelectedRow();
        });
    }

    private void loadTable() {
        fillTable(dao.findAll());
    }

    private void fillTable(List<NgonNgu> list) {
        DefaultTableModel m = view.getModel();
        m.setRowCount(0);
        for (NgonNgu nn : list) {
            m.addRow(new Object[]{nn.getMaNN(), nn.getTenNN()});
        }
    }

    private void handleSearch() {
        String key = view.getTxtSearch().getText().trim();
        if (key.isEmpty()) { loadTable(); return; }
        fillTable(dao.search(key));
    }

    private void handleInsert() {
        String ma = dao.taoMaNNMoi();
        String ten = view.getTenNN();

        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Tên ngôn ngữ không được để trống!");
            return;
        }

        if (dao.checkTrungTenNN(ten)) {
            JOptionPane.showMessageDialog(view, "Tên ngôn ngữ đã tồn tại!");
            return;
        }

        int ok = dao.insert(new NgonNgu(ma, ten));
        if (ok > 0) {
            JOptionPane.showMessageDialog(view, "Thêm ngôn ngữ thành công!");
            loadTable();
            view.clearForm();
            view.setMaNN(dao.taoMaNNMoi());
        } else {
            JOptionPane.showMessageDialog(view, "Thêm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleUpdate() {
        String ma = view.getMaNN();
        String ten = view.getTenNN();

        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Chọn 1 dòng để sửa!");
            return;
        }
        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Tên ngôn ngữ không được để trống!");
            return;
        }

        if (dao.checkTrungTenNNKhacMa(ten, ma)) {
            JOptionPane.showMessageDialog(view, "Tên ngôn ngữ đã tồn tại!");
            return;
        }

        int ok = dao.update(new NgonNgu(ma, ten));
        if (ok > 0) {
            JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
            loadTable();
        } else {
            JOptionPane.showMessageDialog(view, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDelete() {
        int row = view.getTblNN().getSelectedRow();
        String ma = view.getMaNN();

        if (ma.isEmpty() && row >= 0) ma = String.valueOf(view.getModel().getValueAt(row, 0));
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Chọn 1 dòng để xóa.");
            return;
        }

        int cf = JOptionPane.showConfirmDialog(view, "Xóa ngôn ngữ " + ma + " ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (cf != JOptionPane.YES_OPTION) return;

        int ok = dao.delete(ma);
        if (ok > 0) {
            JOptionPane.showMessageDialog(view, "Xóa thành công!");
            loadTable();
            view.clearForm();
            view.setMaNN(dao.taoMaNNMoi());
        } else {
            JOptionPane.showMessageDialog(view, "Xóa thất bại! Ngôn ngữ có thể đang được sách tham chiếu.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fillFormFromSelectedRow() {
        int row = view.getTblNN().getSelectedRow();
        if (row < 0) return;

        DefaultTableModel m = view.getModel();
        view.setForm(
                String.valueOf(m.getValueAt(row, 0)),
                String.valueOf(m.getValueAt(row, 1))
        );
    }

    // ===== CSV (2 cột) =====
    private void importCSVToTable() {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(view) != JFileChooser.APPROVE_OPTION) return;

        List<NgonNgu> dbList = dao.findAll();
        int insert = 0, skip = 0, dup = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(fc.getSelectedFile()))) {
            String line;
            boolean header = true;

            while ((line = br.readLine()) != null) {
                if (header) { header = false; continue; }
                if (line.trim().isEmpty()) continue;

                String[] p = line.split(",", -1);
                if (p.length < 2) continue;

                String ma = p[0].trim();
                String ten = p[1].trim();
                if (ma.isEmpty() || ten.isEmpty()) continue;

                boolean same = false, dupMa = false, dupTen = false;

                for (NgonNgu nn : dbList) {
                    if (nn.getMaNN().equals(ma) && nn.getTenNN().equals(ten)) {
                        same = true; break;
                    }
                    if (nn.getMaNN().equals(ma)) dupMa = true;
                    if (nn.getTenNN().equals(ten)) dupTen = true;
                }

                if (same) { skip++; continue; }

                if (dupMa || dupTen || dao.checkTrungTenNN(ten)) {
                    dup++;
                    continue;
                }

                dao.insert(new NgonNgu(ma, ten));
                dbList.add(new NgonNgu(ma, ten));
                insert++;
            }

            loadTable();
            view.clearForm();
            view.setMaNN(dao.taoMaNNMoi());

            JOptionPane.showMessageDialog(
                    view,
                    "Import xong!\n"
                  + "Thêm: " + insert + "\n"
                  + "Bỏ qua: " + skip + "\n"
                  + "Trùng: " + dup
            );

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Lỗi nhập file!");
        }
    }

    private void exportTableToCSV() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Chọn nơi lưu file CSV Ngôn ngữ");
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
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Xuất CSV thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
