// QLTV/Domain/ChiTietMuonTra.java
package QLTV.Domain;

public class ChiTietMuonTra {
    private String maMT;
    private String maSach;
    private String tenSach;   // để hiển thị trên bảng chi tiết
    private int soLuong;
    private String ghiChu;

    public ChiTietMuonTra() {}

    public ChiTietMuonTra(String maMT, String maSach, String tenSach, int soLuong, String ghiChu) {
        this.maMT = maMT;
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.soLuong = soLuong;
        this.ghiChu = ghiChu;
    }

    // getters & setters
    public String getMaMT() { return maMT; }
    public void setMaMT(String maMT) { this.maMT = maMT; }

    public String getMaSach() { return maSach; }
    public void setMaSach(String maSach) { this.maSach = maSach; }

    public String getTenSach() { return tenSach; }
    public void setTenSach(String tenSach) { this.tenSach = tenSach; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
}