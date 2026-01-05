/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Controller;

import QLTV.Views.FormThongKe;
import QLTV.Model.ThongKeDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import QLTV.Domain.TK_Sach;
import QLTV.Domain.TK_DocGia;
import QLTV.Domain.TK_QuaHan;
/**
 *
 * @author khanh
 */
public class ThongKeController {
       private FormThongKe view;
    private ThongKeDAO dao = new ThongKeDAO();

    public ThongKeController(FormThongKe view) {
        this.view = view;
        addEvents();
        loadCounter();
    }

    private void addEvents() {
        view.getBtnSachMuonNhieu().addActionListener(e -> loadSach());
        view.getBtnDGNhieu().addActionListener(e -> loadDocGia());
        view.getBtnQuaHan().addActionListener(e -> loadQuaHan());
    }

    private void loadSach() {
        DefaultTableModel m = view.getModel();
        m.setRowCount(0);
        m.setColumnIdentifiers(new String[]{"Mã sách", "Tên sách", "Số lượt mượn"});

        for (TK_Sach s : dao.sachMuonNhieu()) {
            m.addRow(new Object[]{s.getMaSach(), s.getTenSach(), s.getTongMuon()});
        }
    }

    private void loadDocGia() {
        DefaultTableModel m = view.getModel();
        m.setRowCount(0);
        m.setColumnIdentifiers(new String[]{"Mã DG", "Tên độc giả", "Số sách mượn"});

        for (TK_DocGia d : dao.docGiaMuonNhieu()) {
            m.addRow(new Object[]{d.getMaDG(), d.getTenDG(), d.getTongSach()});
        }
    }

    private void loadQuaHan() {
        DefaultTableModel m = view.getModel();
        m.setRowCount(0);
        m.setColumnIdentifiers(new String[]{"Mã MT", "Tên độc giả", "Hạn trả", "Ngày quá hạn"});

        for (TK_QuaHan q : dao.phieuQuaHan()) {
            m.addRow(new Object[]{q.getMaMT(), q.getTenDG(), q.getHanTra(), q.getSoNgayQuaHan()});
        }
    }

private void loadCounter() {
    view.setTongSach(dao.tongSoSach());
    view.setTongDG(dao.tongDocGia());
    view.setDangMuon(dao.sachDangMuon());
}
}