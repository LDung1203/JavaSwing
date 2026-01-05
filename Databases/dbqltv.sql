-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               9.5.0 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.13.0.7147
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for thuvienutt
CREATE DATABASE IF NOT EXISTS `thuvienutt` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `thuvienutt`;

-- Dumping structure for table thuvienutt.chitietmuontra
CREATE TABLE IF NOT EXISTS `chitietmuontra` (
  `MaMT` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MaSach` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SoLuong` int NOT NULL,
  KEY `fk_ctmt_s` (`MaSach`),
  KEY `fk_ctmt_mt` (`MaMT`),
  CONSTRAINT `fk_ctmt_mt` FOREIGN KEY (`MaMT`) REFERENCES `muontra` (`MaMT`),
  CONSTRAINT `fk_ctmt_s` FOREIGN KEY (`MaSach`) REFERENCES `sach` (`MaSach`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table thuvienutt.chitietmuontra: ~1 rows (approximately)
INSERT INTO `chitietmuontra` (`MaMT`, `MaSach`, `SoLuong`) VALUES
	('MT01', 'S001', 3),
	('MT02', 'S004', 2),
	('MT01', 'S005', 1),
	('MT02', 'S002', 4);

-- Dumping structure for table thuvienutt.docgia
CREATE TABLE IF NOT EXISTS `docgia` (
  `MaDG` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MaKhoa` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MaLop` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TenDG` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `GioiTinh` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `DiaChi` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Email` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Sdt` varchar(13) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`MaDG`),
  KEY `fk_dg_lop` (`MaLop`),
  KEY `fk_dg_khoa` (`MaKhoa`),
  CONSTRAINT `fk_dg_khoa` FOREIGN KEY (`MaKhoa`) REFERENCES `khoa` (`MaKhoa`),
  CONSTRAINT `fk_dg_lop` FOREIGN KEY (`MaLop`) REFERENCES `lop` (`MaLop`),
  CONSTRAINT `docgia_chk_1` CHECK ((char_length(`Sdt`) between 10 and 13))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table thuvienutt.docgia: ~3 rows (approximately)
INSERT INTO `docgia` (`MaDG`, `MaKhoa`, `MaLop`, `TenDG`, `GioiTinh`, `DiaChi`, `Email`, `Sdt`) VALUES
	('DG01', 'K001', 'L001', 'Nguyễn Văn A', 'Nam', 'Hà Nội', 'asv@utt.edu.vn', '0981113222'),
	('DG02', 'K001', 'L002', 'Trần Thị B', 'Nữ', 'Nam Định', 'bsv@utt.edu.vn', '0973334444'),
	('DG03', 'K002', 'L003', 'Đặng Văn C', 'Nam', 'Hưng Yên', 'csv@utt.edu.vn', '0965556666');

-- Dumping structure for table thuvienutt.khoa
CREATE TABLE IF NOT EXISTS `khoa` (
  `MaKhoa` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TenKhoa` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MoTa` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`MaKhoa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table thuvienutt.khoa: ~4 rows (approximately)
INSERT INTO `khoa` (`MaKhoa`, `TenKhoa`, `MoTa`) VALUES
	('K001', 'Công nghệ thông tin', 'Đào tạo CNTT ứng dụng'),
	('K002', 'Kinh tế vận tải', 'Kinh tế – quản lý giao thông'),
	('K003', 'Công trình', 'Xây dựng cầu đường'),
	('K004', 'Cơ khí', 'Cơ khí – động lực');

-- Dumping structure for table thuvienutt.lop
CREATE TABLE IF NOT EXISTS `lop` (
  `MaLop` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TenLop` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MaKhoa` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`MaLop`),
  KEY `fk_lop_khoa` (`MaKhoa`),
  CONSTRAINT `fk_lop_khoa` FOREIGN KEY (`MaKhoa`) REFERENCES `khoa` (`MaKhoa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table thuvienutt.lop: ~4 rows (approximately)
INSERT INTO `lop` (`MaLop`, `TenLop`, `MaKhoa`) VALUES
	('L001', '74DCHT23', 'K001'),
	('L002', '74DCTN23', 'K002'),
	('L003', '74DCCD23', 'K003'),
	('L004', '74DCCO23', 'K004');

-- Dumping structure for table thuvienutt.muontra
CREATE TABLE IF NOT EXISTS `muontra` (
  `MaMT` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MaDG` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MaNV` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `NgayMuon` date DEFAULT (curdate()),
  `HanTra` date NOT NULL DEFAULT ((curdate() + interval 3 month)),
  `TrangThai` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT 'Chưa trả',
  PRIMARY KEY (`MaMT`),
  KEY `fk_mt_nv` (`MaNV`),
  KEY `fk_mt_dg` (`MaDG`),
  CONSTRAINT `fk_mt_dg` FOREIGN KEY (`MaDG`) REFERENCES `docgia` (`MaDG`),
  CONSTRAINT `fk_mt_nv` FOREIGN KEY (`MaNV`) REFERENCES `nhanvien` (`MaNV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table thuvienutt.muontra: ~2 rows (approximately)
INSERT INTO `muontra` (`MaMT`, `MaDG`, `MaNV`, `NgayMuon`, `HanTra`, `TrangThai`) VALUES
	('MT01', 'DG01', 'NV01', '2024-10-01', '2025-01-01', 'Chưa trả'),
	('MT02', 'DG02', 'NV01', '2024-10-05', '2025-01-05', 'Chưa trả');

-- Dumping structure for table thuvienutt.ngonngu
CREATE TABLE IF NOT EXISTS `ngonngu` (
  `MaNN` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TenNN` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`MaNN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table thuvienutt.ngonngu: ~2 rows (approximately)
INSERT INTO `ngonngu` (`MaNN`, `TenNN`) VALUES
	('NN01', 'Tiếng Việt'),
	('NN02', 'Tiếng Anh');

-- Dumping structure for table thuvienutt.nhanvien
CREATE TABLE IF NOT EXISTS `nhanvien` (
  `MaNV` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TenNV` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `QueQuan` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `GioiTinh` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `DiaChi` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Email` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Sdt` varchar(13) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `Tendangnhap` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Matkhau` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`MaNV`),
  CONSTRAINT `nhanvien_chk_1` CHECK ((char_length(`Sdt`) between 10 and 13))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table thuvienutt.nhanvien: ~3 rows (approximately)
INSERT INTO `nhanvien` (`MaNV`, `TenNV`, `QueQuan`, `GioiTinh`, `DiaChi`, `Email`, `Sdt`, `Tendangnhap`, `Matkhau`) VALUES
	('NV01', 'Nguyễn Nam Khánh', 'Hà Nội', 'Nam', 'Ba Đình', 'khanh@utt.edu.vn', '0901334567', 'khanh', '123456'),
	('NV02', 'Nguyễn Ngọc Bích', 'Hà Nội', 'Nữ', 'Gia Lâm', 'bich@utt.edu.vn', '0901102005', 'bich', '123456'),
	('NV03', 'Đinh Hoàng Đức', 'Hà Nôi', 'Nam', 'Gia Lâm', 'duc@gmail.com', '0978652343', 'duc', '123456');

-- Dumping structure for table thuvienutt.nhaxuatban
CREATE TABLE IF NOT EXISTS `nhaxuatban` (
  `MaNXB` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TenNXB` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `DiaChi` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Email` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Sdt` varchar(13) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`MaNXB`),
  CONSTRAINT `nhaxuatban_chk_1` CHECK ((char_length(`Sdt`) between 10 and 13))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table thuvienutt.nhaxuatban: ~3 rows (approximately)
INSERT INTO `nhaxuatban` (`MaNXB`, `TenNXB`, `DiaChi`, `Email`, `Sdt`) VALUES
	('NXB01', 'Nhà xuất bản Giáo dục Việt Nam', 'Hà Nội', 'contact@nxb.edu.vn', '02438220801'),
	('NXB02', 'Nhà xuất bản Giao thông Vận tải', 'Hà Nội', 'info@nxbgtvt.vn', '02437663323'),
	('NXB03', 'Nhà xuất bản Khoa học và Kỹ thuật', 'Hà Nội', 'khkt@nxb.vn', '02439438220');

-- Dumping structure for table thuvienutt.phieudattruoc
CREATE TABLE IF NOT EXISTS `phieudattruoc` (
  `MaPhieuDat` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MaDG` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MaSach` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `NgayDat` date NOT NULL,
  `TrangThai` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT 'Đang chờ',
  `NgayHetHan` date DEFAULT ((curdate() + interval 7 day)),
  PRIMARY KEY (`MaPhieuDat`),
  KEY `fk_pdt_s` (`MaSach`),
  KEY `fk_pdt_dg` (`MaDG`),
  CONSTRAINT `fk_pdt_dg` FOREIGN KEY (`MaDG`) REFERENCES `docgia` (`MaDG`),
  CONSTRAINT `fk_pdt_s` FOREIGN KEY (`MaSach`) REFERENCES `sach` (`MaSach`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table thuvienutt.phieudattruoc: ~1 rows (approximately)
INSERT INTO `phieudattruoc` (`MaPhieuDat`, `MaDG`, `MaSach`, `NgayDat`, `TrangThai`, `NgayHetHan`) VALUES
	('PD01', 'DG03', 'S001', '2024-11-20', 'Đang chờ', '2024-11-27');

-- Dumping structure for table thuvienutt.phieutra
CREATE TABLE IF NOT EXISTS `phieutra` (
  `MaPT` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MaMT` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `NgayTra` date DEFAULT (curdate()),
  `TinhTrang` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT 'Chưa trả',
  PRIMARY KEY (`MaPT`),
  KEY `fk_pt_mt` (`MaMT`),
  CONSTRAINT `fk_pt_mt` FOREIGN KEY (`MaMT`) REFERENCES `muontra` (`MaMT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table thuvienutt.phieutra: ~1 rows (approximately)
INSERT INTO `phieutra` (`MaPT`, `MaMT`, `NgayTra`, `TinhTrang`) VALUES
	('PT01', 'MT01', '0000-00-00', 'Đã trả');

-- Dumping structure for table thuvienutt.sach
CREATE TABLE IF NOT EXISTS `sach` (
  `MaSach` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MaTG` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MaNXB` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MaTL` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TenSach` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `NamXB` int NOT NULL,
  `SoLuong` int DEFAULT NULL,
  `TinhTrang` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `MoTa` longtext COLLATE utf8mb4_unicode_ci,
  `MaNN` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MaViTri` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`MaSach`),
  KEY `fk_s_tg` (`MaTG`),
  KEY `fk_s_nxb` (`MaNXB`),
  KEY `fk_s_tl` (`MaTL`),
  KEY `fk_s_nn` (`MaNN`),
  KEY `fk_s_vt` (`MaViTri`),
  CONSTRAINT `fk_s_nn` FOREIGN KEY (`MaNN`) REFERENCES `ngonngu` (`MaNN`),
  CONSTRAINT `fk_s_nxb` FOREIGN KEY (`MaNXB`) REFERENCES `nhaxuatban` (`MaNXB`),
  CONSTRAINT `fk_s_tg` FOREIGN KEY (`MaTG`) REFERENCES `tacgia` (`MaTG`),
  CONSTRAINT `fk_s_tl` FOREIGN KEY (`MaTL`) REFERENCES `theloai` (`MaTL`),
  CONSTRAINT `fk_s_vt` FOREIGN KEY (`MaViTri`) REFERENCES `vitri` (`MaViTri`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table thuvienutt.sach: ~6 rows (approximately)
INSERT INTO `sach` (`MaSach`, `MaTG`, `MaNXB`, `MaTL`, `TenSach`, `NamXB`, `SoLuong`, `TinhTrang`, `MoTa`, `MaNN`, `MaViTri`) VALUES
	('S001', 'TG01', 'NXB01', 'TL01', 'Giáo trình Cơ sở dữ liệu', 2019, 30, 'Tốt', 'Giáo trình cung cấp kiến thức nền tảng về cơ sở dữ liệu, bao gồm mô hình dữ liệu quan hệ, đại số quan hệ, SQL, thiết kế cơ sở dữ liệu và các ràng buộc toàn vẹn. Phù hợp cho sinh viên Công nghệ thông tin.', 'NN01', 'VT01'),
	('S002', 'TG02', 'NXB01', 'TL01', 'Giáo trình Lập trình hướng đối tượng Java', 2020, 25, 'Tốt', 'Sách trình bày các khái niệm cốt lõi của lập trình hướng đối tượng bằng Java như lớp, đối tượng, kế thừa, đa hình, xử lý ngoại lệ và lập trình với tập tin. Dùng làm giáo trình chính cho sinh viên CNTT.', 'NN01', 'VT01'),
	('S003', 'TG03', 'NXB02', 'TL03', 'Tổ chức và quản lý giao thông vận tải', 2018, 20, 'Tốt', 'Tài liệu chuyên ngành giao thông, cung cấp kiến thức về tổ chức, điều hành và quản lý các loại hình vận tải như đường bộ, đường sắt và đường thủy. Phù hợp sinh viên ngành Giao thông vận tải.', 'NN01', 'VT02'),
	('S004', 'TG04', 'NXB03', 'TL05', 'Computer Networks', 2011, 15, 'Tốt', 'Sách kinh điển về mạng máy tính của Andrew S. Tanenbaum, trình bày chi tiết mô hình OSI, TCP/IP, giao thức mạng, truyền dữ liệu và bảo mật mạng.', 'NN02', 'VT01'),
	('S005', 'TG05', 'NXB03', 'TL05', 'Operating System Concepts', 2018, 13, 'Tốt', '', 'NN01', 'VT01'),
	('S006', 'TG06', 'NXB03', 'TL02', 'Clean Code – Nghệ thuật viết mã sạch', 2010, 10, 'Tốt', 'Cuốn sách hướng dẫn các nguyên tắc và kỹ thuật viết mã nguồn sạch, dễ đọc, dễ bảo trì. Nội dung tập trung vào tư duy lập trình chuyên nghiệp và thiết kế phần mềm hiệu quả.', 'NN02', 'VT01');

-- Dumping structure for table thuvienutt.tacgia
CREATE TABLE IF NOT EXISTS `tacgia` (
  `MaTG` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TenTG` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `NamSinh` int DEFAULT NULL,
  `GioiTinh` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `QuocTich` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`MaTG`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table thuvienutt.tacgia: ~6 rows (approximately)
INSERT INTO `tacgia` (`MaTG`, `TenTG`, `NamSinh`, `GioiTinh`, `QuocTich`) VALUES
	('TG01', 'Nguyễn Văn Tuấn', 1968, 'Nam', 'Việt Nam'),
	('TG02', 'Nguyễn Đình Phúc', 1972, 'Nam', 'Việt Nam'),
	('TG03', 'Hoàng Minh Sơn', 1969, 'Nam', 'Việt Nam'),
	('TG04', 'Andrew S. Tanenbaum', 1944, 'Nam', 'Hà Lan'),
	('TG05', 'Abraham Silberschatz', 1952, 'Nam', 'Hoa Kỳ'),
	('TG06', 'Robert C. Martin', 1952, 'Nam', 'Hoa Kỳ');

-- Dumping structure for table thuvienutt.taikhoanadmin
CREATE TABLE IF NOT EXISTS `taikhoanadmin` (
  `username` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table thuvienutt.taikhoanadmin: ~1 rows (approximately)
INSERT INTO `taikhoanadmin` (`username`, `password`, `name`, `email`) VALUES
	('admin', '123456', 'Quản trị hệ thống', 'admin@gmail.com');

-- Dumping structure for table thuvienutt.theloai
CREATE TABLE IF NOT EXISTS `theloai` (
  `MaTL` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TenTL` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`MaTL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table thuvienutt.theloai: ~5 rows (approximately)
INSERT INTO `theloai` (`MaTL`, `TenTL`) VALUES
	('TL01', 'Giáo trình'),
	('TL02', 'Tham khảo'),
	('TL03', 'Chuyên ngành'),
	('TL04', 'Khoa học kỹ thuật'),
	('TL05', 'Công nghệ thông tin');

-- Dumping structure for table thuvienutt.thethuvien
CREATE TABLE IF NOT EXISTS `thethuvien` (
  `MaThe` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MaDG` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `NgayCap` date NOT NULL,
  `NgayHetHan` date NOT NULL,
  `TrangThai` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`MaThe`),
  KEY `fk_ttv_dg_` (`MaDG`),
  CONSTRAINT `fk_ttv_dg_` FOREIGN KEY (`MaDG`) REFERENCES `docgia` (`MaDG`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table thuvienutt.thethuvien: ~3 rows (approximately)
INSERT INTO `thethuvien` (`MaThe`, `MaDG`, `NgayCap`, `NgayHetHan`, `TrangThai`) VALUES
	('T001', 'DG01', '2024-01-01', '2028-01-01', 'Hoạt động'),
	('T002', 'DG02', '2024-01-01', '2028-01-01', 'Hoạt động'),
	('T003', 'DG03', '2024-01-01', '2028-01-01', 'Hoạt động');

-- Dumping structure for table thuvienutt.vitri
CREATE TABLE IF NOT EXISTS `vitri` (
  `MaViTri` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TenKe` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`MaViTri`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table thuvienutt.vitri: ~3 rows (approximately)
INSERT INTO `vitri` (`MaViTri`, `TenKe`) VALUES
	('VT01', 'Kệ CNTT'),
	('VT02', 'Kệ Giao thông'),
	('VT03', 'Kệ Kinh tế');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
