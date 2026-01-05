/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
/**
 *
 * @author khanh
 */
public class FormThongKe extends JPanel {
    private final Color PRIMARY = new Color(30, 136, 229);
    private final Color BG = new Color(245, 247, 250);
    private final Color CARD = Color.WHITE;

    // ===== BUTTON =====
    private JButton btnSachMuonNhieu = new JButton("Sách mượn nhiều nhất");
    private JButton btnDGMuonNhieu = new JButton("Độc giả mượn nhiều");
    private JButton btnQuaHan = new JButton("Phiếu mượn quá hạn");

    // ===== COUNTER =====
    private JLabel lbTongSach = new JLabel("0");
    private JLabel lbTongDG = new JLabel("0");
    private JLabel lbDangMuon = new JLabel("0");

    // ===== TABLE =====
    private JTable table;
    private DefaultTableModel model;

    public FormThongKe() {
        setLayout(new BorderLayout(15, 15));
        setBackground(BG);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(createTop(), BorderLayout.NORTH);
        add(createTable(), BorderLayout.CENTER);

        styleButtons();
    }

    // ===== TOP =====
    private JPanel createTop() {
        JPanel p = new JPanel(new GridLayout(2, 1, 15, 15));
        p.setOpaque(false);
        p.add(createCounter());
        p.add(createButtons());
        return p;
    }

    // ===== COUNTER =====
    private JPanel createCounter() {
        JPanel p = new JPanel(new GridLayout(1, 3, 15, 15));
        p.setOpaque(false);
        p.add(counterBox("TỔNG SÁCH", lbTongSach));
        p.add(counterBox("TỔNG ĐỘC GIẢ", lbTongDG));
        p.add(counterBox("SÁCH ĐANG MƯỢN", lbDangMuon));
        return p;
    }

    private JPanel counterBox(String title, JLabel value) {
        JPanel p = new JPanel(new BorderLayout(5, 5));
        p.setBackground(CARD);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel lbTitle = new JLabel(title);
        lbTitle.setForeground(Color.GRAY);
        lbTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));

        value.setFont(new Font("Segoe UI", Font.BOLD, 28));
        value.setForeground(new Color(33, 33, 33));

        p.add(lbTitle, BorderLayout.NORTH);
        p.add(value, BorderLayout.CENTER);
        return p;
    }

    // ===== BUTTON =====
    private JPanel createButtons() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        p.setOpaque(false);
        p.add(btnSachMuonNhieu);
        p.add(btnDGMuonNhieu);
        p.add(btnQuaHan);
        return p;
    }

    private void styleButtons() {
        JButton[] btns = {btnSachMuonNhieu, btnDGMuonNhieu, btnQuaHan};
        for (JButton b : btns) {
            b.setBackground(PRIMARY);
            b.setForeground(Color.BLACK);
            b.setFont(new Font("Segoe UI", Font.BOLD, 13));
            b.setFocusPainted(false);
            b.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        }
    }

    // ===== TABLE =====
    private JScrollPane createTable() {
        model = new DefaultTableModel(new Object[]{"", "", ""}, 0);
        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setGridColor(new Color(230,230,230));
        table.setSelectionBackground(new Color(224, 242, 254));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(230,230,230));

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));
        return sp;
    }

    // ===== GETTER =====
    public JButton getBtnSachMuonNhieu() { return btnSachMuonNhieu; }
    public JButton getBtnDGNhieu() { return btnDGMuonNhieu; }
    public JButton getBtnQuaHan() { return btnQuaHan; }
    public DefaultTableModel getModel() { return model; }

    // ===== SETTER =====
    public void setTongSach(int n) { lbTongSach.setText(String.valueOf(n)); }
    public void setTongDG(int n) { lbTongDG.setText(String.valueOf(n)); }
    public void setDangMuon(int n) { lbDangMuon.setText(String.valueOf(n)); }

}
