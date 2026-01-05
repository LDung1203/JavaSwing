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
CREATE DATABASE IF NOT EXISTS `thuvienutt` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `thuvienutt`;

-- Dumping structure for table thuvienutt.chitietmuontra
CREATE TABLE IF NOT EXISTS `chitietmuontra` (
  `MaMT` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `MaSach` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `SoLuong` int NOT NULL,
  KEY `MaMT` (`MaMT`),
  KEY `MaSach` (`MaSach`),
  CONSTRAINT `chitietmuontra_ibfk_1` FOREIGN KEY (`MaMT`) REFERENCES `muontra` (`MaMT`),
  CONSTRAINT `chitietmuontra_ibfk_2` FOREIGN KEY (`MaSach`) REFERENCES `sach` (`MaSach`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- Dumping data for table thuvienutt.chitietmuontra: ~0 rows (approximately)

-- Dumping structure for table thuvienutt.docgia
CREATE TABLE IF NOT EXISTS `docgia` (
  `MaDG` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `MaKhoa` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `MaLop` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `TenDG` varchar(50) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `GioiTinh` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `DiaChi` varchar(100) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `Email` varchar(50) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `Sdt` varchar(13) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  PRIMARY KEY (`MaDG`),
  KEY `MaKhoa` (`MaKhoa`),
  KEY `MaLop` (`MaLop`),
  CONSTRAINT `docgia_ibfk_1` FOREIGN KEY (`MaKhoa`) REFERENCES `khoa` (`MaKhoa`),
  CONSTRAINT `docgia_ibfk_2` FOREIGN KEY (`MaLop`) REFERENCES `lop` (`MaLop`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- Dumping data for table thuvienutt.docgia: ~6 rows (approximately)
INSERT INTO `docgia` (`MaDG`, `MaKhoa`, `MaLop`, `TenDG`, `GioiTinh`, `DiaChi`, `Email`, `Sdt`) VALUES
	('DG001', 'KH001', 'L002', 'Ngọc Bích', 'Nữ', 'Hà Nội', 'nb@gmail.com', '0393916176'),
	('DG002', 'KH001', 'L001', 'Nguyễn Ngọc Bích', 'Nữ', 'Hà Nội', 'bichnn02@gmail.com', '0393916177'),
	('DG003', 'KH001', 'L001', 'Nguyễn Ngọc Linh', 'Nữ', 'Hà Nội', 'linhnn05@gmail.com', '0367123456'),
	('DG004', 'KH001', 'L002', 'Lưu Đức Anh Dũng', 'Nam', 'Hà Nội', 'dunglda06@gmail.com', '0356123456'),
	('DG005', 'KH001', 'L003', 'Đinh Hoàng Đức', 'Nam', 'Hà Nội', 'ducdh07@gmail.com', '0345123456'),
	('DG006', 'KH001', 'L001', 'Nguyễn Nam Khánh', 'Nam', 'Hà Nội', 'khanhnn08@gmail.com', '0334123456');

-- Dumping structure for table thuvienutt.kesach
CREATE TABLE IF NOT EXISTS `kesach` (
  `MaViTri` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `TenKe` varchar(50) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `MoTa` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`MaViTri`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- Dumping data for table thuvienutt.kesach: ~21 rows (approximately)
INSERT INTO `kesach` (`MaViTri`, `TenKe`, `MoTa`) VALUES
	('KS001', 'Kệ CNTT', 'Sách Công nghệ thông tin'),
	('KS002', 'Kệ CKĐL', 'Sách Cơ khí động lực'),
	('KS003', 'Kệ CT', 'Sách Công trình'),
	('KS004', 'Kệ KTVT', 'Sách Kinh tế vận tải'),
	('KS005', 'Kệ KHUD & LCT', 'Luật – Chính trị – Khoa học ứng dụng'),
	('KS006', 'Kệ Văn học', 'Sách văn học'),
	('KS007', 'Kệ Quốc tế', 'Sách học thuật nước ngoài'),
	('KS008', 'Kệ CNTT', 'Sách Công nghệ thông tin'),
	('KS009', 'Kệ CKĐL', 'Sách Cơ khí động lực'),
	('KS010', 'Kệ CT', 'Sách Công trình'),
	('KS011', 'Kệ KTVT', 'Sách Kinh tế vận tải'),
	('KS012', 'Kệ KHUD & LCT', 'Luật – Chính trị – Khoa học ứng dụng'),
	('KS013', 'Kệ Văn học', 'Sách văn học'),
	('KS014', 'Kệ Quốc tế', 'Sách học thuật nước ngoài'),
	('KS015', 'Kệ CNTT', 'Sách Công nghệ thông tin'),
	('KS016', 'Kệ CKĐL', 'Sách Cơ khí động lực'),
	('KS017', 'Kệ CT', 'Sách Công trình'),
	('KS018', 'Kệ KTVT', 'Sách Kinh tế vận tải'),
	('KS019', 'Kệ KHUD & LCT', 'Luật – Chính trị – Khoa học ứng dụng'),
	('KS020', 'Kệ Văn học', 'Sách văn học'),
	('KS021', 'Kệ Quốc tế', 'Sách học thuật nước ngoài');

-- Dumping structure for table thuvienutt.khoa
CREATE TABLE IF NOT EXISTS `khoa` (
  `MaKhoa` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `TenKhoa` varchar(100) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  PRIMARY KEY (`MaKhoa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- Dumping data for table thuvienutt.khoa: ~12 rows (approximately)
INSERT INTO `khoa` (`MaKhoa`, `TenKhoa`) VALUES
	('KH001', 'Công nghệ thông tin'),
	('KH002', 'Cơ khí động lực'),
	('KH003', 'Ngôn ngữ Anh'),
	('KH004', 'Công trình'),
	('KH005', 'Điện tử viễn thông'),
	('KH006', 'Kinh tế vận tải'),
	('KH007', 'Luật'),
	('KH008', 'Logistics & Chuỗi cung ứng'),
	('KH009', 'Tự động hóa'),
	('KH010', 'Khoa học ứng dụng'),
	('KH011', 'Quản trị kinh doanh'),
	('KH012', 'Tài chính – Ngân hàng');

-- Dumping structure for table thuvienutt.lop
CREATE TABLE IF NOT EXISTS `lop` (
  `MaLop` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `TenLop` varchar(100) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `MaKhoa` varchar(10) COLLATE utf8mb4_unicode_520_ci DEFAULT NULL,
  PRIMARY KEY (`MaLop`),
  KEY `MaKhoa` (`MaKhoa`),
  CONSTRAINT `lop_ibfk_1` FOREIGN KEY (`MaKhoa`) REFERENCES `khoa` (`MaKhoa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- Dumping data for table thuvienutt.lop: ~36 rows (approximately)
INSERT INTO `lop` (`MaLop`, `TenLop`, `MaKhoa`) VALUES
	('L001', '74DCHT21', 'KH001'),
	('L002', '74DCHT22', 'KH001'),
	('L003', '74DCHT23', 'KH001'),
	('L004', '73DCHT21', 'KH001'),
	('L005', '73DCHT22', 'KH001'),
	('L006', '73DCHT23', 'KH001'),
	('L007', '74DCHT24', 'KH001'),
	('L008', '74DCHT25', 'KH001'),
	('L009', '74DCHT26', 'KH001'),
	('L010', '73DCTT21', 'KH001'),
	('L011', '73DCTT22', 'KH001'),
	('L012', '74DCTT24', 'KH001'),
	('L014', '73DDTVT21', 'KH005'),
	('L015', '73DDTVT22', 'KH005'),
	('L016', '74DDTVT24', 'KH005'),
	('L017', '74DDTVT25', 'KH005'),
	('L018', '73DCCT21', 'KH004'),
	('L019', '73DCCT22', 'KH004'),
	('L020', '74DCCT24', 'KH004'),
	('L021', '74DCCT25', 'KH004'),
	('L022', '73DCKDL01', 'KH002'),
	('L023', '73DCKDL02', 'KH002'),
	('L024', '74DCKDL01', 'KH002'),
	('L025', '74DCKDL02', 'KH002'),
	('L026', '74DCNN21', 'KH003'),
	('L027', '73DCNN21', 'KH003'),
	('L028', '73DCNN22', 'KH003'),
	('L029', '73DCLG02', 'KH003'),
	('L030', '73DCL01', 'KH008'),
	('L031', '73DCL02', 'KH008'),
	('L032', '74DCL01', 'KH008'),
	('L033', '74DCL02', 'KH008'),
	('L034', '73DKTVT01', 'KH006'),
	('L035', '73DKTVT02', 'KH006'),
	('L036', '74DKTVT01', 'KH006'),
	('L037', '74DKTVT02', 'KH006');

-- Dumping structure for table thuvienutt.muontra
CREATE TABLE IF NOT EXISTS `muontra` (
  `MaMT` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `MaDG` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `MaNV` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `NgayMuon` date DEFAULT NULL,
  `HanTra` date NOT NULL,
  `TrangThai` varchar(20) COLLATE utf8mb4_unicode_520_ci DEFAULT NULL,
  PRIMARY KEY (`MaMT`),
  KEY `MaDG` (`MaDG`),
  KEY `MaNV` (`MaNV`),
  CONSTRAINT `muontra_ibfk_1` FOREIGN KEY (`MaDG`) REFERENCES `docgia` (`MaDG`),
  CONSTRAINT `muontra_ibfk_2` FOREIGN KEY (`MaNV`) REFERENCES `nhanvien` (`MaNV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- Dumping data for table thuvienutt.muontra: ~0 rows (approximately)

-- Dumping structure for table thuvienutt.ngonngu
CREATE TABLE IF NOT EXISTS `ngonngu` (
  `MaNN` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `TenNN` varchar(100) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  PRIMARY KEY (`MaNN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- Dumping data for table thuvienutt.ngonngu: ~9 rows (approximately)
INSERT INTO `ngonngu` (`MaNN`, `TenNN`) VALUES
	('NN001', 'Tiếng Việt'),
	('NN002', 'Tiếng Anh'),
	('NN003', 'Tiếng Pháp'),
	('NN004', 'Tiếng Trung'),
	('NN005', 'Tiếng Nhật'),
	('NN006', 'Tiếng Hàn'),
	('NN007', 'Tiếng Nga'),
	('NN008', 'Tiếng Đức'),
	('NN009', 'Tiếng Tây Ban Nha');

-- Dumping structure for table thuvienutt.nhanvien
CREATE TABLE IF NOT EXISTS `nhanvien` (
  `MaNV` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `TenNV` varchar(50) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `QueQuan` varchar(50) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `GioiTinh` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `VaiTro` varchar(100) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `Email` varchar(50) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `Sdt` varchar(13) COLLATE utf8mb4_unicode_520_ci DEFAULT NULL,
  `User` varchar(50) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `Pass` varchar(50) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  PRIMARY KEY (`MaNV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- Dumping data for table thuvienutt.nhanvien: ~5 rows (approximately)
INSERT INTO `nhanvien` (`MaNV`, `TenNV`, `QueQuan`, `GioiTinh`, `VaiTro`, `Email`, `Sdt`, `User`, `Pass`) VALUES
	('NV001', 'Trần Hải', 'Hà Nội', 'Nữ', 'Quản lý', 'hai.tran@utt.edu.vn', '0987654321', 'nv1', '123'),
	('NV002', 'Nguyễn Văn Minh', 'Hà Nội', 'Nam', 'Thủ thư', 'minh.nguyen@utt.edu.vn', '0978123456', 'nv2', '123'),
	('NV003', 'Lê Thu Trang', 'Nam Định', 'Nữ', 'Thủ thư', 'trang.le@utt.edu.vn', '0967123456', 'nv3', '123'),
	('NV004', 'Phạm Đức Long', 'Thái Bình', 'Nam', 'Thủ thư', 'long.pham@utt.edu.vn', '0956123456', 'nv4', '123'),
	('NV005', 'Vũ Mai Anh', 'Hải Phòng', 'Nữ', 'Thủ thư', 'anh.vu@utt.edu.vn', '0945123456', 'nv5', '123');

-- Dumping structure for table thuvienutt.nhaxuatban
CREATE TABLE IF NOT EXISTS `nhaxuatban` (
  `MaNXB` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `TenNXB` varchar(100) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `DiaChi` varchar(100) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `Email` varchar(50) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `Sdt` varchar(13) COLLATE utf8mb4_unicode_520_ci DEFAULT NULL,
  PRIMARY KEY (`MaNXB`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- Dumping data for table thuvienutt.nhaxuatban: ~15 rows (approximately)
INSERT INTO `nhaxuatban` (`MaNXB`, `TenNXB`, `DiaChi`, `Email`, `Sdt`) VALUES
	('NXB001', 'NXB Giáo Dục Việt Nam', 'Hà Nội', 'giaoduc@nxb.vn', '02438220801'),
	('NXB002', 'NXB Trẻ', 'TP. Hồ Chí Minh', 'nxbtre@nxb.vn', '02838291145'),
	('NXB003', 'NXB Kim Đồng', 'Hà Nội', 'kimdong@nxb.vn', '02439432856'),
	('NXB004', 'NXB Lao Động', 'Hà Nội', 'laodong@nxb.vn', '02437336888'),
	('NXB005', 'NXB Văn Học', 'Hà Nội', 'vanhoc@nxb.vn', '02439422123'),
	('NXB006', 'NXB Khoa Học & Kỹ Thuật', 'Hà Nội', 'khkt@nxb.vn', '02438692222'),
	('NXB007', 'NXB Chính Trị Quốc Gia', 'Hà Nội', 'chinhtri@nxb.vn', '02437423333'),
	('NXB008', 'NXB Thống Kê', 'Hà Nội', 'thongke@nxb.vn', '02437324444'),
	('NXB009', 'NXB Tổng Hợp TP.HCM', 'TP. Hồ Chí Minh', 'tonghop@nxb.vn', '02838339999'),
	('NXB010', 'NXB Phụ Nữ', 'Hà Nội', 'phunu@nxb.vn', '02439281111'),
	('NXB011', 'NXB Công Thương', 'Hà Nội', 'congthuong@nxb.vn', '02438336666'),
	('NXB012', 'NXB Đại Học Quốc Gia Hà Nội', 'Hà Nội', 'dhqghn@nxb.vn', '02437548888'),
	('NXB013', 'NXB Đại Học Quốc Gia TP.HCM', 'TP. Hồ Chí Minh', 'dhqgtphcm@nxb.vn', '02837221111'),
	('NXB014', 'NXB Xây Dựng', 'Hà Nội', 'xaydung@nxb.vn', '02438775555'),
	('NXB015', 'NXB Giao Thông Vận Tải', 'Hà Nội', 'gtvt@nxb.vn', '02439429999');

-- Dumping structure for table thuvienutt.phieudattruoc
CREATE TABLE IF NOT EXISTS `phieudattruoc` (
  `MaPhieuDat` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `MaDG` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `MaSach` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `NgayDat` date NOT NULL,
  `TrangThai` varchar(20) COLLATE utf8mb4_unicode_520_ci DEFAULT NULL,
  `NgayHetHan` date DEFAULT NULL,
  PRIMARY KEY (`MaPhieuDat`),
  KEY `MaDG` (`MaDG`),
  KEY `MaSach` (`MaSach`),
  CONSTRAINT `phieudattruoc_ibfk_1` FOREIGN KEY (`MaDG`) REFERENCES `docgia` (`MaDG`),
  CONSTRAINT `phieudattruoc_ibfk_2` FOREIGN KEY (`MaSach`) REFERENCES `sach` (`MaSach`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- Dumping data for table thuvienutt.phieudattruoc: ~0 rows (approximately)

-- Dumping structure for table thuvienutt.phieutra
CREATE TABLE IF NOT EXISTS `phieutra` (
  `MaPT` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `MaMT` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `NgayTra` date DEFAULT NULL,
  `TinhTrang` varchar(50) COLLATE utf8mb4_unicode_520_ci DEFAULT NULL,
  PRIMARY KEY (`MaPT`),
  KEY `MaMT` (`MaMT`),
  CONSTRAINT `phieutra_ibfk_1` FOREIGN KEY (`MaMT`) REFERENCES `muontra` (`MaMT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- Dumping data for table thuvienutt.phieutra: ~0 rows (approximately)

-- Dumping structure for table thuvienutt.sach
CREATE TABLE IF NOT EXISTS `sach` (
  `MaSach` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `MaTG` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `MaNXB` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `MaTL` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `TenSach` varchar(100) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `NamXB` int NOT NULL,
  `SoLuong` int DEFAULT NULL,
  `MoTa` text COLLATE utf8mb4_unicode_520_ci,
  `MaNN` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `MaViTri` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  PRIMARY KEY (`MaSach`),
  KEY `MaTG` (`MaTG`),
  KEY `MaNXB` (`MaNXB`),
  KEY `MaTL` (`MaTL`),
  KEY `MaNN` (`MaNN`),
  KEY `MaViTri` (`MaViTri`),
  CONSTRAINT `sach_ibfk_1` FOREIGN KEY (`MaTG`) REFERENCES `tacgia` (`MaTG`),
  CONSTRAINT `sach_ibfk_2` FOREIGN KEY (`MaNXB`) REFERENCES `nhaxuatban` (`MaNXB`),
  CONSTRAINT `sach_ibfk_3` FOREIGN KEY (`MaTL`) REFERENCES `theloai` (`MaTL`),
  CONSTRAINT `sach_ibfk_4` FOREIGN KEY (`MaNN`) REFERENCES `ngonngu` (`MaNN`),
  CONSTRAINT `sach_ibfk_5` FOREIGN KEY (`MaViTri`) REFERENCES `kesach` (`MaViTri`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- Dumping data for table thuvienutt.sach: ~14 rows (approximately)
INSERT INTO `sach` (`MaSach`, `MaTG`, `MaNXB`, `MaTL`, `TenSach`, `NamXB`, `SoLuong`, `MoTa`, `MaNN`, `MaViTri`) VALUES
	('S001', 'TG006', 'NXB001', 'TL006', 'Lập trình Java cơ bản', 2021, 20, 'Giáo trình Java cho sinh viên CNTT', 'NN001', 'KS001'),
	('S002', 'TG006', 'NXB001', 'TL006', 'Lập trình Java nâng cao', 2022, 15, 'Java OOP và Swing', 'NN001', 'KS001'),
	('S003', 'TG009', 'NXB009', 'TL006', 'Cơ sở dữ liệu', 2020, 18, 'SQL và thiết kế CSDL', 'NN001', 'KS001'),
	('S004', 'TG009', 'NXB009', 'TL006', 'Hệ điều hành', 2019, 12, 'Nguyên lý hệ điều hành', 'NN001', 'KS001'),
	('S005', 'TG002', 'NXB004', 'TL005', 'Nguyên lý động cơ', 2018, 10, 'Động cơ đốt trong', 'NN001', 'KS002'),
	('S006', 'TG002', 'NXB004', 'TL005', 'Hệ thống truyền động', 2019, 8, 'Cơ khí động lực ô tô', 'NN001', 'KS002'),
	('S007', 'TG003', 'NXB014', 'TL016', 'Kết cấu bê tông', 2020, 14, 'Công trình xây dựng', 'NN001', 'KS003'),
	('S008', 'TG003', 'NXB014', 'TL016', 'Cơ học đất', 2019, 9, 'Nền móng công trình', 'NN001', 'KS003'),
	('S009', 'TG008', 'NXB006', 'TL007', 'Kinh tế vận tải', 2021, 16, 'Kinh tế ngành vận tải', 'NN001', 'KS004'),
	('S010', 'TG008', 'NXB006', 'TL007', 'Logistics căn bản', 2022, 20, 'Chuỗi cung ứng', 'NN001', 'KS004'),
	('S011', 'TG001', 'NXB005', 'TL001', 'Cho tôi xin một vé đi tuổi thơ', 2015, 25, 'Truyện thiếu nhi nổi tiếng', 'NN001', 'KS006'),
	('S012', 'TG002', 'NXB005', 'TL001', 'Chí Phèo', 2014, 30, 'Tác phẩm văn học Việt Nam', 'NN001', 'KS006'),
	('S013', 'TG006', 'NXB003', 'TL002', 'Harry Potter', 2010, 18, 'Tiểu thuyết giả tưởng', 'NN002', 'KS007'),
	('S014', 'TG009', 'NXB003', 'TL002', 'The Da Vinci Code', 2012, 12, 'Tiểu thuyết trinh thám', 'NN002', 'KS007');

-- Dumping structure for table thuvienutt.tacgia
CREATE TABLE IF NOT EXISTS `tacgia` (
  `MaTG` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `TenTG` varchar(50) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `NamSinh` int DEFAULT NULL,
  `GioiTinh` varchar(20) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `QuocTich` varchar(30) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  PRIMARY KEY (`MaTG`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- Dumping data for table thuvienutt.tacgia: ~15 rows (approximately)
INSERT INTO `tacgia` (`MaTG`, `TenTG`, `NamSinh`, `GioiTinh`, `QuocTich`) VALUES
	('TG001', 'Nguyễn Nhật Ánh', 1955, 'Nam', 'Việt Nam'),
	('TG002', 'Nam Cao', 1915, 'Nam', 'Việt Nam'),
	('TG003', 'Tô Hoài', 1920, 'Nam', 'Việt Nam'),
	('TG004', 'Xuân Diệu', 1916, 'Nam', 'Việt Nam'),
	('TG005', 'Nguyễn Du', 1765, 'Nam', 'Việt Nam'),
	('TG006', 'J.K. Rowling', 1965, 'Nữ', 'Anh'),
	('TG007', 'Haruki Murakami', 1949, 'Nam', 'Nhật Bản'),
	('TG008', 'Paulo Coelho', 1947, 'Nam', 'Brazil'),
	('TG009', 'Dan Brown', 1964, 'Nam', 'Mỹ'),
	('TG010', 'Agatha Christie', 1890, 'Nữ', 'Anh'),
	('TG011', 'Arthur Conan Doyle', 1859, 'Nam', 'Anh'),
	('TG012', 'Ernest Hemingway', 1899, 'Nam', 'Mỹ'),
	('TG013', 'Victor Hugo', 1802, 'Nam', 'Pháp'),
	('TG014', 'Lev Tolstoy', 1828, 'Nam', 'Nga'),
	('TG015', 'Fyodor Dostoevsky', 1821, 'Nam', 'Nga');

-- Dumping structure for table thuvienutt.theloai
CREATE TABLE IF NOT EXISTS `theloai` (
  `MaTL` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `TenTL` varchar(50) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  PRIMARY KEY (`MaTL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- Dumping data for table thuvienutt.theloai: ~19 rows (approximately)
INSERT INTO `theloai` (`MaTL`, `TenTL`) VALUES
	('TL001', 'Văn học Việt Nam'),
	('TL002', 'Văn học nước ngoài'),
	('TL003', 'Thiếu nhi'),
	('TL004', 'Giáo trình'),
	('TL005', 'Khoa học'),
	('TL006', 'Công nghệ thông tin'),
	('TL007', 'Kinh tế'),
	('TL008', 'Quản trị kinh doanh'),
	('TL009', 'Marketing'),
	('TL010', 'Tài chính – Ngân hàng'),
	('TL011', 'Luật'),
	('TL012', 'Y học'),
	('TL013', 'Tâm lý học'),
	('TL014', 'Kỹ năng sống'),
	('TL015', 'Ngoại ngữ'),
	('TL016', 'Lịch sử'),
	('TL017', 'Địa lý'),
	('TL018', 'Triết học'),
	('TL019', 'Văn hóa – Xã hội');

-- Dumping structure for table thuvienutt.thethuvien
CREATE TABLE IF NOT EXISTS `thethuvien` (
  `MaThe` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `MaDG` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `NgayCap` date NOT NULL,
  `NgayHetHan` date NOT NULL,
  `TrangThai` varchar(20) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  PRIMARY KEY (`MaThe`),
  KEY `MaDG` (`MaDG`),
  CONSTRAINT `thethuvien_ibfk_1` FOREIGN KEY (`MaDG`) REFERENCES `docgia` (`MaDG`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- Dumping data for table thuvienutt.thethuvien: ~1 rows (approximately)
INSERT INTO `thethuvien` (`MaThe`, `MaDG`, `NgayCap`, `NgayHetHan`, `TrangThai`) VALUES
	('TTV001', 'DG001', '2025-06-01', '2025-07-01', 'Hết hiệu lực');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
