/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Model;

import QLTV.Domain.NgonNgu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Admin
 */

public class NgonNguDAO {

    public List<NgonNgu> findAll() {
        String sql = "SELECT MaNN, TenNN FROM ngonngu";
        List<NgonNgu> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new NgonNgu(
                        rs.getString("MaNN"),
                        rs.getString("TenNN")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }

    public List<NgonNgu> search(String keyword) {
        String sql = "SELECT MaNN, TenNN FROM ngonngu WHERE MaNN LIKE ? OR TenNN LIKE ?";
        List<NgonNgu> list = new ArrayList<>();
        String k = "%" + keyword + "%";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, k);
            ps.setString(2, k);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new NgonNgu(
                            rs.getString("MaNN"),
                            rs.getString("TenNN")
                    ));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }

    public int insert(NgonNgu nn) {
        String sql = "INSERT INTO ngonngu(MaNN, TenNN) VALUES(?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nn.getMaNN());
            ps.setString(2, nn.getTenNN());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int update(NgonNgu nn) {
        String sql = "UPDATE ngonngu SET TenNN=? WHERE MaNN=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nn.getTenNN());
            ps.setString(2, nn.getMaNN());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int delete(String maNN) {
        String sql = "DELETE FROM ngonngu WHERE MaNN=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maNN);
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public String taoMaNNMoi() {
        String sql = "SELECT MaNN FROM ngonngu ORDER BY MaNN DESC LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (!rs.next()) return "NN001";

            String maCu = rs.getString("MaNN"); // NN005
            int so = Integer.parseInt(maCu.substring(2));
            so++;
            return String.format("NN%03d", so);
        } catch (Exception e) { e.printStackTrace(); }
        return "NN001";
    }

    public boolean checkTrungTenNN(String tenNN) {
        String sql = "SELECT 1 FROM ngonngu WHERE TenNN = ? LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tenNN);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean checkTrungTenNNKhacMa(String tenNN, String maNN) {
        String sql = "SELECT 1 FROM ngonngu WHERE TenNN = ? AND MaNN <> ? LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tenNN);
            ps.setString(2, maNN);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}

