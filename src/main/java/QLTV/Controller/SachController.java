package QLTV.Controller;

import QLTV.Domain.KeSach;
import QLTV.Domain.NgonNgu;
import QLTV.Domain.NhaXuatBan;
import QLTV.Domain.Sach;
import QLTV.Domain.TacGia;
import QLTV.Domain.Theloai;
import QLTV.Model.SachDAO;
import QLTV.Views.FormSach;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class SachController {

    private final FormSach view;
    private final SachDAO dao = new SachDAO();

    // cache list để đổi mã -> tên nhanh (đổ lên table)
    private List<TacGia> listTG;
    private List<Theloai> listTL;
    private List<NhaXuatBan> listNXB;
    private List<NgonNgu> listNN;
    private List<KeSach> listVT;

    public SachController(FormSach view) {
        this.view = view;

        initTacGiaCombo();
        initTheLoaiCombo();
        initNXBCombo();
        initNgonNguCombo();
        initViTriCombo();

        registerEvents();
        loadTable();

        view.setMaSach(dao.taoMaSachMoi());
    }

    private void registerEvents() {
        view.getBtnThem().addActionListener(e -> handleInsert());
        view.getBtnSua().addActionListener(e -> handleUpdate());
        view.getBtnXoa().addActionListener(e -> handleDelete());
        view.getBtnLamMoi().addActionListener(e -> {
            view.clearForm();
            view.setMaSach(dao.taoMaSachMoi());
        });

        view.getBtnNhapFile().addActionListener(e -> importCSVToTable());
        view.getBtnXuatFile().addActionListener(e -> exportTableToCSV());

        view.getTblSach().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) fillFormFromSelectedRow();
        });

        try {
            view.getBtnSearch().addActionListener(e -> handleSearch());
            view.getTxtSearch().addActionListener(e -> handleSearch());
        } catch (Exception ignored) {}
    }

    private void initTacGiaCombo() {
        view.getCboTacGia().removeAllItems();
        listTG = dao.findAllTacGiaForSach();
        for (TacGia tg : listTG) view.getCboTacGia().addItem(tg);
    }

    private void initTheLoaiCombo() {
        view.getCboTheLoai().removeAllItems();
        listTL = dao.findAllTheLoaiForSach();
        for (Theloai tl : listTL) view.getCboTheLoai().addItem(tl);
    }

    private void initNXBCombo() {
        view.getCboNXB().removeAllItems();
        listNXB = dao.findAllNhaXuatBanForSach();
        for (NhaXuatBan nxb : listNXB) view.getCboNXB().addItem(nxb);
    }

    private void initNgonNguCombo() {
        view.getCboNgonNgu().removeAllItems();
        listNN = dao.findAllNgonNguForSach();
        for (NgonNgu nn : listNN) view.getCboNgonNgu().addItem(nn);
    }

    private void initViTriCombo() {
        view.getCboViTri().removeAllItems();
        listVT = dao.findAllViTriForSach();
        for (KeSach ks : listVT) view.getCboViTri().addItem(ks);
    }

    private void loadTable() {
        fillTable(dao.findAll());
    }

    private String tenTacGia(String maTG) {
        if (maTG == null) return "";
        for (TacGia tg : listTG) if (maTG.equals(tg.getMaTG())) return tg.getTenTG();
        return "";
    }

    private String tenTheLoai(String maTL) {
        if (maTL == null) return "";
        for (Theloai tl : listTL) if (maTL.equals(tl.getMaTL())) return tl.getTenTL();
        return "";
    }

    private String tenNXB(String maNXB) {
        if (maNXB == null) return "";
        for (NhaXuatBan nxb : listNXB) if (maNXB.equals(nxb.getMaNXB())) return nxb.getTenNXB();
        return "";
    }

    private String tenNgonNgu(String maNN) {
        if (maNN == null) return "";
        for (NgonNgu nn : listNN) if (maNN.equals(nn.getMaNN())) return nn.getTenNN();
        return "";
    }

    private String tenViTri(String maVT) {
        if (maVT == null) return "";
        for (KeSach ks : listVT) if (maVT.equals(ks.getMaViTri())) return ks.getTenKe();
        return "";
    }

    private void fillTable(List<Sach> list) {
        DefaultTableModel m = view.getModel();
        m.setRowCount(0);

        for (Sach s : list) {
            String maTG = s.getMatg();
            String maTL = s.getMatl();
            String maNXB = s.getManxb();
            String maNN = s.getMann();
            String maVT = s.getMavitri();

            m.addRow(new Object[] {
                s.getMasach(),
                s.getTensach(),

                maTG,  tenTacGia(maTG),
                maTL,  tenTheLoai(maTL),
                maNXB, tenNXB(maNXB),
                maNN,  tenNgonNgu(maNN),
                maVT,  tenViTri(maVT),

                s.getNamxb(),
                (s.getSoluong() == null ? "" : s.getSoluong())
            });
        }
    }

    private void handleSearch() {
        String key = view.getTxtSearch().getText().trim();
        if (key.isEmpty()) { loadTable(); return; }
        fillTable(dao.search(key));
    }

    private void handleInsert() {
        String ma = view.getMaSach();
        if (ma.isEmpty()) ma = dao.taoMaSachMoi();

        String tenSach = view.getTenSach();
        if (!tenSach.isEmpty() && dao.checkTrungTenSach(tenSach)) {
            JOptionPane.showMessageDialog(view, "Tên sách đã tồn tại!");
            return;
        }

        Sach s = readFormToSach(true);
        if (s == null) return;

        int ok = dao.insert(s);
        if (ok > 0) {
            JOptionPane.showMessageDialog(view, "Thêm sách thành công!");
            loadTable();
            view.clearForm();
            view.setMaSach(dao.taoMaSachMoi());
        } else {
            JOptionPane.showMessageDialog(view, "Thêm thất bại! Có thể trùng Mã sách hoặc sai mã FK.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleUpdate() {
        String ma = view.getMaSach();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Chọn 1 dòng để sửa!");
            return;
        }

        String tenSach = view.getTenSach();
        if (!tenSach.isEmpty() && dao.checkTrungTenSachKhacMa(tenSach, ma)) {
            JOptionPane.showMessageDialog(view, "Tên sách đã tồn tại!");
            return;
        }

        Sach s = readFormToSach(true);
        if (s == null) return;

        int ok = dao.update(s);
        if (ok > 0) {
            JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
            loadTable();
        } else {
            JOptionPane.showMessageDialog(view, "Cập nhật thất bại!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDelete() {
        int row = view.getTblSach().getSelectedRow();
        String ma = view.getMaSach();

        if (ma.isEmpty() && row >= 0) ma = String.valueOf(view.getModel().getValueAt(row, 0));
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Chọn 1 dòng hoặc nhập Mã sách để xóa.");
            return;
        }

        int cf = JOptionPane.showConfirmDialog(view, "Xóa sách " + ma + " ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (cf != JOptionPane.YES_OPTION) return;

        int ok = dao.delete(ma);
        if (ok > 0) {
            JOptionPane.showMessageDialog(view, "Xóa thành công!");
            loadTable();
            view.clearForm();
            view.setMaSach(dao.taoMaSachMoi());
        } else {
            JOptionPane.showMessageDialog(view, "Xóa thất bại! Sách có thể đang bị tham chiếu.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setSelectedTacGia(String maTG) {
        JComboBox<TacGia> cbo = view.getCboTacGia();
        for (int i = 0; i < cbo.getItemCount(); i++) {
            if (cbo.getItemAt(i).getMaTG().equals(maTG)) { cbo.setSelectedIndex(i); break; }
        }
    }

    private void setSelectedTheLoai(String maTL) {
        JComboBox<Theloai> cbo = view.getCboTheLoai();
        for (int i = 0; i < cbo.getItemCount(); i++) {
            if (cbo.getItemAt(i).getMaTL().equals(maTL)) { cbo.setSelectedIndex(i); break; }
        }
    }

    private void setSelectedNhaXuatBan(String maNXB) {
        JComboBox<NhaXuatBan> cbo = view.getCboNXB();
        for (int i = 0; i < cbo.getItemCount(); i++) {
            if (cbo.getItemAt(i).getMaNXB().equals(maNXB)) { cbo.setSelectedIndex(i); break; }
        }
    }

    private void setSelectedNgonNgu(String maNN) {
        JComboBox<NgonNgu> cbo = view.getCboNgonNgu();
        for (int i = 0; i < cbo.getItemCount(); i++) {
            if (cbo.getItemAt(i).getMaNN().equals(maNN)) { cbo.setSelectedIndex(i); break; }
        }
    }

    private void setSelectedViTri(String maVT) {
        JComboBox<KeSach> cbo = view.getCboViTri();
        for (int i = 0; i < cbo.getItemCount(); i++) {
            if (cbo.getItemAt(i).getMaViTri().equals(maVT)) { cbo.setSelectedIndex(i); break; }
        }
    }

    private void fillFormFromSelectedRow() {
        int row = view.getTblSach().getSelectedRow();
        if (row < 0) return;

        DefaultTableModel m = view.getModel();

        // đọc theo model index (cột mã đang ẩn nhưng vẫn nằm trong model)
        String maSach = String.valueOf(m.getValueAt(row, 0));
        String tenSach = String.valueOf(m.getValueAt(row, 1));

        String maTG = String.valueOf(m.getValueAt(row, 2));
        String maTL = String.valueOf(m.getValueAt(row, 4));
        String maNXB = String.valueOf(m.getValueAt(row, 6));
        String maNN = String.valueOf(m.getValueAt(row, 8));
        String maVT = String.valueOf(m.getValueAt(row, 10));

        String namXB = String.valueOf(m.getValueAt(row, 12));
        String soLuong = String.valueOf(m.getValueAt(row, 13));

        view.setMaSach(maSach);
        view.setTenSach(tenSach);
        view.setNamXB(namXB);
        view.setSoLuong(soLuong);

        setSelectedTacGia(maTG);
        setSelectedTheLoai(maTL);
        setSelectedNhaXuatBan(maNXB);
        setSelectedNgonNgu(maNN);
        setSelectedViTri(maVT);
    }

    private Sach readFormToSach(boolean requireMaSach) {
        String ma = view.getMaSach();
        String ten = view.getTenSach();

        String maTG = view.getTacGia();
        String maTL = view.getTheLoai();
        String maNXB = view.getNXB();
        String maNN = view.getMaNN();
        String maVT = view.getMaViTri();

        String namStr = view.getNamXB();
        String slStr = view.getSoLuong();

        if (requireMaSach && ma.isEmpty()) { JOptionPane.showMessageDialog(view, "Mã sách không được để trống!"); return null; }
        if (ten.isEmpty()) { JOptionPane.showMessageDialog(view, "Tên sách không được để trống!"); return null; }
        if (maTG.isEmpty() || maTL.isEmpty() || maNXB.isEmpty() || maNN.isEmpty() || maVT.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Tác giả/Thể loại/NXB/Ngôn ngữ/Vị trí không được để trống!");
            return null;
        }

        int nam;
        try { nam = Integer.parseInt(namStr); }
        catch (Exception e) { JOptionPane.showMessageDialog(view, "Năm XB phải là số!"); return null; }

        Integer soLuong = null;
        if (!slStr.isEmpty()) {
            try { soLuong = Integer.valueOf(slStr); }
            catch (NumberFormatException e) { JOptionPane.showMessageDialog(view, "Số lượng phải là số!"); return null; }
        }

        return new Sach(ma, maTG, maNXB, maTL, ten, nam, soLuong, maNN, maVT);
    }

    // CSV giữ như bạn đang dùng (nếu file CSV cũ chưa có MaNN/MaVT thì để default)
    private void importCSVToTable() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Chọn file CSV danh sách sách");
        int choose = fc.showOpenDialog(view);
        if (choose != JFileChooser.APPROVE_OPTION) return;

        int readCount = 0, insertCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(fc.getSelectedFile()))) {
            String line;
            br.readLine(); // header

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(",", -1);
                if (p.length < 7) continue;

                String maSach = p[0].trim();
                String tenSach = p[1].trim();
                String maTG = p[2].trim();
                String maTL = p[3].trim();
                String maNXB = p[4].trim();
                int namXB = Integer.parseInt(p[5].trim());

                Integer soLuong = p[6].trim().isEmpty() ? null : Integer.parseInt(p[6].trim());

                // nếu CSV có thêm cột MaNN, MaViTri thì lấy, không thì default theo combo đầu tiên
                String maNN = (p.length >= 8 && !p[7].trim().isEmpty()) ? p[7].trim() : view.getMaNN();
                String maVT = (p.length >= 9 && !p[8].trim().isEmpty()) ? p[8].trim() : view.getMaViTri();

                Sach s = new Sach(maSach, maTG, maNXB, maTL, tenSach, namXB, soLuong, maNN, maVT);

                int ok = dao.insert(s);
                if (ok > 0) insertCount++;
                readCount++;
            }

            loadTable();
            view.setMaSach(dao.taoMaSachMoi());

            JOptionPane.showMessageDialog(view,
                    "Đọc hợp lệ: " + readCount + " dòng\n" +
                    "Đã lưu DB: " + insertCount + " dòng",
                    "OK", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Nhập CSV thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportTableToCSV() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Chọn nơi lưu file CSV");
        int choose = fc.showSaveDialog(view);
        if (choose != JFileChooser.APPROVE_OPTION) return;

        java.io.File file = fc.getSelectedFile();
        if (!file.getName().toLowerCase().endsWith(".csv")) {
            file = new java.io.File(file.getAbsolutePath() + ".csv");
        }

        try (java.io.PrintWriter pw = new java.io.PrintWriter(
                new java.io.BufferedWriter(
                        new java.io.OutputStreamWriter(
                                new java.io.FileOutputStream(file),
                                java.nio.charset.StandardCharsets.UTF_8
                        )
                )
        )) {
            pw.print('\uFEFF');

            DefaultTableModel m = view.getModel();

            // export tất cả cột đang có trong model (có cả mã ẩn)
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
