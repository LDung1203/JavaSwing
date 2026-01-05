/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Controller;

import QLTV.Domain.TacGia;
import QLTV.Model.TacGiaDAO;
import QLTV.Views.FormTacGia;
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
public class TacGiaController {

    private final FormTacGia view;
    private final TacGiaDAO dao = new TacGiaDAO();

    public TacGiaController(FormTacGia view) {
        this.view = view;
        registerEvents();
        loadTable();

        view.setMaTG(dao.taoMaTGMoi());
    }

    private void registerEvents() {
        view.getBtnThem().addActionListener(e -> handleInsert());
        view.getBtnSua().addActionListener(e -> handleUpdate());
        view.getBtnXoa().addActionListener(e -> handleDelete());
        view.getBtnLamMoi().addActionListener(e -> {
            view.clearForm();
            view.setMaTG(dao.taoMaTGMoi());
        });

        view.getBtnNhapFile().addActionListener(e -> importCSVToTable());
        view.getBtnXuatFile().addActionListener(e -> exportTableToCSV());

        view.getBtnSearch().addActionListener(e -> handleSearch());
        view.getTxtSearch().addActionListener(e -> handleSearch());

        view.getTblTG().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) fillFormFromSelectedRow();
        });
    }


    private void loadTable() {
        fillTable(dao.findAll());
    }

    private void fillTable(List<TacGia> list) {
        DefaultTableModel m = view.getModel();
        m.setRowCount(0);
        for (TacGia tg : list) {
            m.addRow(new Object[]{
                tg.getMaTG(),
                tg.getTenTG(),
                tg.getNamSinh() == null ? "" : tg.getNamSinh(),
                tg.getGioiTinh(),
                tg.getQuocTich()
            });
        }
    }

    private void handleSearch() {
        String key = view.getTxtSearch().getText().trim();
        if (key.isEmpty()) { loadTable(); return; }
        fillTable(dao.search(key));
    }

    private void handleInsert() {
        String ma = view.getMaTG();
        if (ma.isEmpty()) ma = dao.taoMaTGMoi();

        TacGia tg = readForm(ma);
        if (tg == null) return;

        int ok = dao.insert(tg);
        if (ok > 0) {
            JOptionPane.showMessageDialog(view, "Thêm tác giả thành công!");
            loadTable();
            view.clearForm();
            view.setMaTG(dao.taoMaTGMoi());
        } else {
            JOptionPane.showMessageDialog(view, "Thêm thất bại! Có thể trùng mã hoặc dữ liệu không hợp lệ.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleUpdate() {
        String ma = view.getMaTG();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Chọn 1 dòng để sửa!");
            return;
        }

        TacGia tg = readForm(ma);
        if (tg == null) return;

        int ok = dao.update(tg);
        if (ok > 0) {
            JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
            loadTable();
        } else {
            JOptionPane.showMessageDialog(view, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDelete() {
        int row = view.getTblTG().getSelectedRow();
        String ma = view.getMaTG();
        if (ma.isEmpty() && row >= 0) ma = String.valueOf(view.getModel().getValueAt(row, 0));

        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Chọn 1 dòng hoặc có Mã TG để xóa.");
            return;
        }

        int cf = JOptionPane.showConfirmDialog(view, "Xóa tác giả " + ma + " ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (cf != JOptionPane.YES_OPTION) return;

        int ok = dao.delete(ma);
        if (ok > 0) {
            JOptionPane.showMessageDialog(view, "Xóa thành công!");
            loadTable();

            view.clearForm();
            view.setMaTG(dao.taoMaTGMoi());
        } else {
            JOptionPane.showMessageDialog(view, "Xóa thất bại! Tác giả có thể đang được sách tham chiếu (FK MaTG).",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fillFormFromSelectedRow() {
        int row = view.getTblTG().getSelectedRow();
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

    private TacGia readForm(String ma) {
        String ten = view.getTenTG();
        String namStr = view.getNamSinh();
        String gioiTinh = view.getGioiTinh();
        String quocTich = view.getQuocTich();

        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Tên tác giả không được để trống!");
            return null;
        }
        if (gioiTinh.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Giới tính không được để trống!");
            return null;
        }
        if (quocTich.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Quốc tịch không được để trống!");
            return null;
        }

        Integer namSinh = null;
        if (!namStr.isEmpty()) {
            try {
                namSinh = Integer.parseInt(namStr);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, "Năm sinh phải là số!");
                return null;
            }
        }

        return new TacGia(ma, ten, namSinh, gioiTinh, quocTich);
    }

    private void importCSVToTable() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Chọn file CSV tác giả");

        int choose = fc.showOpenDialog(view);
        if (choose != JFileChooser.APPROVE_OPTION) return;

        try (BufferedReader br = new BufferedReader(new FileReader(fc.getSelectedFile()))) {
            String line;
            boolean firstLine = true;
            int count = 0;
            DefaultTableModel m = view.getModel();

            while ((line = br.readLine()) != null) {
                if (firstLine){
                firstLine = false;
                continue;
            }
                if (line.trim().isEmpty()) continue;

                if (count == 0 && line.toLowerCase().contains("matg")) {
                    continue;
                }

                String[] p = line.split(",", -1);
                if (p.length < 5) continue;

                m.addRow(new Object[]{
                    p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim(), p[4].trim()
                });
                count++;
            }

            JOptionPane.showMessageDialog(view, "Nhập thành công " + count + " dòng (chỉ lên bảng, chưa lưu DB)!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Nhập file thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportTableToCSV() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Chọn nơi lưu file CSV tác giả");

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