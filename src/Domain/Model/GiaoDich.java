package Domain.Model;

import java.util.Date;

public abstract class GiaoDich {
    private String maGiaoDich;
    private Date ngayGiaoDich;
    private double donGia;
    private double dienTich;
    private String thongTinKhac;

    public GiaoDich(String maGiaoDich, Date ngayGiaoDich, double donGia, double dienTich, String thongTinKhac) {
        this.maGiaoDich = maGiaoDich;
        this.ngayGiaoDich = ngayGiaoDich;
        this.donGia = donGia;
        this.dienTich = dienTich;
        this.thongTinKhac = thongTinKhac;
    }

    // Getters and setters (omitted for brevity)

   
    public String getMaGiaoDich() {
        return maGiaoDich;
    }

    public void setMaGiaoDich(String maGiaoDich) {
        this.maGiaoDich = maGiaoDich;
    }

    public Date getNgayGiaoDich() {
        return ngayGiaoDich;
    }

    public void setNgayGiaoDich(Date ngayGiaoDich) {
        this.ngayGiaoDich = ngayGiaoDich;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public double getDienTich() {
        return dienTich;
    }

    public void setDienTich(double dienTich) {
        this.dienTich = dienTich;
    }

    public String getThongTinKhac() {
        return thongTinKhac;
    }

    public void setThongTinKhac(String thongTinKhac) {
        this.thongTinKhac = thongTinKhac;
    }
    @Override
    public String toString() {
        return "Mã giao dịch: " + maGiaoDich + ", Ngày giao dịch: " + ngayGiaoDich + ", Đơn giá: " + donGia
                + ", Diện tích: " + dienTich + ", Thông tin khác: " + thongTinKhac + ", Thành tiền: " + tinhThanhTien();
    }

    public abstract double tinhThanhTien();
}

