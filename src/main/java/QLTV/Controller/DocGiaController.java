/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Controller;

import QLTV.Domain.DocGia;
import QLTV.Domain.Khoa;
import QLTV.Domain.Lop;
import QLTV.Model.DocGiaDAO;
import QLTV.Model.KhoaDAO;
import QLTV.Model.LopDAO;
import QLTV.Views.FormDocGia;
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

public class DocGiaController {

    private final FormDocGia view;
    private final DocGiaDAO dao = new DocGiaDAO();
    private final KhoaDAO khoaDAO = new KhoaDAO();
    private final LopDAO lopDAO = new LopDAO();

    private List<Khoa> listKhoa;
    private List<Lop> listLop;

    public DocGiaController(FormDocGia view) {
        this.view = view;
        initCombos();
        registerEvents();
        loadTable();
        view.setMaDG(dao.taoMaDGMoi());
    }

    private void initCombos() {
                view.getCboKhoa().removeAllItems();
        listKhoa = khoaDAO.findAll();
        for (Khoa k : listKhoa) {
            view.getCboKhoa().addItem(k);
        }

        listLop = lopDAO.findAll();

        reloadLopBySelectedKhoa();
    }

    private void reloadLopBySelectedKhoa() {
        Khoa k = (Khoa) view.getCboKhoa().getSelectedItem();
        view.getCboLop().removeAllItems();
        if (k == null) return;

        for (Lop l : lopDAO.findMaLopByMaKhoa(k.getMaKhoa())) {
            view.getCboLop().addItem(l);
        }
    }

