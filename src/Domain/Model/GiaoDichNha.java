package Domain.Model;

import java.util.Date;

public class GiaoDichNha extends GiaoDich {
    private String loaiNha;

    public GiaoDichNha(String maGiaoDich, Date ngayGiaoDich, double donGia, double dienTich, String thongTinKhac) {
        super(maGiaoDich, ngayGiaoDich, donGia, dienTich, "");

        this.loaiNha = thongTinKhac;
    }

    public String getLoaiNha() {
        return loaiNha;
    }

    public void setLoaiNha(String loaiNha) {
        this.loaiNha = loaiNha;
    }

    @Override
    public double tinhThanhTien() {
        if ("Cao cấp".equalsIgnoreCase(loaiNha)) {
            return getDonGia() * getDienTich() * 1.5;
        } else if ("Thường".equalsIgnoreCase(loaiNha)) {
            return getDonGia() * getDienTich();
        } else {
            return 0;
        }
    }
}