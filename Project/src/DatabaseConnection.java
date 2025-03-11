import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
  public static void main(String[] args) throws ClassNotFoundException {
    // Manually set your database connection details here
    String host = "mysql-2fe6a39d-quiz-management-system.h.aivencloud.com";      // Change to your MySQL host (e.g., "localhost" or an IP address)
    String port = "21755";           // Change to your MySQL port (default is "3306")
    String databaseName = "quiz_management_system";   // Change to the name of your database
    String userName = "avnadmin";       // Change to your MySQL username
    String password = "AVNS_JNOUW45voZhtfbJKk_Q"; // Change to your MySQL password

    // JDBC allows nullable username and password, but we will use them
    if (host == null || port == null || databaseName == null) {
      System.out.println("Host, port, and database information are required.");
      return;
    }

    // Load the MySQL JDBC driver
    Class.forName("com.mysql.cj.jdbc.Driver");

    try (final Connection connection =
                DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + databaseName + "?sslmode=require", userName, password);
        final Statement statement = connection.createStatement();
        final ResultSet resultSet = statement.executeQuery("SELECT version() AS version")) {

      // Print the MySQL version
      while (resultSet.next()) {
        System.out.println("Version: " + resultSet.getString("version"));
      }
    } catch (SQLException e) {
      System.out.println("Connection failure.");
      e.printStackTrace();
    }
  }
}

