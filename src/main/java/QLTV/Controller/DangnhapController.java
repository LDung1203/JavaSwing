/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Controller;

import QLTV.Domain.Dangnhap;
import QLTV.Model.DangnhapDAO;
import QLTV.Views.FormDangnhap;
import QLTV.Views.FormTrangchu;
import javax.swing.JOptionPane;
/**
 *
 * @author dinhd
 */
public class DangnhapController {
    private final FormDangnhap view;
    private final DangnhapDAO dao;
    
    public DangnhapController(FormDangnhap view) {
        this.view = view;
        this.dao = new DangnhapDAO();
        registerEvents();
    }

    private void registerEvents() {
        view.getBtnLogin().addActionListener(e -> handleLogin());
        view.getBtnExit().addActionListener(e -> System.exit(0));
    }

    private void handleLogin() {
        String user = view.getUsername().trim();
        String pass = view.getPassword().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            view.showStatus("Vui lòng nhập Username và Password.");
            return;
        }

        Dangnhap tk = dao.findByUserPass(user, pass);
        if (tk == null) {
            view.showStatus("Sai tài khoản hoặc mật khẩu.");
            JOptionPane.showMessageDialog(view, "Đăng nhập thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(view, "Đăng nhập thành công!", "OK", JOptionPane.INFORMATION_MESSAGE);
        new FormTrangchu(tk).setVisible(true);
        view.dispose();
    }
}
