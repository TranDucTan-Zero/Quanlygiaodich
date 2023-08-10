package Persistence;

import Domain.Model.GiaoDich;
import java.util.List;

public interface GiaDichGateway {
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
