package Persistence;

import Domain.Model.GiaoDich;
import Domain.Model.GiaoDichDat;
import Domain.Model.GiaoDichNha;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GiaDichJdbcGateway implements GiaDichGateway {
    private final String jdbcUrl;
    private final String username;
    private final String password;

    public GiaDichJdbcGateway(String jdbcUrl, String username, String password) {
        this.jdbcUrl = "jdbc:sqlserver://localhost:1433;databaseName=GiaoDich;encrypt=true;trustServerCertificate=true";
        this.username = "sa";
        this.password = "12345";
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    @Override
    public void addGiaoDich(GiaoDich giaoDich) {
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO GiaoDich (MaGiaoDich, NgayGiaoDich, DonGia, DienTich, ThongTinKhac) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                setPreparedStatementValues(statement, giaoDich);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeGiaoDich(String maGiaoDich) {
        try (Connection connection = getConnection()) {
            String sql = "DELETE FROM GiaoDich WHERE MaGiaoDich = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, maGiaoDich);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editGiaoDich(String maGiaoDich, GiaoDich updatedGiaoDich) {
        try (Connection connection = getConnection()) {
            String sql = "UPDATE GiaoDich SET NgayGiaoDich = ?, DonGia = ?, DienTich = ?, ThongTinKhac = ? WHERE MaGiaoDich = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                setPreparedStatementValues(statement, updatedGiaoDich);
                statement.setString(5, maGiaoDich);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public GiaoDich getGiaoDichByMa(String maGiaoDich) {
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM GiaoDich WHERE MaGiaoDich = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, maGiaoDich);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapResultSetToGiaoDich(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<GiaoDich> getAllGiaoDich() {
        List<GiaoDich> giaoDichList = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM GiaoDich";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        GiaoDich giaoDich = mapResultSetToGiaoDich(resultSet);
                        giaoDichList.add(giaoDich);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return giaoDichList;
    }

    @Override
    public int countLoaiDat(String loaiDat) {
        int count = 0;
        try (Connection connection = getConnection()) {
            String sql = "SELECT COUNT(*) FROM GiaoDich WHERE LoaiDat = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, loaiDat);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        count = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int countLoaiNha(String loaiNha) {
        int count = 0;
        try (Connection connection = getConnection()) {
            String sql = "SELECT COUNT(*) FROM GiaoDich WHERE LoaiNha = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, loaiNha);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        count = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public double averageThanhTienDat() {
        double sum = 0;
        int count = 0;
        try (Connection connection = getConnection()) {
            String sql = "SELECT DonGia FROM GiaoDich WHERE LoaiDat = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, "dat");
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        sum += resultSet.getDouble("DonGia");
                        count++;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (count == 0) {
            return 0;
        }
        return sum / count;
    }

    @Override
    public List<GiaoDich> searchGiaoDichByMa(String maGiaoDich) {
        List<GiaoDich> giaoDichList = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM GiaoDich WHERE MaGiaoDich = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, maGiaoDich);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        GiaoDich giaoDich = mapResultSetToGiaoDich(resultSet);
                        giaoDichList.add(giaoDich);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return giaoDichList;
    }

   private GiaoDich mapResultSetToGiaoDich(ResultSet resultSet) throws SQLException {
        String maGiaoDich = resultSet.getString("MaGiaoDich");
        java.util.Date ngayGiaoDich = resultSet.getDate("NgayGiaoDich");
        double donGia = resultSet.getDouble("DonGia");
        double dienTich = resultSet.getDouble("DienTich");
        String thongTinKhac = resultSet.getString("ThongTinKhac");
        String loaiDat = resultSet.getString("LoaiDat");
        String loaiNha = resultSet.getString("LoaiNha");

        if ("dat".equalsIgnoreCase(loaiDat)) {
            return new GiaoDichDat(maGiaoDich, ngayGiaoDich, donGia, dienTich, thongTinKhac);
        } else if ("nha".equalsIgnoreCase(loaiNha)) {
            return new GiaoDichNha(maGiaoDich, ngayGiaoDich, donGia, dienTich, thongTinKhac);
        } else {
            throw new IllegalArgumentException("Loại giao dịch không hợp lệ");
        }
    }

    private void setPreparedStatementValues(PreparedStatement statement, GiaoDich giaoDich) throws SQLException {
        statement.setString(1, giaoDich.getMaGiaoDich());
        statement.setDate(2, new java.sql.Date(giaoDich.getNgayGiaoDich().getTime()));
        statement.setDouble(3, giaoDich.getDonGia());
        statement.setDouble(4, giaoDich.getDienTich());
        statement.setString(5, giaoDich.getThongTinKhac());
    }
}
