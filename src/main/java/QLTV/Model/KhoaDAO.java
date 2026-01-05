/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Model;

import QLTV.Domain.Khoa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */

public class KhoaDAO {

    public List<Khoa> findAll() {
        String sql = "SELECT MaKhoa, TenKhoa FROM khoa";
        List<Khoa> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Khoa(
                        rs.getString("MaKhoa"),
                        rs.getString("TenKhoa")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }

    public List<Khoa> search(String keyword) {
        String sql = "SELECT MaKhoa, TenKhoa FROM khoa WHERE MaKhoa LIKE ? OR TenKhoa LIKE ?";
        List<Khoa> list = new ArrayList<>();
        String k = "%" + keyword + "%";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, k);
            ps.setString(2, k);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Khoa(
                            rs.getString("MaKhoa"),
                            rs.getString("TenKhoa")
                    ));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }

    public int insert(Khoa khoa) {
        String sql = "INSERT INTO khoa(MaKhoa, TenKhoa) VALUES(?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, khoa.getMaKhoa());
            ps.setString(2, khoa.getTenKhoa());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int update(Khoa khoa) {
        String sql = "UPDATE khoa SET TenKhoa=? WHERE MaKhoa=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, khoa.getTenKhoa());
            ps.setString(2, khoa.getMaKhoa());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int delete(String maKhoa) {
        String sql = "DELETE FROM khoa WHERE MaKhoa=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maKhoa);
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public List<String> findAllMaKhoa() {
        String sql = "SELECT MaKhoa FROM khoa";
        List<String> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(rs.getString("MaKhoa"));
        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }
    public List<String[]> findAllMaTenKhoa() {
    String sql = "SELECT MaKhoa, TenKhoa FROM khoa ORDER BY MaKhoa";
    List<String[]> list = new ArrayList<>();

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            list.add(new String[]{rs.getString("MaKhoa"), rs.getString("TenKhoa")});
        }
    } catch (Exception e) { e.printStackTrace(); }

    return list;
}


    public String taoMaKhoaMoi() {
        String sql = "SELECT MaKhoa FROM khoa ORDER BY MaKhoa DESC LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (!rs.next()) return "KH001";
            String maCu = rs.getString("MaKhoa"); // KH005
            int so = Integer.parseInt(maCu.substring(2));
            so++;
            return String.format("KH%03d", so);
        } catch (Exception e) { e.printStackTrace(); }
        return "KH001";
    }
    public boolean checkTrungTenKhoa(String tenKhoa) {
    String sql = "SELECT 1 FROM khoa WHERE TenKhoa = ? LIMIT 1";
    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, tenKhoa);
        try (ResultSet rs = ps.executeQuery()) {
            return rs.next(); // có dòng → trùng
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
    public boolean checkTrungTenKhoaKhacMa(String tenKhoa, String maKhoa) {
        String sql = "SELECT 1 FROM khoa WHERE TenKhoa = ? AND MaKhoa <> ? LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tenKhoa);
            ps.setString(2, maKhoa);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
