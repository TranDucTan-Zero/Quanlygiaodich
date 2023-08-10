package Persistence;

import java.util.List;
import Domain.Model.GiaoDich;

public class GiaoDichDAOlmpl implements GiaoDichDAO {
    private GiaDichGateway giaoDichGateway;

    public GiaoDichDAOlmpl(GiaDichGateway giaoDichGateway) {
        this.giaoDichGateway = giaoDichGateway;
    }

    @Override
    public void addGiaoDich(GiaoDich giaoDich) {
        giaoDichGateway.addGiaoDich(giaoDich);
    }

    @Override
    public void removeGiaoDich(String maGiaoDich) {
        giaoDichGateway.removeGiaoDich(maGiaoDich);
    }

    @Override
    public void editGiaoDich(String maGiaoDich, GiaoDich giaoDich) {
        giaoDichGateway.editGiaoDich(maGiaoDich, giaoDich);
    }

    @Override
    public GiaoDich getGiaoDichByMa(String maGiaoDich) {
        return giaoDichGateway.getGiaoDichByMa(maGiaoDich);
    }

    @Override
    public List<GiaoDich> getAllGiaoDich() {
        return giaoDichGateway.getAllGiaoDich();
    }

    @Override
    public int countLoaiDat(String loaiDat) {
        return giaoDichGateway.countLoaiDat(loaiDat);
    }

    @Override
    public int countLoaiNha(String loaiNha) {
        return giaoDichGateway.countLoaiNha(loaiNha);
    }

    @Override
    public double averageThanhTienDat() {
        return giaoDichGateway.averageThanhTienDat();
    }

    @Override
    public List<GiaoDich> searchGiaoDichByMa(String maGiaoDich) {
        return giaoDichGateway.searchGiaoDichByMa(maGiaoDich);
    }
}
