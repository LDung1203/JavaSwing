/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author dinhd
 */
public class DBConnection {
    private static final String URL =
            "jdbc:mysql://localhost:3306/thuvienutt?useSSL=false&serverTimezone=UTC"
            + "&connectTimeout=3000&socketTimeout=3000";
    private static final String USER = "root";
    private static final String PASS = "123456";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            System.out.println(" DB error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}

