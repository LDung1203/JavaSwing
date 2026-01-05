/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Model;

import QLTV.Domain.Theloai;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author dinhd
 */
public class TheloaiDAO {
    
    public List<Theloai> findAll() {
        String sql = "SELECT MaTL, TenTL FROM theloai";
        List<Theloai> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    
    public boolean checkTrungTenTheLoai(String TenTheLoai) {
        String sql = "SELECT 1 FROM theloai WHERE TenTL = ? LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, TenTheLoai);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean checkTrungTenTheLoaiKhacMa(String TenTheLoai, String MaTL) {
        String sql = "SELECT 1 FROM theloai WHERE TenTL = ? AND MaTL <> ? LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, TenTheLoai);
            ps.setString(2, MaTL);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
    
    public List<Theloai> search(String keyword) {
        String sql = "SELECT MaTL, TenTL FROM theloai WHERE MaTL LIKE ? OR TenTL LIKE ?";
        List<Theloai> list = new ArrayList<>();
        String k = "%" + keyword + "%";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, k);
            ps.setString(2, k);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int insert(Theloai tl) {
        String sql = "INSERT INTO theloai(MaTL, TenTL) VALUES(?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tl.getMaTL());
            ps.setString(2, tl.getTenTL());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(Theloai tl) {
        String sql = "UPDATE theloai SET TenTL=? WHERE MaTL=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tl.getTenTL());
            ps.setString(2, tl.getMaTL());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(String maTL) {
        String sql = "DELETE FROM theloai WHERE MaTL=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maTL);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String taoMaTLMoi() {
        String sql = "SELECT MaTL FROM theloai ORDER BY MaTL DESC LIMIT 1";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (!rs.next()) {
                return "TL001";
            }

            String maCu = rs.getString("MaTL"); // ví dụ: TL005
            int so = Integer.parseInt(maCu.substring(2)); // bỏ "TL"
            so++;

            return String.format("TL%03d", so);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "TL001";
    }
    
    private Theloai map(ResultSet rs) throws Exception {
        return new Theloai(
                rs.getString("MaTL"),
                rs.getString("TenTL")
        );
    }
}