    private void registerEvents() {
        view.getBtnThem().addActionListener(e -> handleInsert());
        view.getBtnSua().addActionListener(e -> handleUpdate());
        view.getBtnXoa().addActionListener(e -> handleDelete());
        view.getBtnLamMoi().addActionListener(e -> {
            view.clearForm();
            initCombos();
            view.setMaDG(dao.taoMaDGMoi());
        });

        view.getBtnNhapFile().addActionListener(e -> importCSVToTable());
        view.getBtnXuatFile().addActionListener(e -> exportTableToCSV());

        view.getBtnSearch().addActionListener(e -> handleSearch());
        view.getTxtSearch().addActionListener(e -> handleSearch());

        view.getCboKhoa().addActionListener(e -> reloadLopBySelectedKhoa());

        view.getTblDG().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) fillFormFromSelectedRow();
        });
    }

    private void loadTable() {
        listKhoa = khoaDAO.findAll();
        listLop = lopDAO.findAll();
        fillTable(dao.findAll());
    }


    private String tenKhoa(String maKhoa) {
        if (maKhoa == null) return "";
        if (listKhoa != null) {
            for (Khoa k : listKhoa) if (maKhoa.equals(k.getMaKhoa())) return k.getTenKhoa();
        }
        return "";
    }

    private String tenLop(String maLop) {
        if (maLop == null) return "";
        if (listLop != null) {
            for (Lop l : listLop) if (maLop.equals(l.getMaLop())) return l.getTenLop();
        }
        return "";
    }


    private void fillTable(List<DocGia> list) {
        DefaultTableModel m = view.getModel();
        m.setRowCount(0);
        for (DocGia dg : list) {
                        String maKhoa = dg.getMaKhoa();
            String maLop = dg.getMaLop();

            m.addRow(new Object[]{
                dg.getMaDG(),
                maKhoa, tenKhoa(maKhoa),
                maLop,  tenLop(maLop),
                dg.getTenDG(),
                dg.getGioiTinh(),
                dg.getDiaChi(),
                dg.getEmail(),
                dg.getSdt()
            });
        }
    }

    private void handleSearch() {
        String key = view.getTxtSearch().getText().trim();
        if (key.isEmpty()) { loadTable(); return; }
        fillTable(dao.search(key));
    }

    private void handleInsert() {
        String ma = view.getMaDG();
        if (ma.isEmpty()) ma = dao.taoMaDGMoi();

        DocGia dg = readForm(ma);
        if (dg == null) return;
        
        String tendg = view.getTenDG();
        if (!tendg.isEmpty() && dao.checkTrungTenDocGia(tendg)) {
            JOptionPane.showMessageDialog(view, "Tên độc giả đã tồn tại!");
            return;
        }

        if (dao.existsEmail(dg.getEmail(), "")) {
            JOptionPane.showMessageDialog(view, "Email đã tồn tại!");
            return;
        }
        if (dao.existsSdt(dg.getSdt(), "")) {
            JOptionPane.showMessageDialog(view, "SĐT đã tồn tại!");
            return;
        }

        int ok = dao.insert(dg);
        if (ok > 0) {
            JOptionPane.showMessageDialog(view, "Thêm độc giả thành công!");
            loadTable();
            view.clearForm();
            initCombos();
            view.setMaDG(dao.taoMaDGMoi());
        } else {
            JOptionPane.showMessageDialog(view, "Thêm thất bại! Có thể trùng mã hoặc dữ liệu không hợp lệ.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleUpdate() {
        String ma = view.getMaDG();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Chọn 1 dòng để sửa!");
            return;
        }

        DocGia dg = readForm(ma);
        if (dg == null) return;
        
        String ten = view.getTenDG();
        if (!ten.isEmpty() && dao.checkTrungTenDocGiaKhacMa(ten, ma)) {
            JOptionPane.showMessageDialog(view, "Tên độc giả đã tồn tại!");
            return;
        }

        if (dao.existsEmail(dg.getEmail(), dg.getMaDG())) {
            JOptionPane.showMessageDialog(view, "Email đã tồn tại ở độc giả khác!");
            return;
        }
        if (dao.existsSdt(dg.getSdt(), dg.getMaDG())) {
            JOptionPane.showMessageDialog(view, "SĐT đã tồn tại ở độc giả khác!");
            return;
        }

        int ok = dao.update(dg);
        if (ok > 0) {
            JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
            loadTable();
        } else {
            JOptionPane.showMessageDialog(view, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDelete() {
        int row = view.getTblDG().getSelectedRow();
        String ma = view.getMaDG();

        if (ma.isEmpty() && row >= 0) ma = String.valueOf(view.getModel().getValueAt(row, 0));
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Chọn 1 dòng hoặc có Mã độc giả để xóa.");
            return;
        }

        int cf = JOptionPane.showConfirmDialog(view, "Xóa độc giả " + ma + " ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (cf != JOptionPane.YES_OPTION) return;

        int ok = dao.delete(ma);
        if (ok > 0) {
            JOptionPane.showMessageDialog(view, "Xóa thành công!");
            loadTable();
            view.clearForm();
            initCombos();
            view.setMaDG(dao.taoMaDGMoi());
        } else {
            JOptionPane.showMessageDialog(view, "Xóa thất bại! Độc giả có thể đang được phiếu mượn tham chiếu.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fillFormFromSelectedRow() {
        int row = view.getTblDG().getSelectedRow();
        if (row < 0) return;

        DefaultTableModel m = view.getModel();

        String maDG = String.valueOf(m.getValueAt(row, 0));
                String maKhoa = String.valueOf(m.getValueAt(row, 1));
        String maLop = String.valueOf(m.getValueAt(row, 3));
        String ten = String.valueOf(m.getValueAt(row, 5));
        String gt = String.valueOf(m.getValueAt(row, 6));
        String dc = String.valueOf(m.getValueAt(row, 7));
        String email = String.valueOf(m.getValueAt(row, 8));
        String sdt = String.valueOf(m.getValueAt(row, 9));

        view.setSelectedKhoa(maKhoa);

        reloadLopBySelectedKhoa();

        view.setSelectedLop(maLop);

         view.setForm(maDG, maKhoa, maLop, ten, gt, dc, email, sdt);
    }
    private void setSelectedKhoa(String maKhoa) {
        for (int i = 0; i < view.getCboKhoa().getItemCount(); i++) {
            if (view.getCboKhoa().getItemAt(i).getMaKhoa().equals(maKhoa)) {
                view.getCboKhoa().setSelectedIndex(i);
                break;
            }
        }
    }

    private void setSelectedLop(String maLop) {
        for (int i = 0; i < view.getCboLop().getItemCount(); i++) {
            if (view.getCboLop().getItemAt(i).getMaLop().equals(maLop)) {
                view.getCboLop().setSelectedIndex(i);
                break;
            }
        }
    }
    private DocGia readForm(String ma) {
        Khoa k = (Khoa) view.getCboKhoa().getSelectedItem();
        Lop l = (Lop) view.getCboLop().getSelectedItem();

        if (k == null || l == null) {
            JOptionPane.showMessageDialog(view, "Chọn Khoa và Lớp!");
            return null;
        }

        String maKhoa = k.getMaKhoa();
        String maLop = l.getMaLop();
        String ten = view.getTenDG();
        String gt = view.getGioiTinh();
        String dc = view.getDiaChi();
        String email = view.getEmail();
        String sdt = view.getSdt();

        if (maKhoa == null || maKhoa.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Chọn Mã khoa!");
            return null;
        }
        if (maLop == null || maLop.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Chọn Mã lớp!");
            return null;
        }
        if (ten.isEmpty() || dc.isEmpty() || email.isEmpty() || sdt.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Không được để trống: Tên/Địa chỉ/Email/SĐT!");
            return null;
        }

        if (!Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$", email)) {
            JOptionPane.showMessageDialog(view, "Email không hợp lệ!");
            return null;
        }
        if (!Pattern.matches("^\\d{10,11}$", sdt)) {
            JOptionPane.showMessageDialog(view, "SĐT không hợp lệ (chỉ số, 10-11 chữ số)!");
            return null;
        }

        return new DocGia(ma, maKhoa, maLop, ten, gt, dc, email, sdt);
    }

    private void importCSVToTable() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Chọn file CSV Độc Giả");
        if (fc.showOpenDialog(view) != JFileChooser.APPROVE_OPTION) return;

        int readCount = 0;
        int insertCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(fc.getSelectedFile()))) {
            String line;

            br.readLine();

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] p = line.split(",", -1);
                if (p.length < 8) continue;

                try {
                    String ma   = p[0].trim();
                    String maK  = p[1].trim();
                    String maL  = p[2].trim();
                    String ten  = p[3].trim();
                    String gt   = p[4].trim();
                    String dc   = p[5].trim();
                    String mail = p[6].trim();
                    String sdt  = p[7].trim();

                    if (ma.isEmpty() || maK.isEmpty() || maL.isEmpty()
                            || ten.isEmpty() || dc.isEmpty()
                            || mail.isEmpty() || sdt.isEmpty()) {
                        continue;
                    }

                    if (!Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$", mail)) {
                        JOptionPane.showMessageDialog(view,
                                "Email không hợp lệ ở dòng:\n" + line,
                                "IMPORT THẤT BẠI", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!Pattern.matches("^\\d{10,11}$", sdt)) {
                        JOptionPane.showMessageDialog(view,
                                "SĐT không hợp lệ ở dòng:\n" + line,
                                "IMPORT THẤT BẠI", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (dao.existsEmail(mail, "")) continue;
                    if (dao.existsSdt(sdt, "")) continue;
                    if (dao.checkTrungTenDocGia(ten)) continue;

                    DocGia dg = new DocGia(ma, maK, maL, ten, gt, dc, mail, sdt);

                    try {
                        int ok = dao.insert(dg);
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
            initCombos();
            view.setMaDG(dao.taoMaDGMoi());

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
        fc.setDialogTitle("Chọn nơi lưu file CSV Độc Giả");
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