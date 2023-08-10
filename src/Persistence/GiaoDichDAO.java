package Persistence;

import java.util.List;

import Domain.Model.GiaoDich;

public interface GiaoDichDAO {
    void addGiaoDich(GiaoDich giaoDich);
    void removeGiaoDich(String maGiaoDich);
    void editGiaoDich(String maGiaoDich, GiaoDich giaoDich);
    GiaoDich getGiaoDichByMa(String maGiaoDich);
    List<GiaoDich> getAllGiaoDich();
    int countLoaiDat(String loaiDat);
    int countLoaiNha(String loaiNha);
    double averageThanhTienDat();
   
    List<GiaoDich> searchGiaoDichByMa(String maGiaoDich);
}
