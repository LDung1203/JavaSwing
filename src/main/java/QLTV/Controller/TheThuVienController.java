/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Controller;

import QLTV.Domain.TheThuVien;
import QLTV.Model.DocGiaDAO;
import QLTV.Model.TheThuVienDAO;
import QLTV.Views.FormTheThuVien;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */


public class TheThuVienController {

    private final FormTheThuVien view;
    private final TheThuVienDAO dao = new TheThuVienDAO();
    private final DocGiaDAO docGiaDAO = new DocGiaDAO();

    public TheThuVienController(FormTheThuVien view) {
        this.view = view;
        initCombos();
        registerEvents();
        loadTable();
        view.setMaThe(dao.taoMaTheMoi());
    }

    private void initCombos() {
        view.getCboMaDG().removeAllItems();
        List<String> maDGs = docGiaDAO.findAllMaDG();
        for (String ma : maDGs) view.getCboMaDG().addItem(ma);
    }

    private void registerEvents() {
        view.getBtnThem().addActionListener(e -> handleInsert());
        view.getBtnSua().addActionListener(e -> handleUpdate());
        view.getBtnXoa().addActionListener(e -> handleDelete());

        view.getBtnLamMoi().addActionListener(e -> {
            view.clearForm();
            initCombos();
            view.setMaThe(dao.taoMaTheMoi());
        });

        view.getBtnSearch().addActionListener(e -> handleSearch());
        view.getTxtSearch().addActionListener(e -> handleSearch());

        view.getBtnNhapFile().addActionListener(e -> importCSV());
        view.getBtnXuatFile().addActionListener(e -> exportCSV());

        view.getTblThe().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) fillFormFromTable();
        });
    }

    private void loadTable() {
        fillTable(dao.findAll());
        autoUpdateTrangThai();
        fillTable(dao.findAll());
    }

    private void fillTable(List<TheThuVien> list) {
        DefaultTableModel m = view.getModel();
        m.setRowCount(0);
        for (TheThuVien t : list) {
            m.addRow(new Object[]{
                t.getMaThe(),
                t.getMaDG(),
                t.getNgayCap() == null ? "" : t.getNgayCap().toString(),
                t.getNgayHetHan() == null ? "" : t.getNgayHetHan().toString(),
                t.getTrangThai()
            });
        }
    }

    private void handleSearch() {
        String key = view.getTxtSearch().getText().trim();
        if (key.isEmpty()) { loadTable(); return; }
        fillTable(dao.search(key));
    }

    private void fillFormFromTable() {
        int row = view.getTblThe().getSelectedRow();
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

    private void handleInsert() {
        String maThe = view.getMaThe();
        if (maThe.isEmpty()) maThe = dao.taoMaTheMoi();

        TheThuVien t = readForm(maThe);
        if (t == null) return;

        // 1 độc giả 1 thẻ
        if (dao.existsMaDG(t.getMaDG(), "")) {
            JOptionPane.showMessageDialog(view, "Độc giả đã có thẻ!");
            return;
        }

        int ok = dao.insert(t);
        if (ok > 0) {
            JOptionPane.showMessageDialog(view, "Thêm OK!");
            loadTable();
            view.clearForm();
            initCombos();
            view.setMaThe(dao.taoMaTheMoi());
        } else {
            JOptionPane.showMessageDialog(view, "Thêm lỗi!");
        }
    }

    private void handleUpdate() {
        String maThe = view.getMaThe();
        if (maThe.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Chọn 1 dòng để sửa!");
            return;
        }

        TheThuVien t = readForm(maThe);
        if (t == null) return;

        if (dao.existsMaDG(t.getMaDG(), maThe)) {
            JOptionPane.showMessageDialog(view, "Độc giả đã có thẻ khác!");
            return;
        }

        int ok = dao.update(t);
        if (ok > 0) {
            JOptionPane.showMessageDialog(view, "Sửa OK!");
            loadTable();
        } else {
            JOptionPane.showMessageDialog(view, "Sửa lỗi!");
        }
    }

    private void handleDelete() {
        String maThe = view.getMaThe();
        if (maThe.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Chọn 1 dòng để xóa!");
            return;
        }

        int cf = JOptionPane.showConfirmDialog(view, "Xóa thẻ " + maThe + " ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (cf != JOptionPane.YES_OPTION) return;

        int ok = dao.delete(maThe);
        if (ok > 0) {
            JOptionPane.showMessageDialog(view, "Xóa OK!");
            loadTable();
            view.clearForm();
            initCombos();
            view.setMaThe(dao.taoMaTheMoi());
        } else {
            JOptionPane.showMessageDialog(view, "Xóa lỗi!");
        }
    }

    private TheThuVien readForm(String maThe) {
        String maDG = view.getMaDG();
        String ncS = view.getNgayCapText();
        String nhS = view.getNgayHetHanText();

        if (maDG.isEmpty() || ncS.isEmpty() || nhS.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Không được để trống!");
            return null;
        }

        java.sql.Date nc, nh;
        try {
            nc = java.sql.Date.valueOf(LocalDate.parse(ncS.trim()));
            nh = java.sql.Date.valueOf(LocalDate.parse(nhS.trim()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Ngày phải đúng định dạng yyyy-MM-dd");
            return null;
        }

        String tt = autoTrangThai(nh);
        view.getCboTrangThai().setSelectedItem(tt);

        return new TheThuVien(maThe, maDG, nc, nh, tt);
    }

    private String autoTrangThai(java.sql.Date ngayHetHan) {
        LocalDate now = LocalDate.now();
        LocalDate hh = ngayHetHan.toLocalDate();
        return now.isAfter(hh) ? "Hết hiệu lực" : "Còn hiệu lực";
    }

    private void autoUpdateTrangThai() {
        List<TheThuVien> list = dao.findAll();
        for (TheThuVien t : list) {
            if (t.getNgayHetHan() == null) continue;
            String newTT = autoTrangThai(t.getNgayHetHan());
            if (t.getTrangThai() == null || !t.getTrangThai().equals(newTT)) {
                t.setTrangThai(newTT);
                dao.update(t);
            }
        }
    }

    // CSV: MaThe,MaDG,NgayCap,NgayHetHan,TrangThai (TrangThai có cũng dc, auto lại)
    private void importCSV() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Chọn file CSV Thẻ thư viện");
        if (fc.showOpenDialog(view) != JFileChooser.APPROVE_OPTION) return;

        List<TheThuVien> db = dao.findAll();
        int insert = 0, skip = 0, dup = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(fc.getSelectedFile()))) {
            String line;
            boolean header = true;

            while ((line = br.readLine()) != null) {
                if (header) { header = false; continue; }
                if (line.trim().isEmpty()) continue;

                String[] p = line.split(",", -1);
                if (p.length < 4) { skip++; continue; }

                String maThe = p[0].trim();
                String maDG  = p[1].trim();
                String ncS   = p[2].trim();
                String nhS   = p[3].trim();

                if (maThe.isEmpty() || maDG.isEmpty() || ncS.isEmpty() || nhS.isEmpty()) {
                    skip++; continue;
                }

                java.sql.Date nc, nh;
                try {
                    nc = java.sql.Date.valueOf(LocalDate.parse(ncS));
                    nh = java.sql.Date.valueOf(LocalDate.parse(nhS));
                } catch (Exception ex) { skip++; continue; }

                String tt = autoTrangThai(nh);

                boolean same = false, dupMaThe = false, dupMaDG = false;

                for (TheThuVien t : db) {
                    if (t.getMaThe().equals(maThe)
                            && t.getMaDG().equals(maDG)
                            && t.getNgayCap() != null && t.getNgayHetHan() != null
                            && t.getNgayCap().toString().equals(nc.toString())
                            && t.getNgayHetHan().toString().equals(nh.toString())) {
                        same = true; break;
                    }
                    if (t.getMaThe().equals(maThe)) dupMaThe = true;
                    if (t.getMaDG().equals(maDG)) dupMaDG = true;
                }

                if (same) { skip++; continue; }
                if (dupMaThe || dupMaDG) {
                    dup++;
                    JOptionPane.showMessageDialog(view, "Trùng: " + maThe + " / " + maDG);
                    continue;
                }

                TheThuVien tNew = new TheThuVien(maThe, maDG, nc, nh, tt);
                dao.insert(tNew);
                db.add(tNew);
                insert++;
            }

            loadTable();
            view.clearForm();
            initCombos();
            view.setMaThe(dao.taoMaTheMoi());

            JOptionPane.showMessageDialog(view, "Import xong!\nThêm: " + insert + "\nBỏ qua: " + skip + "\nTrùng: " + dup);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi nhập file!");
        }
    }

    private void exportCSV() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Chọn nơi lưu CSV Thẻ thư viện");
        if (fc.showSaveDialog(view) != JFileChooser.APPROVE_OPTION) return;

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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Xuất CSV lỗi!");
        }
    }
}
