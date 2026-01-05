/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Views;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Admin
 */

public class FormTheThuVien extends JPanel {

    private JTextField txtSearch = new JTextField();
    private JButton btnSearch = new JButton("Tìm");

    private JTextField txtMaThe = new JTextField();
    private JComboBox<String> cboMaDG = new JComboBox<>();
    private JTextField txtNgayCap = new JTextField();     // yyyy-MM-dd
    private JTextField txtNgayHetHan = new JTextField();  // yyyy-MM-dd
    private JComboBox<String> cboTrangThai = new JComboBox<>(new String[]{"Còn hiệu lực", "Hết hiệu lực"});

    private JButton btnThem = new JButton("Thêm");
    private JButton btnSua = new JButton("Sửa");
    private JButton btnXoa = new JButton("Xóa");
    private JButton btnLamMoi = new JButton("Làm mới");
    private JButton btnNhapFile = new JButton("Nhập file (CSV)");
    private JButton btnXuatFile = new JButton("Xuất file (CSV)");

    private JTable tblThe;
    private DefaultTableModel model;

    public FormTheThuVien() {
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

        JLabel title = new JLabel("QUẢN LÝ THẺ THƯ VIỆN");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(20, 40, 70));

        JPanel searchBox = new JPanel(new BorderLayout(8, 0));
        searchBox.setOpaque(false);

        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSearch.setPreferredSize(new Dimension(260, 36));
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220), 1, true),
                new EmptyBorder(8, 10, 8, 10)
        ));
        txtSearch.setBackground(Color.WHITE);

        stylePrimary(btnSearch);
        btnSearch.setPreferredSize(new Dimension(80, 36));

        searchBox.add(txtSearch, BorderLayout.CENTER);
        searchBox.add(btnSearch, BorderLayout.EAST);

        header.add(title, BorderLayout.WEST);
        header.add(searchBox, BorderLayout.EAST);

        return header;
    }

    private JComponent createBody() {
        JPanel body = new JPanel(new BorderLayout(12, 12));
        body.setOpaque(false);

        body.add(createFormCard(), BorderLayout.WEST);
        body.add(createTableCard(), BorderLayout.CENTER);

        return body;
    }

    private JComponent createFormCard() {
        JPanel card = new RoundedPanel(18);
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(360, 0));
        card.setBorder(new EmptyBorder(16, 16, 16, 16));

        JLabel lb = new JLabel("Thông tin thẻ thư viện");
        lb.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lb.setForeground(new Color(30, 50, 90));

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 2, 6, 2);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        int r = 0;
        r = addRow(form, gbc, r, "Mã thẻ", txtMaThe);
        r = addRow(form, gbc, r, "Mã độc giả", cboMaDG);
        r = addRow(form, gbc, r, "Ngày cấp (yyyy-MM-dd)", txtNgayCap);
        r = addRow(form, gbc, r, "Ngày hết hạn (yyyy-MM-dd)", txtNgayHetHan);
        r = addRow(form, gbc, r, "Trạng thái", cboTrangThai);

        JPanel actions = new JPanel(new GridLayout(3, 2, 10, 10));
        actions.setOpaque(false);
        actions.setBorder(new EmptyBorder(12, 0, 0, 0));

        stylePrimary(btnThem);
        stylePrimary(btnSua);
        styleDanger(btnXoa);
        styleGhost(btnLamMoi);
        styleAccent(btnNhapFile);
        styleAccent(btnXuatFile);

        actions.add(btnThem);
        actions.add(btnSua);
        actions.add(btnXoa);
        actions.add(btnLamMoi);
        actions.add(btnNhapFile);
        actions.add(btnXuatFile);

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(lb, BorderLayout.NORTH);
        top.add(new JSeparator(), BorderLayout.SOUTH);

        card.add(top, BorderLayout.NORTH);
        card.add(form, BorderLayout.CENTER);
        card.add(actions, BorderLayout.SOUTH);

        styleInput(txtMaThe);
        txtMaThe.setEditable(false);
        txtMaThe.setBackground(new Color(230, 230, 230));

        styleCombo(cboMaDG);
        styleInput(txtNgayCap);
        styleInput(txtNgayHetHan);
        styleCombo(cboTrangThai);

        return card;
    }

    private int addRow(JPanel form, GridBagConstraints gbc, int row, String text, JComponent field) {
        JLabel lb = new JLabel(text);
        lb.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        gbc.gridx = 0;
        gbc.gridy = row;
        form.add(lb, gbc);

        gbc.gridy = row + 1;
        form.add(field, gbc);

        return row + 2;
    }

    private JComponent createTableCard() {
        JPanel card = new RoundedPanel(18);
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(16, 16, 16, 16));

        JLabel lb = new JLabel("Danh sách thẻ");
        lb.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lb.setForeground(new Color(30, 50, 90));

        String[] cols = {"Mã thẻ", "Mã độc giả", "Ngày cấp", "Ngày hết hạn", "Trạng thái"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };

        tblThe = new JTable(model);
        tblThe.setRowHeight(28);
        tblThe.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblThe.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblThe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane sp = new JScrollPane(tblThe);
        sp.setBorder(BorderFactory.createLineBorder(new Color(220, 230, 240)));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(lb, BorderLayout.NORTH);
        top.add(new JSeparator(), BorderLayout.SOUTH);

        card.add(top, BorderLayout.NORTH);
        card.add(sp, BorderLayout.CENTER);

        return card;
    }

    // ===== Styles =====
    private void styleInput(JTextField f) {
        f.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        f.setPreferredSize(new Dimension(0, 36));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 220, 230), 1, true),
                new EmptyBorder(8, 10, 8, 10)
        ));
        f.setBackground(new Color(250, 252, 255));
    }

    private void styleCombo(JComboBox<?> cb) {
        cb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cb.setPreferredSize(new Dimension(0, 36));
        cb.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 220, 230), 1, true),
                new EmptyBorder(4, 8, 4, 8)
        ));
        cb.setBackground(new Color(250, 252, 255));
        cb.setOpaque(true);
    }

    private void stylePrimary(JButton b) {
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(45, 126, 255));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(0, 36));
    }

    private void styleDanger(JButton b) {
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(220, 70, 70));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(0, 36));
    }

    private void styleGhost(JButton b) {
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setFocusPainted(false);
        b.setForeground(new Color(45, 126, 255));
        b.setBackground(Color.WHITE);
        b.setBorder(BorderFactory.createLineBorder(new Color(45, 126, 255), 1, true));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(0, 36));
    }

    private void styleAccent(JButton b) {
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(46, 170, 125));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(0, 36));
    }

    static class RoundedPanel extends JPanel {
        private final int radius;
        public RoundedPanel(int radius) { this.radius = radius; setOpaque(false); }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // ===== Getters =====
    public JTextField getTxtSearch() { return txtSearch; }
    public JButton getBtnSearch() { return btnSearch; }

    public JButton getBtnThem() { return btnThem; }
    public JButton getBtnSua() { return btnSua; }
    public JButton getBtnXoa() { return btnXoa; }
    public JButton getBtnLamMoi() { return btnLamMoi; }
    public JButton getBtnNhapFile() { return btnNhapFile; }
    public JButton getBtnXuatFile() { return btnXuatFile; }

    public JTable getTblThe() { return tblThe; }
    public DefaultTableModel getModel() { return model; }

    public JComboBox<String> getCboMaDG() { return cboMaDG; }
    public JComboBox<String> getCboTrangThai() { return cboTrangThai; }

    public String getMaThe() { return txtMaThe.getText().trim(); }
    public String getMaDG() { return cboMaDG.getSelectedItem() == null ? "" : cboMaDG.getSelectedItem().toString(); }
    public String getNgayCapText() { return txtNgayCap.getText().trim(); }
    public String getNgayHetHanText() { return txtNgayHetHan.getText().trim(); }
    public String getTrangThai() { return cboTrangThai.getSelectedItem() == null ? "" : cboTrangThai.getSelectedItem().toString(); }

    public void setMaThe(String ma) { txtMaThe.setText(ma); }
    public void setNgayCapText(String s) { txtNgayCap.setText(s); }
    public void setNgayHetHanText(String s) { txtNgayHetHan.setText(s); }

    public void clearForm() {
        txtMaThe.setText("");
        txtNgayCap.setText("");
        txtNgayHetHan.setText("");
        if (cboTrangThai.getItemCount() > 0) cboTrangThai.setSelectedIndex(0);
    }

    public void setForm(String maThe, String maDG, String ngayCap, String ngayHetHan, String trangThai) {
        txtMaThe.setText(maThe);
        cboMaDG.setSelectedItem(maDG);
        txtNgayCap.setText(ngayCap);
        txtNgayHetHan.setText(ngayHetHan);
        cboTrangThai.setSelectedItem(trangThai);
    }
}
