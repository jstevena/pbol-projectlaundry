-- Database katalog layanan laundry
-- Cara pakai: jalankan file ini di phpMyAdmin (tab SQL) atau mysql client

CREATE DATABASE IF NOT EXISTS dblaundry;
USE dblaundry;

CREATE TABLE IF NOT EXISTS layanan (
  kodelayanan varchar(10)  NOT NULL,
  namalayanan varchar(50)  NOT NULL,
  satuan      varchar(10)  NOT NULL,
  tarif       double       NOT NULL DEFAULT 0,
  estimasi    int          NOT NULL DEFAULT 0,
  gambar      varchar(100) DEFAULT NULL,
  PRIMARY KEY (kodelayanan)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- estimasi dalam jam; gambar = lokasi file relatif terhadap folder project
INSERT IGNORE INTO layanan (kodelayanan, namalayanan, satuan, tarif, estimasi, gambar) VALUES
('L001', 'Cuci Kiloan',        'kg',     6000,  48, 'images\\cuci_kiloan.png'),
('L002', 'Cuci Setrika Kiloan','kg',     8000,  48, 'images\\cuci_setrika.png'),
('L003', 'Setrika Saja',       'kg',     5000,  24, 'images\\setrika.png'),
('L004', 'Cuci Express',       'kg',    12000,   6, 'images\\express.png'),
('L005', 'Bed Cover',          'pcs',   25000,  72, 'images\\bed_cover.png'),
('L006', 'Cuci Sepatu',        'pasang',30000,  72, 'images\\sepatu.png');
