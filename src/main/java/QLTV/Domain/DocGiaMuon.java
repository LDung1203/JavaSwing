// QLTV/Domain/DocGia.java (dùng cho bảng tìm độc giả)
package QLTV.Domain;

public class DocGiaMuon {
    private String maDG;
    private String tenDG;
    private String maLop;
    private String tenLop;
    private String maKhoa;
    private String tenKhoa;

    public DocGiaMuon(String maDG, String tenDG, String tenLop, String tenKhoa) {
        this.maDG = maDG;
        this.tenDG = tenDG;
        this.tenLop = tenLop;
        this.tenKhoa = tenKhoa;
    }

    // getters
    public String getMaDG() { return maDG; }
    public String getTenDG() { return tenDG; }
    public String getTenLop() { return tenLop; }
    public String getTenKhoa() { return tenKhoa; }

    @Override
    public String toString() {
        return tenDG + " (" + maDG + ")";
    }
}