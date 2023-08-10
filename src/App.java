import javax.swing.SwingUtilities;
import Domain.GiaoDichServiceImpl;
import Persistence.GiaoDichDAOlmpl;
import Persistence.GiaDichGateway;
import Persistence.GiaDichJdbcGateway;
import Domain.GiaoDichService;
import Presentation.GiaoDichManagementUI;

public class App {
    public static void main(String[] args) {
        // Create an instance of GiaDichGateway
        GiaDichGateway giaoDichGateway = new GiaDichJdbcGateway("jdbc:sqlserver://localhost:1433;databaseName=GiaoDich;encrypt=true;trustServerCertificate=true", "sa", "12345");

        // Create an instance of GiaoDichDAO and pass GiaDichGateway to its constructor
        GiaoDichDAOlmpl giaoDichDAO = new GiaoDichDAOlmpl(giaoDichGateway);

        // Create an instance of GiaoDichService and pass GiaoDichDAO to its constructor
        GiaoDichService giaoDichService = new GiaoDichServiceImpl(giaoDichDAO);

        // Create an instance of the UI
        SwingUtilities.invokeLater(() -> {
            GiaoDichManagementUI ui = new GiaoDichManagementUI(giaoDichService);
            ui.setVisible(true);
        });
    }
}
