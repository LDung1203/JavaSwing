/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Domain;

/**
 *
 * @author Admin
 */

public class KeSach {
    private String maViTri;
    private String tenKe;
    private String moTa;

    public KeSach(String maViTri, String tenKe, String moTa) {
        this.maViTri = maViTri;
        this.tenKe = tenKe;
        this.moTa = moTa;
    }

    public String getMaViTri() { return maViTri; }
    public void setMaViTri(String maViTri) { this.maViTri = maViTri; }

    public String getTenKe() { return tenKe; }
    public void setTenKe(String tenKe) { this.tenKe = tenKe; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
        @Override
    public String toString() {
        return tenKe;
    }
}

