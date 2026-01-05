/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Model;

import QLTV.Domain.TacGia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author dinhd
 */
public class TacGiaDAO {

    public List<TacGia> findAll() {
        String sql = "SELECT MaTG, TenTG, NamSinh, GioiTinh, QuocTich FROM tacgia";
        List<TacGia> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(map(rs));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public List<TacGia> search(String keyword) {
        String sql = "SELECT MaTG, TenTG, NamSinh, GioiTinh, QuocTich " +
                     "FROM tacgia WHERE MaTG LIKE ? OR TenTG LIKE ?";
        List<TacGia> list = new ArrayList<>();
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

    public int insert(TacGia tg) {
        String sql = "INSERT INTO tacgia(MaTG, TenTG, NamSinh, GioiTinh, QuocTich) VALUES(?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tg.getMaTG());
            ps.setString(2, tg.getTenTG());
            if (tg.getNamSinh() == null) ps.setObject(3, null);
            else ps.setInt(3, tg.getNamSinh());
            ps.setString(4, tg.getGioiTinh());
            ps.setString(5, tg.getQuocTich());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int update(TacGia tg) {
        String sql = "UPDATE tacgia SET TenTG=?, NamSinh=?, GioiTinh=?, QuocTich=? WHERE MaTG=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tg.getTenTG());
            if (tg.getNamSinh() == null) ps.setObject(2, null);
            else ps.setInt(2, tg.getNamSinh());
            ps.setString(3, tg.getGioiTinh());
            ps.setString(4, tg.getQuocTich());
            ps.setString(5, tg.getMaTG());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int delete(String maTG) {
        String sql = "DELETE FROM tacgia WHERE MaTG=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maTG);
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public String taoMaTGMoi() {
        String sql = "SELECT MaTG FROM tacgia ORDER BY MaTG DESC LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (!rs.next()) return "TG001";
            String maCu = rs.getString("MaTG"); 
            int so = Integer.parseInt(maCu.substring(2));
            so++;
            return String.format("TG%03d", so);
        } catch (Exception e) { e.printStackTrace(); }
        return "TG001";
    }

    public List<String> getAllGioiTinh() {
        String sql = "SELECT DISTINCT GioiTinh FROM tacgia ORDER BY GioiTinh";
        List<String> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String v = rs.getString(1);
                if (v != null && !v.trim().isEmpty()) list.add(v.trim());
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public List<String> getAllQuocTich() {
        String sql = "SELECT DISTINCT QuocTich FROM tacgia ORDER BY QuocTich";
        List<String> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String v = rs.getString(1);
                if (v != null && !v.trim().isEmpty()) list.add(v.trim());
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    private TacGia map(ResultSet rs) throws Exception {
        return new TacGia(
                rs.getString("MaTG"),
                rs.getString("TenTG"),
                (Integer) rs.getObject("NamSinh"),
                rs.getString("GioiTinh"),
                rs.getString("QuocTich")
        );
    }
}
