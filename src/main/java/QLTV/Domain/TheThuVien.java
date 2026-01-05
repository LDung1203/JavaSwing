/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Domain;

import java.sql.Date;

/**
 *
 * @author Admin
 */

public class TheThuVien {
    private String maThe;
    private String maDG;
    private Date ngayCap;
    private Date ngayHetHan;
    private String trangThai;

    public TheThuVien() {}

    public TheThuVien(String maThe, String maDG, Date ngayCap, Date ngayHetHan, String trangThai) {
        this.maThe = maThe;
        this.maDG = maDG;
        this.ngayCap = ngayCap;
        this.ngayHetHan = ngayHetHan;
        this.trangThai = trangThai;
    }

    public String getMaThe() { return maThe; }
    public void setMaThe(String maThe) { this.maThe = maThe; }

    public String getMaDG() { return maDG; }
    public void setMaDG(String maDG) { this.maDG = maDG; }

    public Date getNgayCap() { return ngayCap; }
    public void setNgayCap(Date ngayCap) { this.ngayCap = ngayCap; }

    public Date getNgayHetHan() { return ngayHetHan; }
    public void setNgayHetHan(Date ngayHetHan) { this.ngayHetHan = ngayHetHan; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}
