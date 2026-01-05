/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Views;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;

/**
 *
 * @author Admin
 */
public class FormMuonTra extends JPanel {

    private JTextField txtMaThe = new JTextField();
    private JButton btnGiaHan = new JButton("Gia hạn");
    private JButton btnTraSach = new JButton("Trả sách");

    private JComboBox<String> cboSachDaChon = new JComboBox<>();
    
    // ===== header search for phiếu =====
    private JTextField txtSearch = new JTextField();
    private JButton btnSearch = new JButton("Tìm");

    // ===== form phiếu inputs =====
    private JComboBox<String> cboDocGia = new JComboBox<>();
    private JList<String> lstSach = new JList<>();
    private JSpinner spSoLuong = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
    private JTextField txtMaPhieu = new JTextField();
    private JTextField txtMaDG = new JTextField();
    private JTextField txtMaNV = new JTextField();
    private JDateChooser dcNgayMuon = new JDateChooser();
    private JDateChooser dcNgayTraDK = new JDateChooser();
    private JTextField txtGhiChu = new JTextField();

    // ===== buttons for phiếu =====
    private JButton btnThemPhieu = new JButton("Thêm phiếu");
    private JButton btnCapNhatPhieu = new JButton("Cập nhật");
    private JButton btnXoaPhieu = new JButton("Xóa phiếu");
    private JButton btnLamMoiPhieu = new JButton("Làm mới");

    // ===== search for sách =====
    private JTextField txtSearchSach = new JTextField();
    private JButton btnSearchSach = new JButton("Tìm sách");

    // ===== table sách =====
    private JTable tblSach;
    private DefaultTableModel modelSach;

    // ===== inputs for chi tiết =====
    private JTextField txtMaSach = new JTextField();
    private JTextField txtSoLuong = new JTextField();
    private JButton btnThemChiTiet = new JButton("Thêm chi tiết");
    private JButton btnXoaChiTiet = new JButton("Xóa chi tiết");

    // ===== table chi tiết mượn trả =====
    private JTable tblChiTiet;
    private DefaultTableModel modelChiTiet;

    // ===== table phiếu mượn trả =====
    private JTable tblPhieu;
    private DefaultTableModel modelPhieu;

