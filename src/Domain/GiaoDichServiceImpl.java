package Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import Domain.Model.GiaoDich;
import Domain.Model.GiaoDichDat;
import Domain.Model.GiaoDichNha;

import Persistence.GiaoDichDAO;

public class GiaoDichServiceImpl implements GiaoDichService {
    private final Map<String, GiaoDich> giaoDichMap;
    private GiaoDichDAO giaoDichDAO;

    public GiaoDichServiceImpl(GiaoDichDAO giaoDichDAO) {
        giaoDichMap = new HashMap<>();
        this.giaoDichDAO = giaoDichDAO;
    }

    @Override
    public void addGiaoDich(GiaoDich giaoDich) {
        giaoDichMap.put(giaoDich.getMaGiaoDich(), giaoDich);
        giaoDichDAO.addGiaoDich(giaoDich);
    }

    @Override
    public void removeGiaoDich(String maGiaoDich) {
        giaoDichMap.remove(maGiaoDich);
        giaoDichDAO.removeGiaoDich(maGiaoDich);
    }

    @Override
    public void editGiaoDich(String maGiaoDich, GiaoDich giaoDich) {
        giaoDichMap.put(maGiaoDich, giaoDich);
        giaoDichDAO.editGiaoDich(maGiaoDich, giaoDich);
    }

    @Override
    public GiaoDich getGiaoDichByMa(String maGiaoDich) {
        return giaoDichMap.get(maGiaoDich);
    }

    @Override
    public List<GiaoDich> getAllGiaoDich() {
        return new ArrayList<>(giaoDichMap.values());
    }

    @Override
    public int countLoaiDat(String loaiDat) {
        int count = 0;
        for (GiaoDich giaoDich : giaoDichMap.values()) {
            if (giaoDich instanceof GiaoDichDat && ((GiaoDichDat) giaoDich).getLoaiDat().equalsIgnoreCase(loaiDat)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int countLoaiNha(String loaiNha) {
        int count = 0;
        for (GiaoDich giaoDich : giaoDichMap.values()) {
            if (giaoDich instanceof GiaoDichNha && ((GiaoDichNha) giaoDich).getLoaiNha().equalsIgnoreCase(loaiNha)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public double averageThanhTienDat() {
        double sum = 0;
        int count = 0;
        for (GiaoDich giaoDich : giaoDichMap.values()) {
            if (giaoDich instanceof GiaoDichDat) {
                sum += giaoDich.tinhThanhTien();
                count++;
            }
        }
        if (count == 0) {
            return 0;
        }
        return sum / count;
    }

    @Override
    public List<GiaoDich> searchGiaoDichByMa(String maGiaoDich) {
        List<GiaoDich> result = new ArrayList<>();
        for (GiaoDich giaoDich : giaoDichMap.values()) {
            if (giaoDich.getMaGiaoDich().equalsIgnoreCase(maGiaoDich)) {
                result.add(giaoDich);
            }
        }
        return result;
    }
}
