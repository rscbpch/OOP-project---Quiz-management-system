import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public abstract class User {
    int id;
    String username;
    String firstName;
    String lastName;
    String email;
    private String password;
    String role;

    public User(String username, String firstName, String lastName, String email, String password, String role) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return new String(new char[password.length()]).replace("\0", "*");
    }

    public void displayInfo() {
        System.out.println("Username: " + username);
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Email: " + email);
        System.out.println("Role: " + role);
    }

    public static User userRegister(String role) {
        Scanner sc = new Scanner(System.in);
        System.out.println("- Create a new account");
        System.out.print("Enter first name: ");
        String firstName = sc.nextLine();
        System.out.print("Enter last name: ");
        String lastName = sc.nextLine();
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        insertUserToDatabase(username, firstName, lastName, email, password, role);
        if (role.equals("Student")) {
            return new Student(username, firstName, lastName, email, password, role);
        } else {
            return new Teacher(username, firstName, lastName, email, password, role);
        }
    }

    public static void insertUserToDatabase(String username, String firstName, String lastName, String email, String password, String role) {
        String tableName = "users";
        String query = "INSERT INTO " + tableName + " (username, first_name, last_name, email, password, role) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
        PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, email);
            stmt.setString(5, password);
            stmt.setString(6, role);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User successfully registered in the users table!");
            } else {
                System.out.println("Failed to register user in the database.");
            }
        } catch (SQLException e) {
            System.out.println("Error inserting data into database: " + e.getMessage());
        }
    }
}
