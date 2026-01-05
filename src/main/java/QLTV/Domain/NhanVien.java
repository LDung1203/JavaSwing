/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QLTV.Domain;

/**
 *
 * @author Admin
 */

public class NhanVien {
    private String maNV, tenNV, queQuan, gioiTinh, vaiTro, email, sdt;

    public NhanVien(String maNV, String tenNV, String queQuan, String gioiTinh,
                    String vaiTro, String email, String sdt) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.queQuan = queQuan;
        this.gioiTinh = gioiTinh;
        this.vaiTro = vaiTro;
        this.email = email;
        this.sdt = sdt;
    }

    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }

    public String getTenNV() { return tenNV; }
    public void setTenNV(String tenNV) { this.tenNV = tenNV; }

    public String getQueQuan() { return queQuan; }
    public void setQueQuan(String queQuan) { this.queQuan = queQuan; }

    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }

    public String getVaiTro() { return vaiTro; }
    public void setVaiTro(String vaiTro) { this.vaiTro = vaiTro; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }
}
