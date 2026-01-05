/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Views;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author dinhd
 */
public class FormDangnhap extends JFrame{
     private JTextField txtUser = new JTextField();
    private JPasswordField txtPass = new JPasswordField();
    private JButton btnLogin = new JButton("Đăng nhập");
    private JButton btnExit  = new JButton("Thoát");
    private JLabel lbStatus  = new JLabel(" ");

    public FormDangnhap() {
        setTitle("Đăng nhập - Thư viện UTT");
        setSize(420, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(new Color(245, 247, 250));
        setContentPane(root);

        RoundedPanel card = new RoundedPanel(20);
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(26, 26, 26, 26));
        card.setPreferredSize(new Dimension(340, 420));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel lbTitle = new JLabel("Admin Login");
        lbTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lbTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbSub = new JLabel("Đăng nhập bằng tài khoản admin");
        lbSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbSub.setForeground(new Color(120, 120, 120));
        lbSub.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(lbTitle);
        card.add(Box.createVerticalStrut(6));
        card.add(lbSub);
        card.add(Box.createVerticalStrut(26));

        card.add(label("Username"));
        card.add(Box.createVerticalStrut(6));
        styleInput(txtUser);
        card.add(txtUser);
        card.add(Box.createVerticalStrut(16));

        card.add(label("Password"));
        card.add(Box.createVerticalStrut(6));
        styleInput(txtPass);
        card.add(txtPass);
        card.add(Box.createVerticalStrut(18));

        stylePrimaryButton(btnLogin);
        styleGhostButton(btnExit);

        btnLogin.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnExit.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(btnLogin);
        card.add(Box.createVerticalStrut(10));
        card.add(btnExit);

        card.add(Box.createVerticalStrut(14));
        lbStatus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbStatus.setForeground(new Color(200, 60, 60));
        lbStatus.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lbStatus);

        // Enter để login
        txtPass.addActionListener(e -> btnLogin.doClick());

        root.add(card);
    }

    private JLabel label(String text) {
        JLabel lb = new JLabel(text);
        lb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lb.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lb;
    }

    private void styleInput(JTextField f) {
        f.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 230), 1, true),
                new EmptyBorder(10, 12, 10, 12)
        ));
        f.setBackground(new Color(250, 252, 255));
    }

    private void stylePrimaryButton(JButton b) {
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(45, 126, 255));
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void styleGhostButton(JButton b) {
        b.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        b.setFocusPainted(false);
        b.setForeground(new Color(45, 126, 255));
        b.setBackground(Color.WHITE);
        b.setBorder(BorderFactory.createLineBorder(new Color(45, 126, 255), 1, true));
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public String getUsername() { return txtUser.getText().trim(); }
    public String getPassword() { return new String(txtPass.getPassword()); }
    public JButton getBtnLogin() { return btnLogin; }
    public JButton getBtnExit() { return btnExit; }
    public void showStatus(String msg) { lbStatus.setText(msg == null ? " " : msg); }

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
}
