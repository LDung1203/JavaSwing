/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Views;

import QLTV.Domain.Khoa;
import QLTV.Domain.Lop;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Admin
 */

public class FormDocGia extends JPanel {

    // ===== header search =====
    private JTextField txtSearch = new JTextField();
    private JButton btnSearch = new JButton("Tìm");

    // ===== form fields =====
    private JTextField txtMaDG = new JTextField();

    private JComboBox<Khoa> cboKhoa = new JComboBox<>();
    private JComboBox<Lop> cboLop = new JComboBox<>();
    private JTextField txtTenDG = new JTextField();
    private JComboBox<String> cboGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
    private JTextField txtDiaChi = new JTextField();
    private JTextField txtEmail = new JTextField();
    private JTextField txtSdt = new JTextField();

    // ===== buttons =====
    private JButton btnThem = new JButton("Thêm");
    private JButton btnSua = new JButton("Sửa");
    private JButton btnXoa = new JButton("Xóa");
    private JButton btnLamMoi = new JButton("Làm mới");
    private JButton btnNhapFile = new JButton("Nhập file (CSV)");
    private JButton btnXuatFile = new JButton("Xuất file (CSV)");

    // ===== table =====
    private JTable tblDG;
    private DefaultTableModel model;

    public FormDocGia() {
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

        JLabel title = new JLabel("QUẢN LÝ ĐỘC GIẢ");
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

        JLabel lb = new JLabel("Thông tin độc giả");
        lb.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lb.setForeground(new Color(30, 50, 90));

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 2, 6, 2);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        int r = 0;
        r = addRow(form, gbc, r, "Mã độc giả", txtMaDG);
        r = addRow(form, gbc, r, "Khoa", cboKhoa);
        r = addRow(form, gbc, r, "Lớp", cboLop);
        r = addRow(form, gbc, r, "Tên độc giả", txtTenDG);
        r = addRow(form, gbc, r, "Giới tính", cboGioiTinh);
        r = addRow(form, gbc, r, "Địa chỉ", txtDiaChi);
        r = addRow(form, gbc, r, "Email", txtEmail);
        r = addRow(form, gbc, r, "SĐT", txtSdt);

        // ===== scroll giống FormSach =====
        JScrollPane spForm = new JScrollPane(form);
        spForm.setBorder(null);
        spForm.getViewport().setOpaque(false);
        spForm.setOpaque(false);
        spForm.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spForm.getVerticalScrollBar().setUnitIncrement(16);

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
        card.add(spForm, BorderLayout.CENTER);
        card.add(actions, BorderLayout.SOUTH);

        // ===== style input =====
        styleInput(txtMaDG);
        txtMaDG.setEditable(false);
        txtMaDG.setBackground(new Color(230, 230, 230));

        styleCombo(cboKhoa);
        styleCombo(cboLop);
        styleInput(txtTenDG);
        styleCombo(cboGioiTinh);
        styleInput(txtDiaChi);
        styleInput(txtEmail);
        styleInput(txtSdt);

