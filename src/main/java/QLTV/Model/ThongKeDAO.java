/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Model;

import QLTV.Domain.*;

import java.sql.*;
import java.util.*;
/**
 *
 * @author khanh
 */
public class ThongKeDAO {
    // Sách mượn nhiều
    public List<TK_Sach> sachMuonNhieu() {
        List<TK_Sach> list = new ArrayList<>();
        String sql = """
        SELECT s.MaSach, s.TenSach, COALESCE(SUM(ct.SoLuong),0) As TongMuon
        FROM sach s
        LEFT JOIN chitietmuontra ct ON s.MaSach = ct.MaSach
        GROUP BY s.MaSach, s.TenSach
        ORDER BY TongMuon DESC;
    """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new TK_Sach(
                        rs.getString("MaSach"),
                        rs.getString("TenSach"),
                        rs.getInt("TongMuon")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Độc giả mượn nhiều
    public List<TK_DocGia> docGiaMuonNhieu() {
        List<TK_DocGia> list = new ArrayList<>();
        String sql =
            "SELECT dg.MaDG, dg.TenDG, COALESCE(SUM(ct.SoLuong),0) Tong " +
            "FROM muontra mt " +
            "JOIN docgia dg ON mt.MaDG = dg.MaDG " +
            "JOIN chitietmuontra ct ON mt.MaMT = ct.MaMT " +
            "GROUP BY dg.MaDG, dg.TenDG " +
            "ORDER BY Tong DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new TK_DocGia(
                        rs.getString("MaDG"),
                        rs.getString("TenDG"),
                        rs.getInt("Tong")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Phiếu mượn quá hạn
    public List<TK_QuaHan> phieuQuaHan() {
        List<TK_QuaHan> list = new ArrayList<>();
        String sql =
            "SELECT mt.MaMT, dg.TenDG, mt.HanTra, " +
            "DATEDIFF(CURDATE(), mt.HanTra) SoNgay " +
            "FROM muontra mt " +
            "JOIN docgia dg ON mt.MaDG = dg.MaDG " +
            "WHERE mt.HanTra < CURDATE() " +
            "AND (mt.TrangThai IS NULL OR mt.TrangThai <> 'Đã trả')";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new TK_QuaHan(
                        rs.getString("MaMT"),
                        rs.getString("TenDG"),
                        rs.getDate("HanTra"),
                        rs.getInt("SoNgay")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
     public int tongDocGia() {
        String sql = "SELECT COUNT(*) FROM docgia";
        return getInt(sql);
    }
    public int tongSoSach() {
        String sql = "SELECT COUNT(*) FROM sach";
        return getInt(sql);
    }
    public int sachDangMuon() {
        String sql = """
            SELECT COALESCE(SUM(ct.SoLuong), 0)
                    FROM chitietmuontra ct
                    JOIN muontra mt ON ct.MaMT = mt.MaMT
                    LEFT JOIN phieutra pt ON mt.MaMT = pt.MaMT
                    WHERE pt.MaMT IS NULL
        """;
        return getInt(sql);
    }
     private int getInt(String sql) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
