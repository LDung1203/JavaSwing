// QLTV/Model/MuonTraDAO.java
package QLTV.Model;

import QLTV.Domain.ChiTietMuonTra;
import QLTV.Domain.DocGiaMuon;
import QLTV.Domain.MuonTra;
import QLTV.Domain.SachMuon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MuonTraDAO {

    public List<MuonTra> findAll() {
        List<MuonTra> list = new ArrayList<>();
        String sql = """
            SELECT mt.MaMT, mt.MaDG, dg.TenDG, mt.MaNV, mt.NgayMuon, mt.HanTra, mt.TrangThai
            FROM muontra mt
            JOIN docgia dg ON mt.MaDG = dg.MaDG
            ORDER BY mt.NgayMuon DESC
            """;

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new MuonTra(
                    rs.getString("MaMT"),
                    rs.getString("MaDG"),
                    rs.getString("TenDG"),
                    rs.getString("MaNV"),
                    rs.getDate("NgayMuon"),
                    rs.getDate("HanTra"),
                    rs.getString("TrangThai"),
                    null
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public List<DocGiaMuon> searchDocGia(String keyword) {
        List<DocGiaMuon> list = new ArrayList<>();
        String sql = """
            SELECT dg.MaDG, dg.TenDG, l.TenLop, k.TenKhoa
            FROM docgia dg
            JOIN lop l ON dg.MaLop = l.MaLop
            JOIN khoa k ON dg.MaKhoa = k.MaKhoa
            """;

        // Chỉ thêm WHERE nếu keyword không rỗng
        boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
        if (hasKeyword) {
            sql += " WHERE dg.MaDG LIKE ? OR dg.TenDG LIKE ?";
        }

        sql += " ORDER BY dg.MaDG";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            if (hasKeyword) {
                String k = "%" + keyword.trim() + "%";
                ps.setString(1, k);
                ps.setString(2, k);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new DocGiaMuon(
                        rs.getString("MaDG"),
                        rs.getString("TenDG"),
                        rs.getString("TenLop"),
                        rs.getString("TenKhoa")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("LỖI LOAD ĐỘC GIẢ: " + e.getMessage());
        }
        return list;
    }

    public String taoMaMTMoi() {
        String sql = "SELECT MaMT FROM muontra ORDER BY MaMT DESC LIMIT 1";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (!rs.next()) return "MT01";
            String maCu = rs.getString("MaMT");
            int so = Integer.parseInt(maCu.substring(2));
            return String.format("MT%02d", so + 1);
        } catch (Exception e) { e.printStackTrace(); }
        return "MT01";
    }

    // Lấy tên sách theo mã
    public String getTenSach(String maSach) {
        String sql = "SELECT TenSach FROM sach WHERE MaSach = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, maSach);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("TenSach");
            }
        } catch (Exception e) { e.printStackTrace(); }
        return "";
    }

    // Kiểm tra số lượng sách còn lại
    public Integer getSoLuongConLai(String maSach) {
        String sql = "SELECT SoLuong FROM sach WHERE MaSach = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, maSach);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getObject("SoLuong", Integer.class);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    // CRUD phiếu mượn
    public int insertMuonTra(MuonTra mt) {
        String sql = "INSERT INTO muontra(MaMT, MaDG, MaNV, NgayMuon, HanTra, TrangThai) VALUES(?,?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, mt.getMaMT());
            ps.setString(2, mt.getMaDG());
            ps.setString(3, mt.getMaNV());
            ps.setDate(4, new java.sql.Date(mt.getNgayMuon().getTime()));
            ps.setDate(5, new java.sql.Date(mt.getHanTra().getTime()));
            ps.setString(6, mt.getTrangThai() == null ? "Chưa trả" : mt.getTrangThai());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int updateMuonTra(MuonTra mt) {
        String sql = "UPDATE muontra SET MaDG=?, MaNV=?, NgayMuon=?, HanTra=?, TrangThai=? WHERE MaMT=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, mt.getMaDG());
            ps.setString(2, mt.getMaNV());
            ps.setDate(3, new java.sql.Date(mt.getNgayMuon().getTime()));
            ps.setDate(4, new java.sql.Date(mt.getHanTra().getTime()));
            ps.setString(5, mt.getTrangThai());
            ps.setString(6, mt.getMaMT());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int deleteMuonTra(String maMT) {
        // Xóa chi tiết trước
        String sqlCT = "DELETE FROM chitietmuontra WHERE MaMT = ?";
        String sqlMT = "DELETE FROM muontra WHERE MaMT = ?";
        try (Connection c = DBConnection.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps1 = c.prepareStatement(sqlCT);
                 PreparedStatement ps2 = c.prepareStatement(sqlMT)) {
                ps1.setString(1, maMT);
                ps1.executeUpdate();

                ps2.setString(1, maMT);
                int rows = ps2.executeUpdate();
                c.commit();
                return rows;
            } catch (Exception e) {
                c.rollback();
                throw e;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    // Chi tiết mượn trả
    public List<ChiTietMuonTra> getChiTietByMaMT(String maMT) {
        List<ChiTietMuonTra> list = new ArrayList<>();
        String sql = """
            SELECT ct.MaSach, s.TenSach, ct.SoLuong
            FROM chitietmuontra ct
            JOIN sach s ON ct.MaSach = s.MaSach
            WHERE ct.MaMT = ?
            """;
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, maMT);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new ChiTietMuonTra(
                        maMT,
                        rs.getString("MaSach"),
                        rs.getString("TenSach"),
                        rs.getInt("SoLuong"),
                        null
                    ));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public int insertChiTiet(ChiTietMuonTra ct) {
        String sql = "INSERT INTO chitietmuontra(MaMT, MaSach, SoLuong) VALUES(?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, ct.getMaMT());
            ps.setString(2, ct.getMaSach());
            ps.setInt(3, ct.getSoLuong());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int deleteChiTiet(String maMT, String maSach) {
        String sql = "DELETE FROM chitietmuontra WHERE MaMT = ? AND MaSach = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, maMT);
            ps.setString(2, maSach);
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }
        // Thêm vào cuối class MuonTraDAO.java

    // Tìm kiếm sách để hiển thị trong bảng sách
        // Tìm kiếm sách để hiển thị trong bảng sách (sửa để join lấy tên thể loại)
    public List<SachMuon> searchSach(String keyword) {
        List<SachMuon> list = new ArrayList<>();
        String sql = """
            SELECT s.MaSach, s.TenSach, tl.TenTL AS TheLoai, s.SoLuong
            FROM sach s
            JOIN theloai tl ON s.MaTL = tl.MaTL
            """;

        boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
        if (hasKeyword) {
            sql += " WHERE s.MaSach LIKE ? OR s.TenSach LIKE ? OR tl.TenTL LIKE ?";
        }
        sql += " ORDER BY s.MaSach";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            if (hasKeyword) {
                String k = "%" + keyword.trim() + "%";
                ps.setString(1, k);
                ps.setString(2, k);
                ps.setString(3, k);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new SachMuon(
                        rs.getString("MaSach"),
                        rs.getString("TenSach"),
                        rs.getString("TheLoai"),  // giờ là TenTL từ bảng theloai
                        rs.getObject("SoLuong", Integer.class)
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("LỖI LOAD SÁCH: " + e.getMessage());
        }

        // Debug
        System.out.println("Load sách thành công: " + list.size() + " cuốn");
        return list;
    }
    public int updateHanTra(String maMT, Date newHanTra) {
        String sql = "UPDATE muontra SET HanTra=? WHERE MaMT=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(newHanTra.getTime()));
            ps.setString(2, maMT);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int updateTrangThai(String maMT, String trangThai) {
        String sql = "UPDATE muontra SET TrangThai=? WHERE MaMT=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, trangThai);
            ps.setString(2, maMT);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    
    // Load tất cả độc giả để đổ vào JComboBox (tái sử dụng searchDocGia)
    public List<DocGiaMuon> getAllDocGiaForCombo() {
        return searchDocGia(""); // trả về tất cả
    }
    
    // Giảm số lượng sách khi mượn
    public int giamSoLuongSach(String maSach, int soLuong) {
        String sql = """
            UPDATE sach 
            SET SoLuong = SoLuong - ?
            WHERE MaSach = ? AND SoLuong >= ?
            """;
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, soLuong);
            ps.setString(2, maSach);
            ps.setInt(3, soLuong);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Tăng số lượng sách khi trả
    public int tangSoLuongSach(String maSach, int soLuong) {
        String sql = """
            UPDATE sach 
            SET SoLuong = SoLuong + ?
            WHERE MaSach = ?
            """;
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, soLuong);
            ps.setString(2, maSach);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}