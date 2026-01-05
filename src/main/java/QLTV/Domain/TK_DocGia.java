/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Domain;

/**
 *
 * @author khanh
 */
    public class TK_DocGia {
    private String maDG;
    private String tenDG;
    private int tongSach;

    public TK_DocGia(String maDG, String tenDG, int tongSach) {
        this.maDG = maDG;
        this.tenDG = tenDG;
        this.tongSach = tongSach;
    }

    public String getMaDG() { return maDG; }
    public String getTenDG() { return tenDG; }
    public int getTongSach() { return tongSach; }
    }