        return card;
    }

    private int addRow(JPanel form, GridBagConstraints gbc, int row, String text, JComponent field) {
        JLabel lb = new JLabel(text);
        lb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lb.setAlignmentX(Component.LEFT_ALIGNMENT);

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

        JLabel lb = new JLabel("Danh sách độc giả");
        lb.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lb.setForeground(new Color(30, 50, 90));

        String[] cols = {"Mã DG", "MaKhoa", "Khoa", "MaLop", "Lớp", "Tên DG", "Giới tính", "Địa chỉ", "Email", "SĐT"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };

        tblDG = new JTable(model);
        tblDG.setRowHeight(28);
        tblDG.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblDG.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblDG.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        hideColumn(1);
        hideColumn(3);

        JScrollPane sp = new JScrollPane(tblDG);
        sp.setBorder(BorderFactory.createLineBorder(new Color(220, 230, 240)));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(lb, BorderLayout.NORTH);
        top.add(new JSeparator(), BorderLayout.SOUTH);

        card.add(top, BorderLayout.NORTH);
        card.add(sp, BorderLayout.CENTER);

        return card;
    }
    private void hideColumn(int modelIndex) {
        int viewIndex = tblDG.convertColumnIndexToView(modelIndex);
        if (viewIndex >= 0) {
            tblDG.getColumnModel().getColumn(viewIndex).setMinWidth(0);
            tblDG.getColumnModel().getColumn(viewIndex).setMaxWidth(0);
            tblDG.getColumnModel().getColumn(viewIndex).setWidth(0);
        }
    }
    // ===== styles =====
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

    // ===== getters cho Controller =====
    public JTextField getTxtSearch() { return txtSearch; }
    public JButton getBtnSearch() { return btnSearch; }

    public JButton getBtnThem() { return btnThem; }
    public JButton getBtnSua() { return btnSua; }
    public JButton getBtnXoa() { return btnXoa; }
    public JButton getBtnLamMoi() { return btnLamMoi; }
    public JButton getBtnNhapFile() { return btnNhapFile; }
    public JButton getBtnXuatFile() { return btnXuatFile; }

    public JTable getTblDG() { return tblDG; }
    public DefaultTableModel getModel() { return model; }

    public JComboBox<Khoa> getCboKhoa() { return cboKhoa; }
    public JComboBox<Lop> getCboLop() { return cboLop; }
    public JComboBox<String> getCboGioiTinh() { return cboGioiTinh; }

    public String getMaDG() { return txtMaDG.getText().trim(); }
    public String getKhoa() { return cboKhoa.getSelectedItem() == null ? "" : cboKhoa.getSelectedItem().toString(); }
    public String getLop() { return cboLop.getSelectedItem() == null ? "" : cboLop.getSelectedItem().toString(); }
    public String getTenDG() { return txtTenDG.getText().trim(); }
    public String getGioiTinh() { return cboGioiTinh.getSelectedItem() == null ? "" : cboGioiTinh.getSelectedItem().toString(); }
    public String getDiaChi() { return txtDiaChi.getText().trim(); }
    public String getEmail() { return txtEmail.getText().trim(); }
    public String getSdt() { return txtSdt.getText().trim(); }
    public Khoa getSelectedKhoa() {return (Khoa) cboKhoa.getSelectedItem();}
    public Lop getSelectedLop() {return (Lop) cboLop.getSelectedItem();}
    
    public void setMaDG(String ma) { txtMaDG.setText(ma); }

    public void clearForm() {
        txtMaDG.setText("");
        txtTenDG.setText("");
        txtDiaChi.setText("");
        txtEmail.setText("");
        txtSdt.setText("");
        if (cboGioiTinh.getItemCount() > 0) cboGioiTinh.setSelectedIndex(0);
    }
    public void setSelectedKhoa(String maKhoa) {
        for (int i = 0; i < cboKhoa.getItemCount(); i++) {
            if (cboKhoa.getItemAt(i).getMaKhoa().equals(maKhoa)) {
                cboKhoa.setSelectedIndex(i);
                break;
            }
        }
    }
    public void setSelectedLop(String maLop) {
        for (int i = 0; i < cboLop.getItemCount(); i++) {
            if (cboLop.getItemAt(i).getMaLop().equals(maLop)) {
                cboLop.setSelectedIndex(i);
                break;
            }
        }
    }
    public void setForm(String maDG, String maKhoa, String maLop, String tenDG,
                        String gioiTinh, String diaChi, String email, String sdt) {
        txtMaDG.setText(maDG);


        setSelectedKhoa(maKhoa);
        setSelectedLop(maLop);
        cboGioiTinh.setSelectedItem(gioiTinh);

        txtTenDG.setText(tenDG);
        txtDiaChi.setText(diaChi);
        txtEmail.setText(email);
        txtSdt.setText(sdt);
    }
    public void setComboboxItems(
        java.util.List<Khoa> khoas,
        java.util.List<Lop> lops) {

        cboKhoa.removeAllItems();
        for (Khoa k : khoas) cboKhoa.addItem(k);

        cboLop.removeAllItems();
        for (Lop l : lops) cboLop.addItem(l);
    }
}