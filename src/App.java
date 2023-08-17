import javax.swing.SwingUtilities;
import Domain.GiaoDichServiceImpl;
import Persistence.GiaDichGateway;
import Persistence.GiaDichJdbcGateway;
import Persistence.GiaoDichDAOlmpl;
import Domain.GiaoDichService;
import Presentation.GiaoDichManagementUI;

public class App {
    public static void main(String[] args) {
        // Khởi tạo gateway và service
        GiaDichGateway giaoDichGateway = new GiaDichJdbcGateway("jdbc:sqlserver://localhost:1433;databaseName=GiaoDich;encrypt=true;trustServerCertificate=true", "sa", "12345");
        GiaoDichDAOlmpl giaoDichDAO = new GiaoDichDAOlmpl(giaoDichGateway);
        GiaoDichService giaoDichService = new GiaoDichServiceImpl(giaoDichDAO); 
        // Khởi tạo và hiển thị giao diện
        SwingUtilities.invokeLater(() -> {
            GiaoDichManagementUI ui = new GiaoDichManagementUI(giaoDichService);
            ui.setVisible(true);
        });
    }
}
