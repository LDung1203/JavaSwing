/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Model;

import QLTV.Domain.KeSach;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */

public class KeSachDAO {

    public List<KeSach> findAll() {
        String sql = "SELECT MaViTri, TenKe, MoTa FROM kesach";
        List<KeSach> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public List<KeSach> search(String keyword) {
        String sql = "SELECT MaViTri, TenKe, MoTa FROM kesach " +
                     "WHERE MaViTri LIKE ? OR TenKe LIKE ? OR MoTa LIKE ?";
        List<KeSach> list = new ArrayList<>();
        String k = "%" + keyword + "%";
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

    public int insert(KeSach ks) {
        String sql = "INSERT INTO kesach(MaViTri, TenKe, MoTa) VALUES(?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ks.getMaViTri());
            ps.setString(2, ks.getTenKe());
            ps.setString(3, ks.getMoTa());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int update(KeSach ks) {
        String sql = "UPDATE kesach SET TenKe=?, MoTa=? WHERE MaViTri=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ks.getTenKe());
            ps.setString(2, ks.getMoTa());
            ps.setString(3, ks.getMaViTri());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int delete(String maViTri) {
        String sql = "DELETE FROM kesach WHERE MaViTri=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maViTri);
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    // ===== check trùng =====
    public boolean existsMaViTri(String maViTri) {
        String sql = "SELECT 1 FROM kesach WHERE MaViTri=? LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maViTri);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean existsTenKe(String tenKe, String excludeMaViTri) {
        String sql = "SELECT 1 FROM kesach WHERE TenKe=? AND MaViTri<>? LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tenKe);
            ps.setString(2, excludeMaViTri == null ? "" : excludeMaViTri);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
    public String taoMaViTriMoi() {
        String sql = "SELECT MaViTri FROM kesach ORDER BY MaViTri DESC LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (!rs.next()) return "KS001";

            String maCu = rs.getString("MaViTri"); 
            int so = Integer.parseInt(maCu.substring(2));
            so++;
            return String.format("KS%03d", so);

        } catch (Exception e) { e.printStackTrace(); }

        return "KS001";
    }

    private KeSach map(ResultSet rs) throws Exception {
        return new KeSach(
                rs.getString("MaViTri"),
                rs.getString("TenKe"),
                rs.getString("MoTa")
        );
    }
}
