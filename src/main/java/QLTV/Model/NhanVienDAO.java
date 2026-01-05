/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Model;

import QLTV.Domain.NhanVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class NhanVienDAO {

    public List<NhanVien> findAll() {
        String sql = "SELECT MaNV, TenNV, QueQuan, GioiTinh, VaiTro, Email, Sdt FROM nhanvien";
        List<NhanVien> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(map(rs));
        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }

    public List<NhanVien> search(String keyword) {
        String sql = "SELECT MaNV, TenNV, QueQuan, GioiTinh, VaiTro, Email, Sdt FROM nhanvien " +
                     "WHERE MaNV LIKE ? OR TenNV LIKE ? OR QueQuan LIKE ? OR GioiTinh LIKE ? OR VaiTro LIKE ? OR Email LIKE ? OR Sdt LIKE ?";
        List<NhanVien> list = new ArrayList<>();
        String k = "%" + keyword + "%";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, k);
            ps.setString(2, k);
            ps.setString(3, k);
            ps.setString(4, k);
            ps.setString(5, k);
            ps.setString(6, k);
            ps.setString(7, k);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }
    
    
    public boolean checkTrungTenNV(String ten) {
        String sql = "SELECT 1 FROM nhanvien WHERE TenNV = ? LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ten);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean checkTrungTenNVKhacMa(String ten, String ma) {
        String sql = "SELECT 1 FROM nhanvien WHERE TenNV = ? AND MaNV <> ? LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ten);
            ps.setString(2, ma);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public int insert(NhanVien nv) {
        String sql = "INSERT INTO nhanvien(MaNV, TenNV, QueQuan, GioiTinh, VaiTro, Email, Sdt) VALUES(?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nv.getMaNV());
            ps.setString(2, nv.getTenNV());
            ps.setString(3, nv.getQueQuan());
            ps.setString(4, nv.getGioiTinh());
            ps.setString(5, nv.getVaiTro());
            ps.setString(6, nv.getEmail());
            ps.setString(7, (nv.getSdt() == null || nv.getSdt().isEmpty()) ? null : nv.getSdt());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int update(NhanVien nv) {
        String sql = "UPDATE nhanvien SET TenNV=?, QueQuan=?, GioiTinh=?, VaiTro=?, Email=?, Sdt=? WHERE MaNV=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nv.getTenNV());
            ps.setString(2, nv.getQueQuan());
            ps.setString(3, nv.getGioiTinh());
            ps.setString(4, nv.getVaiTro());
            ps.setString(5, nv.getEmail());
            ps.setString(6, (nv.getSdt() == null || nv.getSdt().isEmpty()) ? null : nv.getSdt());
            ps.setString(7, nv.getMaNV());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int delete(String maNV) {
        String sql = "DELETE FROM nhanvien WHERE MaNV=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maNV);
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public String taoMaNVMoi() {
        String sql = "SELECT MaNV FROM nhanvien ORDER BY MaNV DESC LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (!rs.next()) return "NV001";
            String maCu = rs.getString("MaNV");
            int so = Integer.parseInt(maCu.substring(2));
            so++;
            return String.format("NV%03d", so);
        } catch (Exception e) { e.printStackTrace(); }
        return "NV001";
    }

    public boolean existsEmail(String email, String excludeMaNV) {
        String sql = "SELECT 1 FROM nhanvien WHERE Email=? AND MaNV<>? LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, excludeMaNV == null ? "" : excludeMaNV);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean existsSdt(String sdt, String excludeMaNV) {
        // sdt được NULL => nếu rỗng/null thì coi như không check trùng
        if (sdt == null || sdt.trim().isEmpty()) return false;

        String sql = "SELECT 1 FROM nhanvien WHERE Sdt=? AND MaNV<>? LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, sdt);
            ps.setString(2, excludeMaNV == null ? "" : excludeMaNV);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    private NhanVien map(ResultSet rs) throws Exception {
        return new NhanVien(
                rs.getString("MaNV"),
                rs.getString("TenNV"),
                rs.getString("QueQuan"),
                rs.getString("GioiTinh"),
                rs.getString("VaiTro"),
                rs.getString("Email"),
                rs.getString("Sdt")
        );
    }
}