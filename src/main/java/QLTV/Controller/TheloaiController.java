/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Controller;

import QLTV.Domain.Theloai;
import QLTV.Model.TheloaiDAO;
import QLTV.Views.FormTheloai;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
/**
 *
 * @author dinhd
 */
public class TheloaiController {
    private final FormTheloai view;
    private final TheloaiDAO dao = new TheloaiDAO();

    public TheloaiController(FormTheloai view) {
        this.view = view;
        registerEvents();
        loadTable();

        view.setMaTL(dao.taoMaTLMoi());
    }

    private void registerEvents() {
        view.getBtnThem().addActionListener(e -> handleInsert());
        view.getBtnSua().addActionListener(e -> handleUpdate());
        view.getBtnXoa().addActionListener(e -> handleDelete());
        view.getBtnLamMoi().addActionListener(e -> {
            view.clearForm();
            view.setMaTL(dao.taoMaTLMoi());
        });

        view.getBtnNhapFile().addActionListener(e -> importCSVToTable());
        view.getBtnXuatFile().addActionListener(e -> exportTableToCSV());

        view.getTblTheLoai().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) fillFormFromSelectedRow();
        });

        view.getBtnSearch().addActionListener(e -> handleSearch());
        view.getTxtSearch().addActionListener(e -> handleSearch());
    }

    private void loadTable() {
        List<Theloai> list = dao.findAll();
        fillTable(list);
    }

    private void fillTable(List<Theloai> list) {
        DefaultTableModel m = view.getModel();
        m.setRowCount(0);
        for (Theloai tl : list) {
            m.addRow(new Object[]{
                    tl.getMaTL(),
                    tl.getTenTL()
            });
        }
    }

    private void handleSearch() {
        String key = view.getTxtSearch().getText().trim();
        if (key.isEmpty()) {
            loadTable();
            return;
        }
        fillTable(dao.search(key));
    }

    private void handleInsert() {
        String ma = view.getMaTL();
        if (ma.isEmpty()) ma = dao.taoMaTLMoi();

        String ten = view.getTenTL();
        
        if (!ten.isEmpty() && dao.checkTrungTenTheLoai(ten)) {
            JOptionPane.showMessageDialog(view, "Tên thể loại đã tồn tại!");
            return;
        }

        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Tên thể loại không được để trống!");
            return;
        }

        Theloai tl = new Theloai(ma, ten);

        int ok = dao.insert(tl);
        if (ok > 0) {
            JOptionPane.showMessageDialog(view, "Thêm thể loại thành công!");
            loadTable();
            view.clearForm();
            view.setMaTL(dao.taoMaTLMoi());
        } else {
            JOptionPane.showMessageDialog(view, "Thêm thất bại! Có thể trùng Mã thể loại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleUpdate() {
        String ma = view.getMaTL();
        String ten = view.getTenTL();

        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Chọn 1 dòng để sửa!");
            return;
        }
        
        if (!ten.isEmpty() && dao.checkTrungTenTheLoaiKhacMa(ten, ma)) {
            JOptionPane.showMessageDialog(view, "Tên thể loại đã tồn tại!");
            return;
        }
        
        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Tên thể loại không được để trống!");
            return;
        }

        Theloai tl = new Theloai(ma, ten);

        int ok = dao.update(tl);
        if (ok > 0) {
            JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
            loadTable();
        } else {
            JOptionPane.showMessageDialog(view, "Cập nhật thất bại! Kiểm tra mã có tồn tại không.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDelete() {
        int row = view.getTblTheLoai().getSelectedRow();
        String ma = view.getMaTL();

        if (ma.isEmpty() && row >= 0) ma = String.valueOf(view.getModel().getValueAt(row, 0));
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Chọn 1 dòng hoặc có Mã thể loại để xóa.");
            return;
        }

        int cf = JOptionPane.showConfirmDialog(view, "Xóa thể loại " + ma + " ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (cf != JOptionPane.YES_OPTION) return;

        int ok = dao.delete(ma);
        if (ok > 0) {
            JOptionPane.showMessageDialog(view, "Xóa thành công!");
            loadTable();
            view.clearForm();
            view.setMaTL(dao.taoMaTLMoi());
        } else {
            JOptionPane.showMessageDialog(view, "Xóa thất bại! Thể loại có thể đang được sách tham chiếu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fillFormFromSelectedRow() {
        int row = view.getTblTheLoai().getSelectedRow();
        if (row < 0) return;

        DefaultTableModel m = view.getModel();
        view.setForm(
                String.valueOf(m.getValueAt(row, 0)),
                String.valueOf(m.getValueAt(row, 1))
        );
    }

    private void importCSVToTable() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Chọn file CSV thể loại");

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
                if (p.length < 2) continue;

                try {
                    String maTL = p[0].trim();
                    String tenTL = p[1].trim();

                    if (maTL.isEmpty() || tenTL.isEmpty()) continue;

                    if (dao.checkTrungTenTheLoai(tenTL)) {
                        readCount++;
                        continue;
                    }

                    Theloai tl = new Theloai(maTL, tenTL);

                    try {
                        int ok = dao.insert(tl);
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
            view.setMaTL(dao.taoMaTLMoi());

            JOptionPane.showMessageDialog(view,
                    "Đọc hợp lệ: " + readCount + " dòng\n"
                  + "Đã lưu DB: " + insertCount + " dòng",
                    "OK", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view,
                    "Nhập CSV thất bại! Kiểm tra CSV.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportTableToCSV() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Chọn nơi lưu file CSV thể loại");
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