    private void styleSpinner(JSpinner sp) {
        sp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sp.setPreferredSize(new Dimension(0, 36));
        JComponent editor = sp.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
            styleInput(tf);
        }
    }

    public FormMuonTra() {
        setLayout(new BorderLayout());
        setBackground(new Color(235, 242, 250));
        setBorder(new EmptyBorder(12, 12, 12, 12));
        add(createHeader(), BorderLayout.NORTH);
        add(createBody(), BorderLayout.CENTER);
    }

    private JComponent createHeader() {
        JPanel header = new JPanel(new BorderLayout(10, 10));
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(0, 0, 10, 0));
        JLabel title = new JLabel("QUẢN LÝ MƯỢN TRẢ");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(20, 40, 70));
        return header;
    }

    private JComponent createBody() {
        JPanel body = new JPanel(new BorderLayout(12, 12));
        body.setOpaque(false);

        // LEFT: FORM PHIẾU
        JComponent form = createPhieuFormCard();
        form.setPreferredSize(new Dimension(360, 0));
        body.add(form, BorderLayout.WEST);

        // CENTER: 3 CARD DỌC (phiếu, sách, chi tiết)
        body.add(createRightPanel(), BorderLayout.CENTER);
        return body;
    }

    private JComponent createRightPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        panel.add(createPhieuTableCard());
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(createSachCard());
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(createChiTietCard());

        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    private JScrollPane createSachMultiSelect() {
        lstSach.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lstSach.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        lstSach.setVisibleRowCount(5);
        lstSach.setBorder(BorderFactory.createLineBorder(new Color(210, 220, 230)));
        JScrollPane sp = new JScrollPane(lstSach);
        sp.setPreferredSize(new Dimension(0, 90));
        return sp;
    }

    private void styleComboBox(JComboBox<?> cb) {
        cb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cb.setPreferredSize(new Dimension(0, 36));
        cb.setBackground(Color.WHITE);
    }

    private JComponent createPhieuFormCard() {
        JPanel card = new RoundedPanel(18);
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(16, 16, 16, 16));
        card.setPreferredSize(new Dimension(360, 0));

        // ===== HEADER =====
        JLabel lbTitle = new JLabel("Thông tin phiếu mượn");
        lbTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lbTitle.setForeground(new Color(30, 50, 90));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(lbTitle, BorderLayout.NORTH);
        top.add(new JSeparator(), BorderLayout.SOUTH);

        // ===== NỘI DUNG FORM =====
        JPanel formContent = new JPanel(new GridBagLayout());
        formContent.setOpaque(false);

        // Cấu hình 2 cột: nhãn (căn phải) và input (giãn rộng)
        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.gridx = 0;
        gbcLabel.anchor = GridBagConstraints.EAST;    // Nhãn căn phải
        gbcLabel.insets = new Insets(10, 10, 10, 15);
        gbcLabel.weightx = 0.0;

        GridBagConstraints gbcInput = new GridBagConstraints();
        gbcInput.gridx = 1;
        gbcInput.fill = GridBagConstraints.HORIZONTAL;
        gbcInput.anchor = GridBagConstraints.WEST;
        gbcInput.insets = new Insets(10, 0, 10, 10);
        gbcInput.weightx = 1.0;   // Input chiếm hết phần còn lại → dài ra!

        int row = 0;

        // Helper method gọi trực tiếp (không cần lambda)
        row = addFormRow(formContent, gbcLabel, gbcInput, row, "Mã phiếu", txtMaPhieu);
        styleComboBox(cboDocGia);
        row = addFormRow(formContent, gbcLabel, gbcInput, row, "Độc giả", cboDocGia);

        styleInput(txtMaThe);
        txtMaThe.setEditable(false);
        row = addFormRow(formContent, gbcLabel, gbcInput, row, "Thẻ thư viện", txtMaThe);

        row = addFormRow(formContent, gbcLabel, gbcInput, row, "Mã nhân viên", txtMaNV);

        styleDateChooser(dcNgayMuon);
        styleDateChooser(dcNgayTraDK);
        row = addFormRow(formContent, gbcLabel, gbcInput, row, "Ngày mượn", dcNgayMuon);
        row = addFormRow(formContent, gbcLabel, gbcInput, row, "Ngày trả dự kiến", dcNgayTraDK);

        styleSpinner(spSoLuong);
        row = addFormRow(formContent, gbcLabel, gbcInput, row, "Số lượng", spSoLuong);

        row = addFormRow(formContent, gbcLabel, gbcInput, row, "Ghi chú", txtGhiChu);

        // ===== SÁCH ĐÃ CHỌN =====
        JLabel lbSachChon = new JLabel("Sách đã chọn:");
        lbSachChon.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbSachChon.setForeground(new Color(30, 50, 90));

        GridBagConstraints gbcFullLabel = new GridBagConstraints();
        gbcFullLabel.gridx = 0;
        gbcFullLabel.gridy = row;
        gbcFullLabel.gridwidth = 2;
        gbcFullLabel.anchor = GridBagConstraints.WEST;
        gbcFullLabel.insets = new Insets(20, 10, 8, 10);
        formContent.add(lbSachChon, gbcFullLabel);

        styleComboBox(cboSachDaChon);
        cboSachDaChon.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cboSachDaChon.setPrototypeDisplayValue("S001 - Tên sách dài để test chiều rộng (Giáo trình, Còn: 30)");
        cboSachDaChon.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                            boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (c instanceof JLabel) {
                    ((JLabel) c).setHorizontalAlignment(JLabel.LEFT);
                }
                return c;
            }
        });

        GridBagConstraints gbcFullCombo = new GridBagConstraints();
        gbcFullCombo.gridx = 0;
        gbcFullCombo.gridy = row + 1;
        gbcFullCombo.gridwidth = 2;
        gbcFullCombo.fill = GridBagConstraints.HORIZONTAL;
        gbcFullCombo.weightx = 1.0;
        gbcFullCombo.insets = new Insets(0, 10, 20, 10);
        formContent.add(cboSachDaChon, gbcFullCombo);

        // ===== NÚT HÀNH ĐỘNG =====
        JPanel actions = new JPanel(new GridLayout(2, 3, 10, 10));
        actions.setOpaque(false);
        actions.setBorder(new EmptyBorder(12, 0, 8, 0));

        stylePrimary(btnThemPhieu);
        stylePrimary(btnCapNhatPhieu);
        styleAccent(btnGiaHan);
        styleDanger(btnXoaPhieu);
        styleGhost(btnLamMoiPhieu);
        styleAccent(btnTraSach); // màu xanh lá giống gia hạn

        actions.add(btnTraSach);
        actions.add(btnThemPhieu);
        actions.add(btnCapNhatPhieu);
        actions.add(btnGiaHan);
        actions.add(btnXoaPhieu);
        actions.add(btnLamMoiPhieu);
        actions.add(new JLabel(""));

        // ===== SCROLLPANE =====
        JScrollPane scrollPane = new JScrollPane(formContent);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));

        // ===== LẮP RÁP =====
        card.add(top, BorderLayout.NORTH);
        card.add(scrollPane, BorderLayout.CENTER);
        card.add(actions, BorderLayout.SOUTH);

        return card;
    }

    private int addFormRow(JPanel panel, GridBagConstraints gbcLabel, GridBagConstraints gbcInput,
                       int row, String labelText, JComponent component) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        gbcLabel.gridy = row;
        panel.add(label, gbcLabel);

        gbcInput.gridy = row;
        panel.add(component, gbcInput);

        return row + 1;
    }
    
    private JComponent createSachCard() {
        JPanel card = new RoundedPanel(18);
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(16, 16, 16, 16));

        JLabel lb = new JLabel("Danh sách sách");
        lb.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lb.setForeground(new Color(30, 50, 90));

        JPanel searchBox = new JPanel(new BorderLayout(8, 0));
        searchBox.setOpaque(false);
        searchBox.setBorder(new EmptyBorder(0, 0, 10, 0));
        txtSearchSach.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSearchSach.setPreferredSize(new Dimension(0, 36));
        txtSearchSach.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220), 1, true),
                new EmptyBorder(8, 10, 8, 10)
        ));
        txtSearchSach.setBackground(Color.WHITE);
        stylePrimary(btnSearchSach);
        btnSearchSach.setPreferredSize(new Dimension(100, 36));
        searchBox.add(txtSearchSach, BorderLayout.CENTER);
        searchBox.add(btnSearchSach, BorderLayout.EAST);

        String[] colsSach = {"Mã Sách", "Tên Sách", "Thể Loại", "Số Lượng"};
        modelSach = new DefaultTableModel(colsSach, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblSach = new JTable(modelSach);
        tblSach.setRowHeight(28);
        tblSach.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblSach.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblSach.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);  // Thay dòng SINGLE thành MULTIPLE
        JScrollPane sp = new JScrollPane(tblSach);
        sp.setBorder(BorderFactory.createLineBorder(new Color(220, 230, 240)));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(lb, BorderLayout.NORTH);
        top.add(new JSeparator(), BorderLayout.SOUTH);

        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        center.add(searchBox, BorderLayout.NORTH);
        center.add(sp, BorderLayout.CENTER);

        card.add(top, BorderLayout.NORTH);
        card.add(center, BorderLayout.CENTER);
        return card;
    }

    private JComponent createChiTietCard() {
        JPanel card = new RoundedPanel(18);
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(16, 16, 16, 16));

        JLabel lb = new JLabel("Chi tiết mượn trả");
        lb.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lb.setForeground(new Color(30, 50, 90));

        // Input khu vực
        JPanel inputs = new JPanel(new GridBagLayout());
        inputs.setOpaque(false);
        inputs.setBorder(new EmptyBorder(0, 0, 10, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 2, 6, 2);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        int r = 0;
        r = addRow(inputs, gbc, r, "Mã sách", txtMaSach);
        r = addRow(inputs, gbc, r, "Số lượng", txtSoLuong);

        JPanel chiTietActions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        chiTietActions.setOpaque(false);
        styleAccent(btnThemChiTiet);
        styleDanger(btnXoaChiTiet);
        chiTietActions.add(btnThemChiTiet);
        chiTietActions.add(btnXoaChiTiet);

        String[] colsChiTiet = {"Mã Sách", "Tên Sách", "Số Lượng", "Ghi Chú"};
        modelChiTiet = new DefaultTableModel(colsChiTiet, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        tblChiTiet = new JTable(modelChiTiet);
        tblChiTiet.setRowHeight(28);
        tblChiTiet.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblChiTiet.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblChiTiet.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane spChiTiet = new JScrollPane(tblChiTiet);
        spChiTiet.setBorder(BorderFactory.createLineBorder(new Color(220, 230, 240)));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(lb, BorderLayout.NORTH);
        top.add(new JSeparator(), BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(inputs, BorderLayout.NORTH);
        centerPanel.add(spChiTiet, BorderLayout.CENTER);

        card.add(top, BorderLayout.NORTH);
        card.add(centerPanel, BorderLayout.CENTER);
        card.add(chiTietActions, BorderLayout.SOUTH);
        return card;
    }

    private JComponent createPhieuTableCard() {
        JPanel card = new RoundedPanel(18);
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(16, 16, 16, 16));

        JLabel lb = new JLabel("Danh sách phiếu mượn trả");
        lb.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lb.setForeground(new Color(30, 50, 90));

        String[] colsPhieu = {"Mã Phiếu", "Mã DG", "Tên DG", "Mã NV", "Ngày Mượn", "Ngày Trả DK", "Trạng Thái","Số Ngày Mượn"};
        modelPhieu = new DefaultTableModel(colsPhieu, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        tblPhieu = new JTable(modelPhieu);
        tblPhieu.setRowHeight(28);
        tblPhieu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblPhieu.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblPhieu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sp = new JScrollPane(tblPhieu);
        sp.setBorder(BorderFactory.createLineBorder(new Color(220, 230, 240)));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(lb, BorderLayout.NORTH);
        top.add(new JSeparator(), BorderLayout.SOUTH);

        card.add(top, BorderLayout.NORTH);
        card.add(sp, BorderLayout.CENTER);
        return card;
    }

    private int addRow(JPanel form, GridBagConstraints gbc, int row, String labelText, JComponent field) {
        JLabel lb = new JLabel(labelText);
        lb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        if (field instanceof JTextField) {
            styleInput((JTextField) field);
        }
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        form.add(lb, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.10;
        form.add(field, gbc);
        return row + 1;
    }

    private void styleInput(JTextField f) {
        f.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        f.setPreferredSize(new Dimension(0, 36));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 220, 230), 1, true),
                new EmptyBorder(8, 10, 8, 10)
        ));
        f.setBackground(new Color(250, 252, 255));
    }

    private void styleDateChooser(JDateChooser dc) {
        dc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dc.setPreferredSize(new Dimension(0, 36));
        dc.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 220, 230), 1, true),
                new EmptyBorder(8, 10, 8, 10)
        ));
        dc.setBackground(new Color(250, 252, 255));
        dc.setDateFormatString("yyyy-MM-dd");
    }

    private void stylePrimary(JButton b) {
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(45, 126, 255));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void styleDanger(JButton b) {
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(220, 70, 70));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void styleGhost(JButton b) {
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setFocusPainted(false);
        b.setForeground(new Color(45, 126, 255));
        b.setBackground(Color.WHITE);
        b.setBorder(BorderFactory.createLineBorder(new Color(45, 126, 255), 1, true));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void styleAccent(JButton b) {
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(46, 170, 125));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    static class RoundedPanel extends JPanel {
        private final int radius;
        public RoundedPanel(int radius) { 
            this.radius = radius; 
            setOpaque(false); 
        }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }
    

        // ===== getters for Controller =====
    public JComboBox<String> getCboDocGia() { return cboDocGia; }
    public JSpinner getSpSoLuong() { return spSoLuong; }

    public JTextField getTxtSearch() { return txtSearch; }
    public JButton getBtnSearch() { return btnSearch; }
    public JTextField getTxtMaPhieu() { return txtMaPhieu; }
    public JTextField getTxtMaDG() { return txtMaDG; }
    public JTextField getTxtMaNV() { return txtMaNV; }
    public JDateChooser getDcNgayMuon() { return dcNgayMuon; }
    public JDateChooser getDcNgayTraDK() { return dcNgayTraDK; }
    public JTextField getTxtGhiChu() { return txtGhiChu; }

    public JButton getBtnThemPhieu() { return btnThemPhieu; }
    public JButton getBtnCapNhatPhieu() { return btnCapNhatPhieu; }
    public JButton getBtnXoaPhieu() { return btnXoaPhieu; }
    public JButton getBtnLamMoiPhieu() { return btnLamMoiPhieu; }

    public JTextField getTxtSearchSach() { return txtSearchSach; }
    public JButton getBtnSearchSach() { return btnSearchSach; }

    public JTable getTblSach() { return tblSach; }
    public DefaultTableModel getModelSach() { return modelSach; }

    public JTextField getTxtMaSach() { return txtMaSach; }
    public JTextField getTxtSoLuong() { return txtSoLuong; }
    public JButton getBtnThemChiTiet() { return btnThemChiTiet; }
    public JButton getBtnXoaChiTiet() { return btnXoaChiTiet; }

    public JTable getTblChiTiet() { return tblChiTiet; }
    public DefaultTableModel getModelChiTiet() { return modelChiTiet; }

    public JTable getTblPhieu() { return tblPhieu; }
    public DefaultTableModel getModelPhieu() { return modelPhieu; }

    public JComboBox<String> getCboSachDaChon() { return cboSachDaChon; }
        public JTextField getTxtMaThe() {
        return txtMaThe;
    }
        
    public JButton getBtnGiaHan() {
        return btnGiaHan;
    }
    public JButton getBtnTraSach() { return btnTraSach; }

    public void clearPhieuForm() {
        txtMaPhieu.setText("");
        txtMaDG.setText("");
        dcNgayMuon.setDate(null);
        dcNgayTraDK.setDate(null);
        txtGhiChu.setText("");
    }

    public void clearChiTietForm() {
        txtMaSach.setText("");
        txtSoLuong.setText("");
    }
}