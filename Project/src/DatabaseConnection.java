import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection connect() throws SQLException {
        // Replace with your actual database details
        String host = "mysql-2fe6a39d-quiz-management-system.h.aivencloud.com";  
        String port = "21755";           
        String databaseName = "quiz_management_system";   
        String userName = "avnadmin";       
        String password = "AVNS_JNOUW45voZhtfbJKk_Q";

        // Establishing the connection
        return DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + databaseName + "?sslmode=require", userName, password);
    }
}
