import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;;

public class Main {
   public static void main(String[] args) {
        String jdbcUrl = "jdbc:sqlserver://localhost:1433;databaseName=Hello;encrypt=true;trustServerCertificate=true";
        String username = "sa";
        String password = "12345";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connected to the database");

            // Thực hiện truy vấn SELECT đơn giản
            String query = "SELECT 'Hello, SQL Server' AS message";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String message = resultSet.getString("message");
                System.out.println("Message from SQL Server: " + message);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

