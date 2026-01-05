/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Domain;

/**
 *
 * @author khanh
 */
public class TK_Sach {
    private String maSach;
    private String tenSach;
    private int tongMuon;

    public TK_Sach(String maSach, String tenSach, int tongMuon) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tongMuon = tongMuon;
    }

    public String getMaSach() { return maSach; }
    public String getTenSach() { return tenSach; }
    public int getTongMuon() { return tongMuon; }
}
