import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        this.username = username.trim();
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
        this.email = email.trim();
        this.password = PasswordHasher.hashPassword(password.trim()); 
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = PasswordHasher.hashPassword(password.trim()); 
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
        String username, email;
    
        while (true) {
            System.out.println("- Create a new account");
            System.out.print("Enter first name: ");
            String firstName = sc.nextLine().trim();
            System.out.print("Enter last name: ");
            String lastName = sc.nextLine().trim();
    
            while (true) {
                System.out.print("Enter username: ");
                username = sc.nextLine().trim();
                System.out.print("Enter email: ");
                email = sc.nextLine().trim();
    
                if (isUsernameOrEmailExist(username, email)) {
                    System.out.println("\nUsername or Email already exists. Please choose a different one.\n");
                } else {
                    break;
                }
            }
    
            System.out.print("Enter password: ");
            String password = sc.nextLine().trim();
            String hashedPassword = PasswordHasher.hashPassword(password);
    
            insertUserToDatabase(username, firstName, lastName, email, hashedPassword, role);
    
            if (role.equalsIgnoreCase("Student")) {
                return new Student(username, firstName, lastName, email, hashedPassword, role);
            } else {
                return new Teacher(username, firstName, lastName, email, hashedPassword, role);
            }
        }
    }    
    
    public static boolean isUsernameOrEmailExist(String username, String email) {
        String query = "SELECT COUNT(*) FROM users WHERE user_name = ? OR email = ?";
    
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, username);
            stmt.setString(2, email);
    
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; 
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return false; 
    }    

    public static void insertUserToDatabase(String username, String firstName, String lastName, String email, String hashedPassword, String role) {
        String query = "INSERT INTO users (user_name, first_name, last_name, email, password, role) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, email);
            stmt.setString(5, hashedPassword); 
            stmt.setString(6, role);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User successfully registered in the database!");
            } else {
                System.out.println("Failed to register user.");
            }
        } catch (SQLException e) {
            System.out.println("Error inserting user into database: " + e.getMessage());
        }
    }

    public static User loginUser(String email, String password) {
    String query = "SELECT user_id, user_name, first_name, last_name, email, password, role FROM users WHERE email = ?";

    try (Connection conn = DatabaseConnection.connect();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setString(1, email.trim());
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String storedHashedPassword = rs.getString("password");
            String enteredHashedPassword = PasswordHasher.hashPassword(password.trim());

            if (storedHashedPassword.equals(enteredHashedPassword)) {
                String username = rs.getString("user_name");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String role = rs.getString("role");

                System.out.println("\nLogin successful!");
                return role.equalsIgnoreCase("Student") ?
                        new Student(username, firstName, lastName, email, storedHashedPassword, role) :
                        new Teacher(username, firstName, lastName, email, storedHashedPassword, role);
            } else {
                System.out.println("\nIncorrect password. Try again.");
            }
        } else {
            System.out.println("\nUser not found. Please register.");
        }
    } catch (SQLException e) {
        System.out.println("Database error: " + e.getMessage());
    }
    return null;
}

}
