/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Model;

import QLTV.Domain.Lop;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Admin
 */

public class LopDAO {

    public List<Lop> findAll() {
        String sql = "SELECT MaLop, TenLop, MaKhoa FROM lop";
        List<Lop> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Lop(
                        rs.getString("MaLop"),
                        rs.getString("TenLop"),
                        rs.getString("MaKhoa")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }

    public List<Lop> search(String keyword) {
        String sql = "SELECT l.MaLop, l.TenLop, l.MaKhoa, k.TenKhoa " +
                     "FROM lop l " +
                     "JOIN khoa k ON l.MaKhoa = k.MaKhoa " +
                     "WHERE (l.MaLop LIKE ? OR l.TenLop LIKE ? OR k.TenKhoa LIKE ?)";
        List<Lop> list = new ArrayList<>();
        String k = "%" + keyword + "%";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, k);
            ps.setString(2, k);
            ps.setString(3, k);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Lop(
                            rs.getString("MaLop"),
                            rs.getString("TenLop"),
                            rs.getString("MaKhoa")
                    ));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }

    public int insert(Lop lop) {
        String sql = "INSERT INTO lop(MaLop, TenLop, MaKhoa) VALUES(?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, lop.getMaLop());
            ps.setString(2, lop.getTenLop());
            ps.setString(3, lop.getMaKhoa());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int update(Lop lop) {
        String sql = "UPDATE lop SET TenLop=?, MaKhoa=? WHERE MaLop=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, lop.getTenLop());
            ps.setString(2, lop.getMaKhoa());
            ps.setString(3, lop.getMaLop());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int delete(String maLop) {
        String sql = "DELETE FROM lop WHERE MaLop=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maLop);
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public List<Lop> findMaLopByMaKhoa(String maKhoa) {
         String sql = "SELECT MaLop, TenLop FROM lop WHERE MaKhoa=? ORDER BY TenLop";
         List<Lop> list = new ArrayList<>();

         try (Connection con = DBConnection.getConnection();
              PreparedStatement ps = con.prepareStatement(sql)) {

             ps.setString(1, maKhoa);

             try (ResultSet rs = ps.executeQuery()) {
                 while (rs.next()) {
                     Lop l = new Lop(
                         rs.getString("MaLop"),
                         rs.getString("TenLop"),
                         maKhoa
                     );
                     list.add(l);
                 }
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
         return list;
    }
    public String taoMaLopMoi() {
        String sql = "SELECT MaLop FROM lop ORDER BY MaLop DESC LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (!rs.next()) return "L001";
            String maCu = rs.getString("MaLop"); // L005
            int so = Integer.parseInt(maCu.substring(1));
            so++;
            return String.format("L%03d", so);
        } catch (Exception e) { e.printStackTrace(); }
        return "L001";
    }
    
   public boolean checkTrungTenLop(String tenLop) {
    String sql = "SELECT 1 FROM lop WHERE TenLop = ? LIMIT 1";
    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, tenLop);
        try (ResultSet rs = ps.executeQuery()) {
            return rs.next(); // có dòng → trùng
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
    public boolean checkTrungTenLopKhacMa(String tenLop, String maLop) {
        String sql = "SELECT 1 FROM lop WHERE TenLop = ? AND MaLop <> ? LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tenLop);
            ps.setString(2, maLop);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}