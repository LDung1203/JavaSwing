/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Model;

import QLTV.Domain.NhaXuatBan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dinhd
 */
public class NhaXuatBanDAO {

    public List<NhaXuatBan> findAll() {
        String sql = "SELECT MaNXB, TenNXB, DiaChi, Sdt, Email FROM nhaxuatban";
        List<NhaXuatBan> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(map(rs));
        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }

    public List<NhaXuatBan> search(String keyword) {
        String sql = "SELECT MaNXB, TenNXB, DiaChi, Sdt, Email FROM nhaxuatban " +
                     "WHERE MaNXB LIKE ? OR TenNXB LIKE ?";
        List<NhaXuatBan> list = new ArrayList<>();
        String k = "%" + keyword + "%";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, k);
            ps.setString(2, k);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        } catch (Exception e) { e.printStackTrace(); }

        return list;
        
    }
    
    public boolean checkTrungTenNXB(String TenNXB) {
        String sql = "SELECT 1 FROM nhaxuatban WHERE TenNXB = ? LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, TenNXB);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

//    // Dùng khi UPDATE: cho phép trùng với chính nó, nhưng không trùng với sách khác
//    public boolean checkTrungTenNXBKhacMa(String tenSach, String maSach) {
//        String sql = "SELECT 1 FROM sach WHERE TenSach = ? AND MaSach <> ? LIMIT 1";
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setString(1, tenSach);
//            ps.setString(2, maSach);
//            try (ResultSet rs = ps.executeQuery()) {
//                return rs.next();
//            }
//
//        } catch (Exception e) { e.printStackTrace(); }
//        return false;
//    }
    
    public int insert(NhaXuatBan nxb) {
        String sql = "INSERT INTO nhaxuatban(MaNXB, TenNXB, DiaChi, Sdt, Email) VALUES(?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nxb.getMaNXB());
            ps.setString(2, nxb.getTenNXB());
            ps.setString(3, nxb.getDiaChi());
            ps.setString(4, nxb.getSdt());
            ps.setString(5, nxb.getEmail());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int update(NhaXuatBan nxb) {
        String sql = "UPDATE nhaxuatban SET TenNXB=?, DiaChi=?, Sdt=?, Email=? WHERE MaNXB=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nxb.getTenNXB());
            ps.setString(2, nxb.getDiaChi());
            ps.setString(3, nxb.getSdt());
            ps.setString(4, nxb.getEmail());
            ps.setString(5, nxb.getMaNXB());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int delete(String maNXB) {
        String sql = "DELETE FROM nhaxuatban WHERE MaNXB=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maNXB);
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public String taoMaNXBMoi() {
        String sql = "SELECT MaNXB FROM nhaxuatban ORDER BY MaNXB DESC LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (!rs.next()) return "NXB001";
            String maCu = rs.getString("MaNXB"); // NXB005
            int so = Integer.parseInt(maCu.substring(3));
            so++;
            return String.format("NXB%03d", so);
        } catch (Exception e) { e.printStackTrace(); }
        return "NXB001";
    }

    private NhaXuatBan map(ResultSet rs) throws Exception {
        return new NhaXuatBan(
                rs.getString("MaNXB"),
                rs.getString("TenNXB"),
                rs.getString("DiaChi"),
                rs.getString("Sdt"),
                rs.getString("Email")
        );
    }
}
