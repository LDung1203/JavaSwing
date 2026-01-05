/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Views;

import QLTV.Domain.Dangnhap;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author dinhd
 */
public class FormTrangchu extends JFrame {

    private final JPanel pnlContent = new JPanel(new BorderLayout());

    private final JLabel lbTaiKhoan = new JLabel();
    private final JLabel lbQuyen = new JLabel();

    private static final Color C_PRIMARY = new Color(25, 118, 210);
    private static final Color C_BG = new Color(245, 247, 250);
    private static final Color C_MENU_BG = new Color(236, 240, 246);
    private static final Color C_BTN = Color.WHITE;
    private static final Color C_BTN_HOVER = new Color(232, 240, 254);
    private static final Color C_BORDER = new Color(210, 218, 230);
    private static final Color C_TEXT = new Color(33, 37, 41);
    private static final Color C_SUBTEXT = new Color(90, 98, 110);

    public FormTrangchu(Dangnhap tk) {
        setTitle("Quản lý thư viện - UTT");
        setSize(1050, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createHeader(tk), BorderLayout.NORTH);
        add(createMenuLeft(tk), BorderLayout.WEST);

        pnlContent.setBackground(C_BG);
        pnlContent.setBorder(new EmptyBorder(14, 14, 14, 14));
        add(pnlContent, BorderLayout.CENTER);

        setContent(createHomePanel(tk));

        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private JPanel createHeader(Dangnhap tk) {
        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(1000, 72));
        header.setBackground(C_PRIMARY);
        header.setBorder(new EmptyBorder(10, 16, 10, 16));

        JLabel logo = new JLabel();
        ImageIcon icon = loadIcon("/QLTV/Resource/logo.png");
        if (icon != null) {
            Image img = icon.getImage().getScaledInstance(44, 44, Image.SCALE_SMOOTH);
            logo.setIcon(new ImageIcon(img));
        } else {
            logo.setIcon(renderIcon(new HomeIcon(44, Color.WHITE), 44, 44));
        }

        JLabel title = new JLabel("HỆ THỐNG QUẢN LÝ THƯ VIỆN");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 4));
        left.setOpaque(false);
        left.add(logo);
        left.add(title);

        JLabel admin = new JLabel("Xin chào, " + safe(tk.getName()));
        admin.setForeground(Color.WHITE);
        admin.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        header.add(left, BorderLayout.WEST);
        header.add(admin, BorderLayout.EAST);
        return header;
    }
    private JPanel createMenuLeft(Dangnhap tk) {
        JPanel menu = new JPanel(new BorderLayout());
        menu.setPreferredSize(new Dimension(270, 0));
        menu.setBackground(C_MENU_BG);
        menu.setBorder(new EmptyBorder(12, 12, 12, 12));

        JPanel btnPanel = new JPanel(new GridLayout(0, 1, 0, 10));
        btnPanel.setOpaque(false);

        JButton btnHome       = menuButton("Trang chủ", new HomeIcon(18, C_PRIMARY));

        JButton btnSach       = menuButton("Quản lý Sách", new BookIcon(18, new Color(25,118,210)));
        JButton btnTheLoai    = menuButton("Quản lý Thể Loại", new TagIcon(18, new Color(2,136,209)));
        JButton btnTacGia     = menuButton("Quản lý Tác Giả", new PenIcon(18, new Color(67,160,71)));
        JButton btnNXB        = menuButton("Quản lý Nhà Xuất Bản", new BuildingIcon(18, new Color(255,143,0)));
        JButton btnNgonNgu    = menuButton("Quản lý Ngôn Ngữ Sách", new GlobeIcon(18, new Color(171,71,188)));

        JButton btnDocGia     = menuButton("Quản lý Độc Giả", new UsersIcon(18, new Color(21,101,192)));
        JButton btnNhanVien   = menuButton("Quản lý Nhân Viên", new UserIcon(18, new Color(0,121,107)));
        JButton btnMuonTra    = menuButton("Quản lý Mượn Trả", new SwapIcon(18, new Color(230,74,25)));
        JButton btnViTri      = menuButton("Quản lý Kệ Sách", new ShelfIcon(18, new Color(109,76,65)));
        JButton btnKhoa       = menuButton("Quản lý Khoa", new SchoolIcon(18, new Color(94,53,177)));
        JButton btnLop        = menuButton("Quản lý Lớp", new ClassIcon(18, new Color(30,136,229)));
        JButton btnTheThuVien = menuButton("Quản lý Thẻ Thư Viện", new CardIcon(18, new Color(142,36,170)));
        JButton btnThongKe    = menuButton("Thống Kê", new ChartIcon(18, Color.BLACK));

        btnPanel.add(btnHome);

        btnPanel.add(btnSach);
        btnPanel.add(btnTheLoai);
        btnPanel.add(btnTacGia);
        btnPanel.add(btnNXB);
        btnPanel.add(btnNgonNgu);

        btnPanel.add(btnDocGia);
        btnPanel.add(btnNhanVien);
        btnPanel.add(btnMuonTra);
        btnPanel.add(btnViTri);
        btnPanel.add(btnKhoa);
        btnPanel.add(btnLop);
        btnPanel.add(btnTheThuVien);
        btnPanel.add(btnThongKe);

        JScrollPane sp = new JScrollPane(btnPanel);
        sp.setBorder(null);
        sp.getViewport().setOpaque(false);
        sp.setOpaque(false);
        sp.getVerticalScrollBar().setUnitIncrement(16);

        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new GridLayout(0, 1, 0, 6));
        info.setBorder(new EmptyBorder(10, 2, 0, 2));

        lbTaiKhoan.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbQuyen.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbTaiKhoan.setForeground(C_TEXT);
        lbQuyen.setForeground(C_TEXT);

        lbTaiKhoan.setText("Tài khoản: " + safe(tk.getUsername()));
        lbQuyen.setText("Email: " + safe(tk.getEmail())); 

        info.add(new JSeparator());
        info.add(lbTaiKhoan);
        info.add(lbQuyen);

        btnHome.addActionListener(e -> setContent(createHomePanel(tk)));

        btnSach.addActionListener(e -> {
            FormSach p = new FormSach();
            new QLTV.Controller.SachController(p);
            setContent(p);
        });

        btnTheLoai.addActionListener(e -> {
            FormTheloai p = new FormTheloai();
            new QLTV.Controller.TheloaiController(p);
            setContent(p);
        });

        btnTacGia.addActionListener(e -> {
            FormTacGia p = new FormTacGia();
            new QLTV.Controller.TacGiaController(p);
            setContent(p);
        });

        btnNXB.addActionListener(e -> {
            FormNXB p = new FormNXB();
            new QLTV.Controller.NhaXuatBanController(p);
            setContent(p);
        });

        btnNgonNgu.addActionListener(e -> {
            FormNgonNgu p = new FormNgonNgu();
            new QLTV.Controller.NgonNguController(p);
            setContent(p);
        });

        btnDocGia.addActionListener(e -> {
            FormDocGia p = new FormDocGia();
            new QLTV.Controller.DocGiaController(p);
            setContent(p);
        });

        btnNhanVien.addActionListener(e -> {
            FormNhanVien p = new FormNhanVien();
            new QLTV.Controller.NhanVienController(p);
            setContent(p);
        });

        btnMuonTra.addActionListener(e -> {
            pnlContent.removeAll();

            FormMuonTra p = new FormMuonTra();
            new QLTV.Controller.MuonTraController(p);

            pnlContent.add(p, BorderLayout.CENTER);
            pnlContent.revalidate();
            pnlContent.repaint();
        });

        btnViTri.addActionListener(e -> {
            FormKeSach p = new FormKeSach();
            new QLTV.Controller.KeSachController(p);
            setContent(p);
        });

        btnKhoa.addActionListener(e -> {
            FormKhoa p = new FormKhoa();
            new QLTV.Controller.KhoaController(p);
            setContent(p);
        });

        btnLop.addActionListener(e -> {
            try {
                FormLop p = new FormLop();
                new QLTV.Controller.LopController(p);
                setContent(p);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Lỗi mở form Lớp: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnTheThuVien.addActionListener(e -> {
            FormTheThuVien p = new FormTheThuVien();
            new QLTV.Controller.TheThuVienController(p);
            setContent(p);
        });

        btnThongKe.addActionListener(e -> {
            try {
                FormThongKe ftk = new FormThongKe();
                new QLTV.Controller.ThongKeController(ftk);
                setContent(ftk);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi mở form: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        menu.add(sp, BorderLayout.CENTER);
        menu.add(info, BorderLayout.SOUTH);
        return menu;
    }

    private JPanel createHomePanel(Dangnhap tk) {
        JPanel root = new JPanel(new BorderLayout());
        root.setOpaque(false);

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

        JLabel welcome = new JLabel("Trang chủ");
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcome.setForeground(C_TEXT);

        JLabel sub = new JLabel("Xin chào: " + safe(tk.getName()));
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sub.setForeground(C_SUBTEXT);

        top.add(welcome);
        top.add(Box.createVerticalStrut(4));
        top.add(sub);
        top.setBorder(new EmptyBorder(6, 6, 12, 6));

        JPanel main = new JPanel(new GridBagLayout());
        main.setOpaque(false);
        main.setBorder(new EmptyBorder(6, 6, 6, 6));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);




        JPanel left = cardPanel("Thông tin lớp & đề tài", new SchoolIcon(18, C_PRIMARY));
        left.setLayout(new BorderLayout());

        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new GridLayout(0, 1, 0, 10));
        info.setBorder(new EmptyBorder(12, 12, 12, 12));

        String lop = " 74DCHT23";
        String mon = " Lập trình Java (Swing)";
        String deTai = " Quản lý thư viện";
        String gv = " Trần Văn Tâm ";

        info.add(infoRow("Lớp:", lop));
        info.add(infoRow("Môn học:", mon));
        info.add(infoRow("Đề tài:", deTai));
        info.add(infoRow("Giảng viên hướng dẫn:", gv));

        JPanel note = new JPanel(new BorderLayout());
        note.setOpaque(false);
        note.setBorder(new EmptyBorder(0, 12, 12, 12));

        left.add(info, BorderLayout.NORTH);
        left.add(note, BorderLayout.CENTER);

        JPanel right = cardPanel("Thành viên nhóm", new UsersIcon(18, C_PRIMARY));
        right.setLayout(new BorderLayout());
        right.setBorder(new EmptyBorder(0,0,0,0));

        String[] cols = {"STT", "Họ tên", "MSSV", "Vai trò", "Email"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        Object[][] members = {
                {1, "Đinh Hoàng Đức", "74DCHT22264", "Đăng nhập, trang chủ, quản lý sách, quản lý thế loại", "dinhduck98@gmail.com"},
                {2, "Lưu Đức Anh Dũng",   "74DCHT22203", "Thống kê, quản lý mượn trả", "2005luuducanhdung@gmail.com"},
                {3, "Nguyễn Ngọc Bích",     "74DCHT22190", "Quản lý tác giả, quản lý nhà xuất bản, quản lý nhân viên","ngocbich.study@gmail.com"},
                {4, "Nguyễn Nam	Khánh",   "74DCHT22224", "Quản lý ngôn ngữ sách, quản lý độc giả, quản lý kệ sách",  "nammkhanhnguyen@gmail.com"},
                {5, "Nguyễn Ngọc Linh",   "74DCHT22276", "Quản lý lớp, quản lý khoa, quản lý thẻ thư viện",  "ngnglinhhh22@gmail.com"},
        };
        for (Object[] r : members) model.addRow(r);

        JTable table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane tsp = new JScrollPane(table);
        tsp.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel stats = new JPanel(new GridLayout(1, 3, 10, 10));
        stats.setOpaque(false);
        stats.setBorder(new EmptyBorder(0, 12, 12, 12));

        stats.add(miniStat("Số thành viên", String.valueOf(members.length), new UsersIcon(16, new Color(21,101,192))));
        stats.add(miniStat("Phiên bản", "v1.0", new TagIcon(16, new Color(2,136,209))));
        stats.add(miniStat("Trạng thái", "Đang phát triển", new PenIcon(16, new Color(67,160,71))));

        right.add(tsp, BorderLayout.CENTER);
        right.add(stats, BorderLayout.SOUTH);

        gbc.gridx = 0;
        gbc.weightx = 0.33;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 0, 0, 7); 
        main.add(left, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.67;
        gbc.insets = new Insets(0, 7, 0, 0);
        main.add(right, gbc);
        root.add(top, BorderLayout.NORTH);
        root.add(main, BorderLayout.CENTER);
        return root;
    }

    private JPanel infoRow(String k, String v) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);

        JLabel lk = new JLabel(k);
        lk.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lk.setForeground(C_TEXT);

        JLabel lv = new JLabel(v);
        lv.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lv.setForeground(C_SUBTEXT);

        row.add(lk, BorderLayout.WEST);
        row.add(lv, BorderLayout.CENTER);
        return row;
    }

    private JPanel cardPanel(String title, Icon icon) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(C_BORDER, 1));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(12, 12, 10, 12));

        JLabel lbTitle = new JLabel(title);
        lbTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lbTitle.setForeground(C_TEXT);

        JLabel lbIcon = new JLabel(renderIcon(icon, 18, 18));
        lbIcon.setBorder(new EmptyBorder(0, 0, 0, 8));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        left.setOpaque(false);
        left.add(lbIcon);
        left.add(lbTitle);

        header.add(left, BorderLayout.WEST);
        card.add(header, BorderLayout.NORTH);

        return card;
    }

    private JPanel miniStat(String title, String value, Icon icon) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(248, 250, 253));
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                new EmptyBorder(10, 10, 10, 10)
        ));

        JLabel ic = new JLabel(renderIcon(icon, 16, 16));
        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        t.setForeground(C_SUBTEXT);

        JLabel v = new JLabel(value);
        v.setFont(new Font("Segoe UI", Font.BOLD, 14));
        v.setForeground(C_TEXT);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        top.setOpaque(false);
        top.add(ic);
        top.add(Box.createHorizontalStrut(8));
        top.add(t);

        p.add(top, BorderLayout.NORTH);
        p.add(v, BorderLayout.CENTER);

        return p;
    }

    private JButton menuButton(String text, Icon icon) {
        JButton b = new JButton(text);
        b.setIcon(renderIcon(icon, 18, 18));
        b.setIconTextGap(10);

        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setFocusPainted(false);
        b.setBackground(C_BTN);
        b.setForeground(C_TEXT);
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(C_BORDER, 1),
                new EmptyBorder(10, 12, 10, 12)
        ));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                b.setBackground(C_BTN_HOVER);
                b.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(160, 180, 210), 1),
                        new EmptyBorder(10, 12, 10, 12)
                ));
            }
            @Override public void mouseExited(MouseEvent e) {
                b.setBackground(C_BTN);
                b.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(C_BORDER, 1),
                        new EmptyBorder(10, 12, 10, 12)
                ));
            }
        });

        return b;
    }

    private void setContent(JComponent comp) {
        pnlContent.removeAll();
        pnlContent.add(comp, BorderLayout.CENTER);
        pnlContent.revalidate();
        pnlContent.repaint();
    }

    private void showScreen(String title) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);

        JLabel lb = new JLabel(title, SwingConstants.CENTER);
        lb.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lb.setForeground(C_TEXT);

        JLabel hint = new JLabel("Tính năng này đang được phát triển...", SwingConstants.CENTER);
        hint.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        hint.setForeground(C_SUBTEXT);

        p.add(lb, BorderLayout.CENTER);
        p.add(hint, BorderLayout.SOUTH);
        p.setBorder(new EmptyBorder(30, 10, 30, 10));

        setContent(p);
    }

    private String safe(String s) {
        return (s == null || s.trim().isEmpty()) ? "-" : s.trim();
    }

    private ImageIcon loadIcon(String path) {
        try {
            java.net.URL url = getClass().getResource(path);
            return (url == null) ? null : new ImageIcon(url);
        } catch (Exception e) {
            return null;
        }
    }

    private ImageIcon renderIcon(Icon ic, int w, int h) {
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bi.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ic.paintIcon(null, g, 0, 0);
        g.dispose();
        return new ImageIcon(bi);
    }

    private static abstract class BaseIcon implements Icon {
        protected final int size;
        protected final Color color;
        protected final int stroke;

        protected BaseIcon(int size, Color color, int stroke) {
            this.size = size;
            this.color = color;
            this.stroke = stroke;
        }

        @Override public int getIconWidth() { return size; }
        @Override public int getIconHeight() { return size; }

        protected Graphics2D g2(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            return g2;
        }
    }

    private static class HomeIcon extends BaseIcon {
        HomeIcon(int size, Color color) { super(size, color, 2); }
        @Override public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = g2(g);
            int s = size;
            int px = x, py = y;
            int roofH = (int)(s * 0.45);

            g2.drawLine(px + s/2, py + 2, px + 2, py + roofH);
            g2.drawLine(px + s/2, py + 2, px + s - 2, py + roofH);

            int bw = s - 8, bh = s - roofH - 6;
            int bx = px + 4, by = py + roofH;
            g2.drawRoundRect(bx, by, bw, bh, 6, 6);

            int dw = s/5, dh = bh/2;
            g2.drawRoundRect(px + s/2 - dw/2, by + bh - dh - 2, dw, dh, 6, 6);

            g2.dispose();
        }
    }

    private static class BookIcon extends BaseIcon {
        BookIcon(int size, Color color) { super(size, color, 2); }
        @Override public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = g2(g);
            int s = size;
            int px = x, py = y;

            g2.drawRoundRect(px + 2, py + 3, s - 6, s - 6, 6, 6);
            g2.drawLine(px + s/2, py + 3, px + s/2, py + s - 3);
            g2.drawLine(px + 5, py + 6, px + s/2 - 3, py + 6);
            g2.drawLine(px + 5, py + 9, px + s/2 - 3, py + 9);

            g2.dispose();
        }
    }

    private static class TagIcon extends BaseIcon {
        TagIcon(int size, Color color) { super(size, color, 2); }
        @Override public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = g2(g);
            int s = size;
            int px = x, py = y;

            Polygon p = new Polygon();
            p.addPoint(px + 3, py + s/2);
            p.addPoint(px + s/2, py + 3);
            p.addPoint(px + s - 3, py + 3);
            p.addPoint(px + s - 3, py + s - 3);
            p.addPoint(px + s/2, py + s - 3);
            g2.drawPolygon(p);
            g2.drawOval(px + s/2 - 2, py + s/2 - 2, 4, 4);

            g2.dispose();
        }
    }

    private static class PenIcon extends BaseIcon {
        PenIcon(int size, Color color) { super(size, color, 2); }
        @Override public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = g2(g);
            int s = size;
            int px = x, py = y;

            g2.drawLine(px + 4, py + s - 5, px + s - 5, py + 4);
            g2.drawLine(px + 6, py + s - 3, px + 9, py + s - 3);
            g2.drawLine(px + s - 3, py + 7, px + s - 3, py + 10);

            g2.dispose();
        }
    }

    private static class BuildingIcon extends BaseIcon {
        BuildingIcon(int size, Color color) { super(size, color, 2); }
        @Override public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = g2(g);
            int s = size;
            int px = x, py = y;

            g2.drawRoundRect(px + 4, py + 3, s - 8, s - 6, 6, 6);
            for (int i = 0; i < 3; i++) {
                int wx = px + 7 + i * (s - 18) / 2;
                g2.drawLine(wx, py + 8, wx, py + s - 7);
            }
            g2.drawLine(px + 7, py + 10, px + s - 7, py + 10);
            g2.drawLine(px + 7, py + 14, px + s - 7, py + 14);

            g2.dispose();
        }
    }

    private static class GlobeIcon extends BaseIcon {
        GlobeIcon(int size, Color color) { super(size, color, 2); }
        @Override public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = g2(g);
            int s = size;
            int px = x, py = y;

            g2.drawOval(px + 3, py + 3, s - 6, s - 6);
            g2.drawLine(px + s/2, py + 3, px + s/2, py + s - 3);
            g2.drawOval(px + 6, py + s/2 - 5, s - 12, 10);
            g2.drawOval(px + 6, py + s/2 - 2, s - 12, 4);

            g2.dispose();
        }
    }

    private static class UsersIcon extends BaseIcon {
        UsersIcon(int size, Color color) { super(size, color, 2); }
        @Override public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = g2(g);
            int s = size, px = x, py = y;

            g2.drawOval(px + 3, py + 4, 6, 6);
            g2.drawOval(px + 9, py + 4, 6, 6);
            g2.drawRoundRect(px + 2, py + 11, 14, 6, 6, 6);

            g2.dispose();
        }
    }

    private static class UserIcon extends BaseIcon {
        UserIcon(int size, Color color) { super(size, color, 2); }
        @Override public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = g2(g);
            int s = size, px = x, py = y;

            g2.drawOval(px + s/2 - 4, py + 4, 8, 8);
            g2.drawRoundRect(px + 4, py + 12, s - 8, 6, 8, 8);

            g2.dispose();
        }
    }

    private static class SwapIcon extends BaseIcon {
        SwapIcon(int size, Color color) { super(size, color, 2); }
        @Override public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = g2(g);
            int s = size, px = x, py = y;

            g2.drawLine(px + 3, py + 6, px + s - 6, py + 6);
            g2.drawLine(px + s - 6, py + 6, px + s - 9, py + 4);
            g2.drawLine(px + s - 6, py + 6, px + s - 9, py + 8);

            g2.drawLine(px + s - 3, py + s - 6, px + 6, py + s - 6);
            g2.drawLine(px + 6, py + s - 6, px + 9, py + s - 8);
            g2.drawLine(px + 6, py + s - 6, px + 9, py + s - 4);

            g2.dispose();
        }
    }

    private static class ShelfIcon extends BaseIcon {
        ShelfIcon(int size, Color color) { super(size, color, 2); }
        @Override public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = g2(g);
            int s = size, px = x, py = y;

            g2.drawRoundRect(px + 3, py + 4, s - 6, s - 8, 6, 6);
            g2.drawLine(px + 5, py + 9, px + s - 5, py + 9);
            g2.drawLine(px + 5, py + 13, px + s - 5, py + 13);

            g2.dispose();
        }
    }

    private static class SchoolIcon extends BaseIcon {
        SchoolIcon(int size, Color color) { super(size, color, 2); }
        @Override public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = g2(g);
            int s = size, px = x, py = y;

            Polygon roof = new Polygon();
            roof.addPoint(px + s/2, py + 3);
            roof.addPoint(px + 3, py + 8);
            roof.addPoint(px + s - 3, py + 8);
            g2.drawPolygon(roof);

            g2.drawRoundRect(px + 4, py + 8, s - 8, s - 7, 6, 6);
            g2.drawLine(px + 7, py + 12, px + s - 7, py + 12);

            g2.dispose();
        }
    }

    private static class ClassIcon extends BaseIcon {
        ClassIcon(int size, Color color) { super(size, color, 2); }
        @Override public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = g2(g);
            int s = size, px = x, py = y;

            g2.drawRoundRect(px + 3, py + 4, s - 6, s - 8, 6, 6);
            g2.drawLine(px + 6, py + 9, px + s - 6, py + 9);
            g2.drawLine(px + 6, py + 13, px + s - 10, py + 13);

            g2.dispose();
        }
    }

    private static class CardIcon extends BaseIcon {
        CardIcon(int size, Color color) { super(size, color, 2); }
        @Override public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = g2(g);
            int s = size, px = x, py = y;

            g2.drawRoundRect(px + 3, py + 5, s - 6, s - 10, 8, 8);
            g2.drawLine(px + 5, py + 9, px + s - 5, py + 9);
            g2.drawLine(px + 6, py + 13, px + s/2, py + 13);

            g2.dispose();
        }
    }

    private static class ChartIcon extends BaseIcon {
        ChartIcon(int size, Color color) { super(size, color, 2); }
        @Override public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = g2(g);
            int s = size, px = x, py = y;

            g2.drawLine(px + 3, py + s - 3, px + s - 3, py + s - 3);
            g2.drawLine(px + 3, py + s - 3, px + 3, py + 3);

            g2.drawLine(px + 6, py + s - 5, px + 6, py + 12);
            g2.drawLine(px + 10, py + s - 5, px + 10, py + 9);
            g2.drawLine(px + 14, py + s - 5, px + 14, py + 6);

            g2.dispose();
        }
    }
}