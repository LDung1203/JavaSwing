/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Controller;

import QLTV.Domain.Khoa;
import QLTV.Model.KhoaDAO;
import QLTV.Views.FormKhoa;
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

public class KhoaController {
    private final FormKhoa view;
    private final KhoaDAO dao = new KhoaDAO();

    public KhoaController(FormKhoa view) {
        this.view = view;
        registerEvents();
        loadTable();
        view.setMaKhoa(dao.taoMaKhoaMoi());
    }

    private void registerEvents() {
        view.getBtnThem().addActionListener(e -> handleInsert());
        view.getBtnSua().addActionListener(e -> handleUpdate());
        view.getBtnXoa().addActionListener(e -> handleDelete());

        view.getBtnLamMoi().addActionListener(e -> {
            view.clearForm();
            view.setMaKhoa(dao.taoMaKhoaMoi());
        });

        view.getBtnSearch().addActionListener(e -> handleSearch());
        view.getTxtSearch().addActionListener(e -> handleSearch());

        view.getBtnNhapFile().addActionListener(e -> importCSVToTable());
        view.getBtnXuatFile().addActionListener(e -> exportTableToCSV());

        view.getTblKhoa().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) fillFormFromSelectedRow();
        });
    }

    private void loadTable() {
        fillTable(dao.findAll());
    }

    private void fillTable(List<Khoa> list) {
        DefaultTableModel m = view.getModel();
        m.setRowCount(0);
        for (Khoa k : list) {
            m.addRow(new Object[]{k.getMaKhoa(), k.getTenKhoa()});
        }
    }

    private void handleSearch() {
        String key = view.getTxtSearch().getText().trim();
        if (key.isEmpty()) { loadTable(); return; }
        fillTable(dao.search(key));
    }

    private void handleInsert() {
        String ma = dao.taoMaKhoaMoi();
        String ten = view.getTenKhoa();

        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Tên khoa không được để trống!");
            return;
        }

        if (dao.checkTrungTenKhoa(ten)) {
            JOptionPane.showMessageDialog(view, "Tên khoa đã tồn tại!");
            return;
        }

        int ok = dao.insert(new Khoa(ma, ten));
        if (ok > 0) {
            JOptionPane.showMessageDialog(view, "Thêm khoa thành công!");
            loadTable();
            view.clearForm();
            view.setMaKhoa(dao.taoMaKhoaMoi());
        } else {
            JOptionPane.showMessageDialog(view, "Thêm thất bại! Có thể trùng mã.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleUpdate() {
       String ma = view.getMaKhoa();
       String ten = view.getTenKhoa();

       if (ma.isEmpty()) {
           JOptionPane.showMessageDialog(view, "Chọn 1 dòng để sửa!");
           return;
       }
       if (ten.isEmpty()) {
           JOptionPane.showMessageDialog(view, "Tên khoa không được để trống!");
           return;
       }

       if (dao.checkTrungTenKhoaKhacMa(ten, ma)) {
           JOptionPane.showMessageDialog(view, "Tên khoa đã tồn tại!");
           return;
       }

       int ok = dao.update(new Khoa(ma, ten));
       if (ok > 0) {
           JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
           loadTable();
       } else {
           JOptionPane.showMessageDialog(view, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
       }
   }

    private void handleDelete() {
        int row = view.getTblKhoa().getSelectedRow();
        String ma = view.getMaKhoa();

        if (ma.isEmpty() && row >= 0) ma = String.valueOf(view.getModel().getValueAt(row, 0));
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Chọn 1 dòng để xóa.");
            return;
        }

        int cf = JOptionPane.showConfirmDialog(view, "Xóa khoa " + ma + " ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (cf != JOptionPane.YES_OPTION) return;

        int ok = dao.delete(ma);
        if (ok > 0) {
            JOptionPane.showMessageDialog(view, "Xóa thành công!");
            loadTable();
            view.clearForm();
            view.setMaKhoa(dao.taoMaKhoaMoi());
        } else {
            JOptionPane.showMessageDialog(view, "Xóa thất bại! Khoa có thể đang được lớp tham chiếu.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fillFormFromSelectedRow() {
        int row = view.getTblKhoa().getSelectedRow();
        if (row < 0) return;

        DefaultTableModel m = view.getModel();
        view.setForm(
                String.valueOf(m.getValueAt(row, 0)),
                String.valueOf(m.getValueAt(row, 1))
        );
    }

    private void importCSVToTable() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Chọn file CSV Khoa");
        if (fc.showOpenDialog(view) != JFileChooser.APPROVE_OPTION) return;

        int readCount = 0;
        int insertCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(fc.getSelectedFile()))) {
            String line;

            br.readLine();

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] p = line.split(",", -1);
                if (p.length < 2) continue;

                try {
                    String ma = p[0].trim();
                    String ten = p[1].trim();

                    if (ma.isEmpty() || ten.isEmpty()) continue;

                    if (dao.checkTrungTenKhoa(ten)) {
                        readCount++;
                        continue;
                    }

                    Khoa k = new Khoa(ma, ten);

                    try {
                        int ok = dao.insert(k);
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
            view.clearForm();
            view.setMaKhoa(dao.taoMaKhoaMoi());

            JOptionPane.showMessageDialog(view,
                    "Đọc hợp lệ: " + readCount + " dòng\n"
                  + "Đã lưu DB: " + insertCount + " dòng",
                    "OK", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Lỗi nhập file!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void exportTableToCSV() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Chọn nơi lưu file CSV Khoa");
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