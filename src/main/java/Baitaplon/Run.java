/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Baitaplon;

import QLTV.Controller.DangnhapController;
import QLTV.Views.FormDangnhap;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author dinhd
 */
public class Run {
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            FormDangnhap view = new FormDangnhap();
            new DangnhapController(view);
            view.setVisible(true);
        });
    }
}
