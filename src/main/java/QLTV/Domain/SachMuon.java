// QLTV/Domain/SachMuon.java
package QLTV.Domain;

public class SachMuon {
    private String maSach;
    private String tenSach;
    private String theLoai;
    private Integer soLuongCon;

    public SachMuon() {}

    public SachMuon(String maSach, String tenSach, String theLoai, Integer soLuongCon) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.theLoai = theLoai;
        this.soLuongCon = soLuongCon;
    }

    public String getMaSach() { return maSach; }
    public void setMaSach(String maSach) { this.maSach = maSach; }

    public String getTenSach() { return tenSach; }
    public void setTenSach(String tenSach) { this.tenSach = tenSach; }

    public String getTheLoai() { return theLoai; }
    public void setTheLoai(String theLoai) { this.theLoai = theLoai; }

    public Integer getSoLuongCon() { return soLuongCon; }
    public void setSoLuongCon(Integer soLuongCon) { this.soLuongCon = soLuongCon; }
}