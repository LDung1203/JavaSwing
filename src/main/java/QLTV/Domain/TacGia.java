/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Domain;

/**
 *
 * @author dinhd
 */
public class TacGia {
    private String maTG;
    private String tenTG;
    private Integer namSinh;
    private String gioiTinh;
    private String quocTich;

    public TacGia() {
    }

    public TacGia(String maTG, String tenTG, Integer namSinh, String gioiTinh, String quocTich) {
        this.maTG = maTG;
        this.tenTG = tenTG;
        this.namSinh = namSinh;
        this.gioiTinh = gioiTinh;
        this.quocTich = quocTich;
    }

    public String getMaTG() {
        return maTG;
    }

    public void setMaTG(String maTG) {
        this.maTG = maTG;
    }

    public String getTenTG() {
        return tenTG;
    }

    public void setTenTG(String tenTG) {
        this.tenTG = tenTG;
    }

    public Integer getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(Integer namSinh) {
        this.namSinh = namSinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getQuocTich() {
        return quocTich;
    }

    public void setQuocTich(String quocTich) {
        this.quocTich = quocTich;
    }
    
    public TacGia(String maTG, String tenTG) {
        this.maTG = maTG;
        this.tenTG = tenTG;
    }
    @Override
    public String toString() {
        return tenTG;
    }
}