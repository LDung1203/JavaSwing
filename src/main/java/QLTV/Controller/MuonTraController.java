package QLTV.Controller;

import QLTV.Domain.*;
import QLTV.Model.DBConnection;
import QLTV.Model.MuonTraDAO;
import QLTV.Views.FormMuonTra;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import QLTV.Model.TheThuVienDAO;
import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.table.DefaultTableCellRenderer;


public class MuonTraController {

    private final TheThuVienDAO theDAO = new TheThuVienDAO();
    private final FormMuonTra view;
    private final MuonTraDAO dao = new MuonTraDAO();
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private ChiTietMuonTra ct;

    public MuonTraController(FormMuonTra view) {
        this.view = view;

        // Load dữ liệu ban đầu
        loadPhieuTable();
        loadSachTable();           // ← Quan trọng: load sách ngay
        loadDocGiaCombo();

        view.getTxtMaPhieu().setText(dao.taoMaMTMoi());
        view.getTxtMaNV().setText("NV01");

        registerEvents();
    }

    private void registerEvents() {
        view.getTxtMaPhieu().setText(dao.taoMaMTMoi());
        view.getTxtMaNV().setText("NV001");
        
        view.getBtnGiaHan().addActionListener(e -> handleGiaHan());
        view.getBtnTraSach().addActionListener(e -> handleTraSach());
        view.getCboDocGia().addActionListener(e -> onDocGiaSelected());
        
        view.getBtnSearch().addActionListener(e -> searchPhieu());
        view.getTxtSearch().addActionListener(e -> searchPhieu());

        view.getBtnSearchSach().addActionListener(e -> searchSach());
        view.getTxtSearchSach().addActionListener(e -> searchSach());

        view.getBtnThemPhieu().addActionListener(e -> handleInsertPhieu());
        view.getBtnCapNhatPhieu().addActionListener(e -> handleUpdatePhieu());
        view.getBtnXoaPhieu().addActionListener(e -> handleDeletePhieu());
        view.getBtnLamMoiPhieu().addActionListener(e -> clearAll());

        view.getBtnThemChiTiet().addActionListener(e -> handleAddChiTiet());
        view.getBtnXoaChiTiet().addActionListener(e -> handleDeleteChiTiet());

        view.getTblPhieu().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) fillFormFromPhieuSelected();
        });

        view.getTblSach().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) fillMaSachFromSelected();
        });
        
        // Listener cập nhật sách đã chọn khi chọn trong bảng
        view.getTblSach().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                capNhatSachDaChon();
            }
        });
    }
    private void capNhatSachDaChon() {
        JComboBox<String> combo = view.getCboSachDaChon();
        combo.removeAllItems();

        int[] selectedRows = view.getTblSach().getSelectedRows();
        for (int row : selectedRows) {
            String maSach = view.getModelSach().getValueAt(row, 0).toString();
            String tenSach = view.getModelSach().getValueAt(row, 1).toString();
            String theLoai = view.getModelSach().getValueAt(row, 2).toString();
            int soLuongCon = (Integer) view.getModelSach().getValueAt(row, 3);

            combo.addItem(maSach + " - " + tenSach + " (" + theLoai + ", Còn: " + soLuongCon + ")");
        }

        // Nếu có sách được chọn, mở combo để thấy danh sách
        if (selectedRows.length > 0) {
            combo.setPopupVisible(true);
            combo.setPopupVisible(false);
        }
    }
    private void loadPhieuTable() {
        List<MuonTra> list = dao.findAll();
        fillPhieuTable(list);
        applyOverdueRowColoring();
    }

    private void fillPhieuTable(List<MuonTra> list) {
        DefaultTableModel m = view.getModelPhieu();
        m.setRowCount(0);
        for (MuonTra mt : list) {
            m.addRow(new Object[]{
                mt.getMaMT(),
                mt.getMaDG(),
                mt.getTenDG(),
                mt.getMaNV(),
                sdf.format(mt.getNgayMuon()),
                sdf.format(mt.getHanTra()),
                mt.getTrangThai(),
                mt.getSoNgayMuon()
            });
        }
    }

    private void searchPhieu() {
        String key = view.getTxtSearch().getText().trim();
        if (key.isEmpty()) {
            loadPhieuTable();
        } else {
            // Tạm thời load tất cả (có thể mở rộng sau)
            loadPhieuTable();
        }
    }

    private void loadDocGiaCombo() {
        try {
            List<DocGiaMuon> list = dao.getAllDocGiaForCombo();
            view.getCboDocGia().removeAllItems();
            for (DocGiaMuon dg : list) {
                view.getCboDocGia().addItem(dg.getMaDG() + " - " + dg.getTenDG());
            }
            System.out.println("Load combo độc giả: " + list.size() + " độc giả");
        } catch (Exception ex) {
            System.err.println("LỖI LOAD COMBO ĐỘC GIẢ: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // ================== SÁCH ==================
    private void loadSachTable() {
        searchSach("");  // Load tất cả khi mở form
    }

    private void searchSach() {
        searchSach(view.getTxtSearchSach().getText().trim());
    }

    private void searchSach(String keyword) {
        try {
            List<SachMuon> list = dao.searchSach(keyword);
            DefaultTableModel m = view.getModelSach();
            m.setRowCount(0);
            for (SachMuon s : list) {
                m.addRow(new Object[]{
                    s.getMaSach(),
                    s.getTenSach(),
                    s.getTheLoai(),
                    s.getSoLuongCon()
                });
            }
            System.out.println("=== LOAD BẢNG SÁCH ===");
            System.out.println("Từ khóa: '" + keyword + "'");
            System.out.println("Tìm thấy: " + list.size() + " sách");
            if (list.isEmpty()) {
                System.out.println("→ Bảng sách trống! Kiểm tra:");
                System.out.println("  1. Bảng 'sach' trong database có dữ liệu chưa?");
                System.out.println("  2. Tên cột có đúng: MaSach, TenSach, TheLoai, SoLuong?");
            }
        } catch (Exception ex) {
            System.err.println("LỖI LOAD BẢNG SÁCH: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Lỗi kết nối database khi load sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fillMaSachFromSelected() {
        int row = view.getTblSach().getSelectedRow();
        if (row >= 0) {
            String maSach = view.getModelSach().getValueAt(row, 0).toString();
            view.getTxtMaSach().setText(maSach);
        }
    }

    private void fillFormFromPhieuSelected() {
        int row = view.getTblPhieu().getSelectedRow();
        System.out.println("Click bảng phiếu - Dòng được chọn: " + row); // ← Debug quan trọng

        if (row < 0) {
            System.out.println("Không có dòng nào được chọn → bỏ qua");
            return;
        }

        DefaultTableModel m = view.getModelPhieu();
        String maMT = m.getValueAt(row, 0).toString();
        String maDG = m.getValueAt(row, 1).toString();
        String maNV = m.getValueAt(row, 3).toString();

        System.out.println("Đang fill form cho phiếu: " + maMT + " - Độc giả: " + maDG);

        view.getTxtMaPhieu().setText(maMT);
        view.getTxtMaNV().setText(maNV);

        // Chọn độc giả trong combo
        boolean found = false;
        for (int i = 0; i < view.getCboDocGia().getItemCount(); i++) {
            String item = view.getCboDocGia().getItemAt(i);
            if (item != null && item.startsWith(maDG + " - ")) {
                view.getCboDocGia().setSelectedIndex(i);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("⚠️ Không tìm thấy độc giả " + maDG + " trong combo!");
        }

        try {
            String ngayMuonStr = m.getValueAt(row, 4).toString();
            String hanTraStr = m.getValueAt(row, 5).toString();
            view.getDcNgayMuon().setDate(sdf.parse(ngayMuonStr));
            view.getDcNgayTraDK().setDate(sdf.parse(hanTraStr));
            System.out.println("Set ngày: " + ngayMuonStr + " → " + hanTraStr);
        } catch (Exception ex) {
            System.out.println("Lỗi parse ngày: " + ex.getMessage());
            ex.printStackTrace();
        }

        loadChiTietTable(maMT);

        // Hiển thị mã thẻ
        TheThuVien the = theDAO.findByMaDG(maDG);
        if (the != null) {
            view.getTxtMaThe().setText(the.getMaThe());
            System.out.println("Mã thẻ: " + the.getMaThe());
        } else {
            view.getTxtMaThe().setText("");
            System.out.println("Không tìm thấy thẻ cho độc giả: " + maDG);
        }
    }



    private void loadChiTietTable(String maMT) {
        List<ChiTietMuonTra> list = dao.getChiTietByMaMT(maMT);
        DefaultTableModel m = view.getModelChiTiet();
        m.setRowCount(0);
        for (ChiTietMuonTra ct : list) {
            m.addRow(new Object[]{ct.getMaSach(), ct.getTenSach(), ct.getSoLuong(), ct.getGhiChu()});
        }
    }

    private MuonTra readPhieuForm() {
        String selected = (String) view.getCboDocGia().getSelectedItem();
        if (selected == null || selected.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn độc giả!");
            return null;
        }
        String maDG = selected.split(" - ")[0].trim();

        String maNV = view.getTxtMaNV().getText().trim();
        Date ngayMuon = view.getDcNgayMuon().getDate();
        Date hanTra = view.getDcNgayTraDK().getDate();

        if (maDG.isEmpty() || maNV.isEmpty() || ngayMuon == null || hanTra == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin phiếu!");
            return null;
        }
        if (hanTra.before(ngayMuon)) {
            JOptionPane.showMessageDialog(view, "Hạn trả phải sau ngày mượn!");
            return null;
        }

        return new MuonTra(null, maDG, null, maNV, ngayMuon, hanTra, "Chưa trả", view.getTxtGhiChu().getText());
    }

    private void handleInsertPhieu() {
        MuonTra mt = readPhieuForm();
        if (mt == null) return;

        // Lấy danh sách sách được chọn
        int[] selectedRows = view.getTblSach().getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(view, "Chưa chọn sách để mượn!");
            return;
        }

        // Tạo mã phiếu
        String maMT = dao.taoMaMTMoi();
        mt.setMaMT(maMT);

        // 1️⃣ Thêm phiếu mượn
        if (dao.insertMuonTra(mt) <= 0) {
            JOptionPane.showMessageDialog(view, "Thêm phiếu thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int soLuong = (int) view.getSpSoLuong().getValue();
        DefaultTableModel modelSach = view.getModelSach();
        boolean allInserted = true;

        // 2️⃣ Thêm chi tiết + giảm kho
        for (int row : selectedRows) {
            String maSach = modelSach.getValueAt(row, 0).toString();
            String tenSach = modelSach.getValueAt(row, 1).toString();

            ChiTietMuonTra ct = new ChiTietMuonTra(
                    maMT,
                    maSach,
                    tenSach,
                    soLuong,
                    ""
            );

            // Insert chi tiết
            if (dao.insertChiTiet(ct) > 0) {

                // 🔥 Giảm kho
                if (dao.giamSoLuongSach(maSach, soLuong) <= 0) {
                    allInserted = false;
                    JOptionPane.showMessageDialog(
                            view,
                            "Không đủ số lượng cho sách: " + tenSach,
                            "Lỗi kho",
                            JOptionPane.ERROR_MESSAGE
                    );
                    break;
                }

            } else {
                allInserted = false;
                break;
            }
        }

        // 3️⃣ Reload & reset
        loadPhieuTable();
        loadSachTable();
        clearAll();

        if (allInserted) {
            JOptionPane.showMessageDialog(view, "Mượn sách thành công!");
        } else {
            JOptionPane.showMessageDialog(
                    view,
                    "Phiếu đã tạo nhưng có lỗi khi thêm chi tiết!",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }



    private void handleUpdatePhieu() {
        String maMT = view.getTxtMaPhieu().getText().trim();
        if (maMT.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Chọn phiếu để cập nhật!");
            return;
        }
        MuonTra mt = readPhieuForm();
        if (mt == null) return;
        mt.setMaMT(maMT);

        if (dao.updateMuonTra(mt) > 0) {
            JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
            loadPhieuTable();
        } else {
            JOptionPane.showMessageDialog(view, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDeletePhieu() {
        String maMT = view.getTxtMaPhieu().getText().trim();
        if (maMT.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Chọn phiếu để xóa!");
            return;
        }
        int cf = JOptionPane.showConfirmDialog(view, "Xóa phiếu " + maMT + " và tất cả chi tiết?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (cf == JOptionPane.YES_OPTION && dao.deleteMuonTra(maMT) > 0) {
            JOptionPane.showMessageDialog(view, "Xóa thành công!");
            loadPhieuTable();
            clearAll();
        }
    }

    private void handleAddChiTiet() {
        String maMT = view.getTxtMaPhieu().getText().trim();
        if (maMT.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Chưa có mã phiếu mượn!");
            return;
        }
        String maSach = view.getTxtMaSach().getText().trim();
        int soLuong = (int) view.getSpSoLuong().getValue();

        if (maSach.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nhập mã sách!");
            return;
        }
        if (soLuong <= 0) {
            JOptionPane.showMessageDialog(view, "Số lượng phải lớn hơn 0!");
            return;
        }

        Integer conLai = dao.getSoLuongConLai(maSach);
        if (conLai == null) {
            JOptionPane.showMessageDialog(view, "Mã sách không tồn tại!");
            return;
        }
        if (soLuong > conLai) {
            JOptionPane.showMessageDialog(view, "Chỉ còn " + conLai + " cuốn!");
            return;
        }

        String tenSach = dao.getTenSach(maSach);
        ChiTietMuonTra ct = new ChiTietMuonTra(maMT, maSach, tenSach, soLuong, "");

        if (dao.insertChiTiet(ct) > 0) {
            JOptionPane.showMessageDialog(view, "Thêm chi tiết thành công!");
            loadChiTietTable(maMT);
            view.clearChiTietForm();
            view.getSpSoLuong().setValue(1); // reset spinner
        } else {
            JOptionPane.showMessageDialog(view, "Thêm chi tiết thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDeleteChiTiet() {
        int row = view.getTblChiTiet().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Chọn dòng chi tiết để xóa!");
            return;
        }
        String maMT = view.getTxtMaPhieu().getText().trim();
        String maSach = view.getModelChiTiet().getValueAt(row, 0).toString();

        if (dao.deleteChiTiet(maMT, maSach) > 0) {
            JOptionPane.showMessageDialog(view, "Xóa chi tiết thành công!");
            loadChiTietTable(maMT);
        } else {
            JOptionPane.showMessageDialog(view, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void onDocGiaSelected() {
        Object selected = view.getCboDocGia().getSelectedItem();
        if (selected == null) {
            view.getTxtMaThe().setText("");
            setPhieuButtonsEnabled(false);
            return;
        }

        String maDG = selected.toString().split(" - ")[0].trim();

        TheThuVien the = theDAO.findByMaDG(maDG);

        // ❌ Chưa có thẻ
        if (the == null) {
            view.getTxtMaThe().setText("");
            JOptionPane.showMessageDialog(view,
                    "Độc giả chưa có thẻ thư viện!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            setPhieuButtonsEnabled(false);
            return;
        }

        // Hiển thị mã thẻ
        view.getTxtMaThe().setText(the.getMaThe());


        // ✅ OK
        setPhieuButtonsEnabled(true);
    }
    private void handleGiaHan() {
        int row = view.getTblPhieu().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn phiếu để gia hạn!");
            return;
        }

        DefaultTableModel m = view.getModelPhieu();
        String maMT = m.getValueAt(row, 0).toString();
        String hanTraStr = m.getValueAt(row, 5).toString();

        try {
            // Chuyển từ String sang Date
            Date hanTra = sdf.parse(hanTraStr);

            // Cộng thêm 10 ngày
            long newTime = hanTra.getTime() + 10L * 24 * 60 * 60 * 1000; // 10 ngày
            Date newHanTra = new Date(newTime);

            // Cập nhật vào DB
            if (dao.updateHanTra(maMT, newHanTra) > 0) {
                JOptionPane.showMessageDialog(view, "Gia hạn thành công! Hạn trả mới: " + sdf.format(newHanTra));
                loadPhieuTable(); // reload bảng phiếu
            } else {
                JOptionPane.showMessageDialog(view, "Gia hạn thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Lỗi khi gia hạn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setPhieuButtonsEnabled(boolean enabled) {
        view.getBtnThemPhieu().setEnabled(enabled);
        view.getBtnCapNhatPhieu().setEnabled(enabled);
        view.getBtnGiaHan().setEnabled(enabled);
    }
    
    private void handleTraSach() {
        int row = view.getTblPhieu().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn phiếu để trả sách!");
            return;
        }

        String maMT = view.getModelPhieu().getValueAt(row, 0).toString();
        String trangThai = view.getModelPhieu().getValueAt(row, 6).toString();

        if (trangThai.equalsIgnoreCase("Đã trả")) {
            JOptionPane.showMessageDialog(view, "Phiếu này đã được trả trước đó!");
            return;
        }

        int cf = JOptionPane.showConfirmDialog(view, "Xác nhận trả sách cho phiếu " + maMT + "?", 
                                               "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (cf != JOptionPane.YES_OPTION) return;

        if (dao.updateTrangThai(maMT, "Đã trả") > 0) {
            JOptionPane.showMessageDialog(view, "Trả sách thành công!");
            loadPhieuTable(); // reload bảng phiếu
        } else {
            JOptionPane.showMessageDialog(view, "Trả sách thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        
        // Lấy chi tiết mượn
        List<ChiTietMuonTra> listCT = dao.getChiTietByMaMT(maMT);

        // Trả sách → tăng kho
        for (ChiTietMuonTra ct : listCT) {
            dao.tangSoLuongSach(ct.getMaSach(), ct.getSoLuong());
        }
        loadSachTable();

    }
    private void applyOverdueRowColoring() {
        view.getTblPhieu().setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Lấy giá trị cột "Ngày còn lại" - cột cuối cùng (index = số cột - 1)
                int daysColumnIndex = table.getColumnCount() - 1;
                Object daysValue = table.getValueAt(row, daysColumnIndex);
                String daysStr = daysValue != null ? daysValue.toString().trim() : "";

                // Kiểm tra nếu quá hạn (bắt đầu bằng "+")
                boolean isOverdue = daysStr.startsWith("+");

                if (!isSelected) {  // Nếu không đang chọn dòng
                    if (isOverdue) {
                        c.setBackground(new Color(255, 180, 180)); // Đỏ nhạt, dễ nhìn
                        c.setForeground(Color.BLACK);
                    } else {
                        c.setBackground(table.getBackground());
                        c.setForeground(table.getForeground());
                    }
                } else {
                    // Nếu dòng đang được chọn → giữ màu chọn mặc định
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(table.getSelectionForeground());
                }

                // Căn giữa cột số ngày (đẹp hơn)
                if (column == daysColumnIndex) {
                    setHorizontalAlignment(JLabel.CENTER);
                }

                return c;
            }
        });
    }
    // Giảm số lượng sách khi mượn
    public int giamSoLuongSach(String maSach, int soLuong) {
        String sql = "UPDATE sach SET SoLuongCon = SoLuongCon - ? WHERE MaSach = ? AND SoLuongCon >= ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, soLuong);
            ps.setString(2, maSach);
            ps.setInt(3, soLuong);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Tăng số lượng sách khi trả
    public int tangSoLuongSach(String maSach, int soLuong) {
        String sql = "UPDATE sach SET SoLuongCon = SoLuongCon + ? WHERE MaSach = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, soLuong);
            ps.setString(2, maSach);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void clearAll() {
        view.clearPhieuForm();
        view.clearChiTietForm();
        view.getModelChiTiet().setRowCount(0);
        view.getTxtMaPhieu().setText(dao.taoMaMTMoi());
        view.getCboDocGia().setSelectedIndex(-1);
        view.getTxtMaThe().setText("");          // 🔥 thêm
        setPhieuButtonsEnabled(false);           // 🔥 thêm
        view.getSpSoLuong().setValue(1);
    }

}