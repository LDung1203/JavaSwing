// QLTV/Domain/MuonTra.java
package QLTV.Domain;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class MuonTra {
    private String maMT;
    private String maDG;
    private String tenDG;        // để hiển thị trên bảng
    private String maNV;
    private Date ngayMuon;
    private Date hanTra;
    private String trangThai;
    private String ghiChu;

    public MuonTra() {}

    public MuonTra(String maMT, String maDG, String tenDG, String maNV, Date ngayMuon, Date hanTra, String trangThai, String ghiChu) {
        this.maMT = maMT;
        this.maDG = maDG;
        this.tenDG = tenDG;
        this.maNV = maNV;
        this.ngayMuon = ngayMuon;
        this.hanTra = hanTra;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

    // getters & setters
    public String getMaMT() { return maMT; }
    public void setMaMT(String maMT) { this.maMT = maMT; }

    public String getMaDG() { return maDG; }
    public void setMaDG(String maDG) { this.maDG = maDG; }

    public String getTenDG() { return tenDG; }
    public void setTenDG(String tenDG) { this.tenDG = tenDG; }

    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }

    public Date getNgayMuon() { return ngayMuon; }
    public void setNgayMuon(Date ngayMuon) { this.ngayMuon = ngayMuon; }

    public Date getHanTra() { return hanTra; }
    public void setHanTra(Date hanTra) { this.hanTra = hanTra; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
    
    public String getSoNgayMuon() {
        if (hanTra == null) {
            return "-";
        }

        Date today = new Date(); // ngày hiện tại

        // Tính số mili giây chênh lệch
        long diff = hanTra.getTime() - today.getTime();

        // Chuyển sang số ngày (làm tròn xuống)
        long days = diff / (1000 * 60 * 60 * 24);

        if (days > 0) {
            return String.valueOf(days);     // còn X ngày → hiển thị "10", "5"...
        } else if (days == 0) {
            return "0";                      // hôm nay là hạn cuối
        } else {
            return "+" + Math.abs(days);     // quá hạn → "+3", "+10"...
        }
    }
}