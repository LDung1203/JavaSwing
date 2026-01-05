/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Model;

import QLTV.Domain.TheThuVien;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Admin
 */
public class TheThuVienDAO {

    public List<TheThuVien> findAll() {
        String sql = "SELECT MaThe, MaDG, NgayCap, NgayHetHan, TrangThai FROM thethuvien";
        List<TheThuVien> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    public List<TheThuVien> search(String keyword) {
        String sql = "SELECT MaThe, MaDG, NgayCap, NgayHetHan, TrangThai FROM thethuvien " +
                     "WHERE MaThe LIKE ? OR MaDG LIKE ? OR TrangThai LIKE ?";
        String k = "%" + keyword + "%";
        List<TheThuVien> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, k);
            ps.setString(2, k);
            ps.setString(3, k);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public int insert(TheThuVien t) {
        String sql = "INSERT INTO thethuvien(MaThe, MaDG, NgayCap, NgayHetHan, TrangThai) VALUES(?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getMaThe());
            ps.setString(2, t.getMaDG());
            ps.setDate(3, t.getNgayCap());
            ps.setDate(4, t.getNgayHetHan());
            ps.setString(5, t.getTrangThai());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int update(TheThuVien t) {
        String sql = "UPDATE thethuvien SET MaDG=?, NgayCap=?, NgayHetHan=?, TrangThai=? WHERE MaThe=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getMaDG());
            ps.setDate(2, t.getNgayCap());
            ps.setDate(3, t.getNgayHetHan());
            ps.setString(4, t.getTrangThai());
            ps.setString(5, t.getMaThe());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int delete(String maThe) {
        String sql = "DELETE FROM thethuvien WHERE MaThe=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maThe);
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    // ===== mã tự tăng =====
    public String taoMaTheMoi() {
        String sql = "SELECT MaThe FROM thethuvien ORDER BY MaThe DESC LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (!rs.next()) return "TTV001";
            String maCu = rs.getString("MaThe"); // TTV005
            int so = Integer.parseInt(maCu.substring(3));
            so++;
            return String.format("TTV%03d", so);

        } catch (Exception e) { e.printStackTrace(); }
        return "TTV001";
    }

    // ===== check trùng =====
    public boolean existsMaThe(String maThe) {
        String sql = "SELECT 1 FROM thethuvien WHERE MaThe=? LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maThe);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // 1 độc giả chỉ có 1 thẻ (nếu bạn muốn luật này)
    public boolean existsMaDG(String maDG, String excludeMaThe) {
        String sql = "SELECT 1 FROM thethuvien WHERE MaDG=? AND MaThe<>? LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maDG);
            ps.setString(2, excludeMaThe == null ? "" : excludeMaThe);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    private TheThuVien map(ResultSet rs) throws Exception {
        return new TheThuVien(
                rs.getString("MaThe"),
                rs.getString("MaDG"),
                rs.getDate("NgayCap"),
                rs.getDate("NgayHetHan"),
                rs.getString("TrangThai")
        );
    }
    public TheThuVien findByMaDG(String maDG) {
        String sql = """
            SELECT MaThe, MaDG, NgayCap, NgayHetHan, TrangThai
            FROM thethuvien
            WHERE MaDG = ?
            LIMIT 1
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maDG);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // ❗ không có thẻ
    }
    public boolean isTheConHan(TheThuVien t) {
        if (t == null) return false;

        String trangThai = t.getTrangThai();
        if (trangThai == null || !trangThai.equalsIgnoreCase("Hoạt động")) {
            return false;
        }

        Date ngayHetHanDate = t.getNgayHetHan();  // giả sử getter trả Date hoặc sql.Date
        if (ngayHetHanDate == null) return false;

        LocalDate ngayHetHan = ngayHetHanDate.toInstant()
                                              .atZone(java.time.ZoneId.systemDefault())
                                              .toLocalDate();
        // Hoặc đơn giản hơn nếu là java.sql.Date: 
        // LocalDate ngayHetHan = ((java.sql.Date) ngayHetHanDate).toLocalDate();

        LocalDate today = LocalDate.now();

        System.out.println("DEBUG: Today = " + today);  // tạm để xem
        System.out.println("DEBUG: NgayHetHan = " + ngayHetHan);

        return !ngayHetHan.isBefore(today);  // còn hạn nếu >= hôm nay
    }

}

