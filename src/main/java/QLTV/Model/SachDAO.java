package QLTV.Model;

import QLTV.Domain.KeSach;
import QLTV.Domain.NgonNgu;
import QLTV.Domain.NhaXuatBan;
import QLTV.Domain.Sach;
import QLTV.Domain.TacGia;
import QLTV.Domain.Theloai;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class SachDAO {

    // ====== LOAD DATA FOR COMBO (hiển thị TÊN, lưu MÃ) ======
    public List<TacGia> findAllTacGiaForSach() {
        List<TacGia> list = new ArrayList<>();
        String sql = "SELECT MaTG, TenTG FROM tacgia ORDER BY MaTG";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new TacGia(rs.getString("MaTG"), rs.getString("TenTG")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public List<Theloai> findAllTheLoaiForSach() {
        List<Theloai> list = new ArrayList<>();
        String sql = "SELECT MaTL, TenTL FROM theloai ORDER BY MaTL";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Theloai(rs.getString("MaTL"), rs.getString("TenTL")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public List<NhaXuatBan> findAllNhaXuatBanForSach() {
        List<NhaXuatBan> list = new ArrayList<>();
        String sql = "SELECT MaNXB, TenNXB FROM nhaxuatban ORDER BY MaNXB";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new NhaXuatBan(rs.getString("MaNXB"), rs.getString("TenNXB")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // ✅ Ngôn ngữ
    public List<NgonNgu> findAllNgonNguForSach() {
        List<NgonNgu> list = new ArrayList<>();
        String sql = "SELECT MaNN, TenNN FROM ngonngu ORDER BY MaNN";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new NgonNgu(rs.getString("MaNN"), rs.getString("TenNN")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // ✅ Vị trí (bảng KeSach)
    public List<KeSach> findAllViTriForSach() {
        List<KeSach> list = new ArrayList<>();
        String sql = "SELECT MaViTri, TenKe, MoTa FROM kesach ORDER BY MaViTri";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new KeSach(
                        rs.getString("MaViTri"),
                        rs.getString("TenKe"),
                        rs.getString("MoTa")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // ====== CHECK TRÙNG ======
    public boolean checkTrungTenSach(String tenSach) {
        String sql = "SELECT 1 FROM sach WHERE TenSach = ? LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tenSach);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean checkTrungTenSachKhacMa(String tenSach, String maSach) {
        String sql = "SELECT 1 FROM sach WHERE TenSach = ? AND MaSach <> ? LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tenSach);
            ps.setString(2, maSach);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // ====== CRUD ======
    public List<Sach> findAll() {
        String sql = "SELECT MaSach, MaTG, MaNXB, MaTL, TenSach, NamXB, SoLuong, MaNN, MaViTri " +
                     "FROM sach ORDER BY MaSach";
        List<Sach> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(map(rs));

        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }

    public List<Sach> search(String keyword) {
        String sql =
            "SELECT s.MaSach, s.MaTG, s.MaNXB, s.MaTL, s.TenSach, s.NamXB, s.SoLuong, s.MaNN, s.MaViTri " +
            "FROM sach s " +
            "JOIN tacgia tg ON s.MaTG = tg.MaTG " +
            "JOIN nhaxuatban nxb ON s.MaNXB = nxb.MaNXB " +
            "JOIN theloai tl ON s.MaTL = tl.MaTL " +
            "JOIN ngonngu nn ON s.MaNN = nn.MaNN " +
            "JOIN kesach ks ON s.MaViTri = ks.MaViTri " +
            "WHERE s.MaSach LIKE ? OR s.TenSach LIKE ? OR tg.TenTG LIKE ? OR nxb.TenNXB LIKE ? " +
            "OR tl.TenTL LIKE ? OR nn.TenNN LIKE ? OR ks.TenKe LIKE ? OR s.NamXB = ? " +
            "ORDER BY s.MaSach";

        List<Sach> list = new ArrayList<>();
        String k = "%" + keyword + "%";
        boolean isYear = keyword != null && keyword.trim().matches("\\d{4}");

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, k);
            ps.setString(2, k);
            ps.setString(3, k);
            ps.setString(4, k);
            ps.setString(5, k);
            ps.setString(6, k);
            ps.setString(7, k);
            ps.setInt(8, isYear ? Integer.parseInt(keyword.trim()) : 0);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "SEARCH ERROR: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public int insert(Sach s) {
        String sql = "INSERT INTO sach(MaSach, MaTG, MaNXB, MaTL, TenSach, NamXB, SoLuong, MaNN, MaViTri) " +
                     "VALUES(?,?,?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            bind(ps, s);
            return ps.executeUpdate();

        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int update(Sach s) {
        String sql = "UPDATE sach SET MaTG=?, MaNXB=?, MaTL=?, TenSach=?, NamXB=?, SoLuong=?, MaNN=?, MaViTri=? " +
                     "WHERE MaSach=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getMatg());
            ps.setString(2, s.getManxb());
            ps.setString(3, s.getMatl());
            ps.setString(4, s.getTensach());
            ps.setInt(5, s.getNamxb());
            if (s.getSoluong() == null) ps.setObject(6, null);
            else ps.setInt(6, s.getSoluong());
            ps.setString(7, s.getMann());
            ps.setString(8, s.getMavitri());
            ps.setString(9, s.getMasach());

            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int delete(String maSach) {
        String sql = "DELETE FROM sach WHERE MaSach=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maSach);
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    // ====== MaSach tự tăng an toàn ======
    public String taoMaSachMoi() {
        String sql = "SELECT MAX(CAST(SUBSTRING(MaSach, 2) AS UNSIGNED)) AS maxSo FROM sach";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int next = 1;
            if (rs.next()) {
                int max = rs.getInt("maxSo");
                next = max + 1;
            }
            return String.format("S%03d", next);

        } catch (Exception e) { e.printStackTrace(); }
        return "S001";
    }

    private Sach map(ResultSet rs) throws Exception {
        return new Sach(
                rs.getString("MaSach"),
                rs.getString("MaTG"),
                rs.getString("MaNXB"),
                rs.getString("MaTL"),
                rs.getString("TenSach"),
                rs.getInt("NamXB"),
                (Integer) rs.getObject("SoLuong"),
                rs.getString("MaNN"),
                rs.getString("MaViTri")
        );
    }

    private void bind(PreparedStatement ps, Sach s) throws Exception {
        ps.setString(1, s.getMasach());
        ps.setString(2, s.getMatg());
        ps.setString(3, s.getManxb());
        ps.setString(4, s.getMatl());
        ps.setString(5, s.getTensach());
        ps.setInt(6, s.getNamxb());
        if (s.getSoluong() == null) ps.setObject(7, null);
        else ps.setInt(7, s.getSoluong());
        ps.setString(8, s.getMann());
        ps.setString(9, s.getMavitri());
    }
}
