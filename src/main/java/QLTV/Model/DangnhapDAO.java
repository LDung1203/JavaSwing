/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Model;

import QLTV.Domain.Dangnhap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author dinhd
 */
public class DangnhapDAO {
    public Dangnhap findByUserPass(String username, String password) {
    String sql = "SELECT User, Pass, TenNV, Email " +
                 "FROM nhanvien WHERE User=? AND Pass=?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, username.trim());
        ps.setString(2, password.trim() );
        

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Dangnhap(
                        rs.getString("User"),
                        rs.getString("Pass"),
                        rs.getString("TenNV"),
                        rs.getString("Email")
                );
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

}
