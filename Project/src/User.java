import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public interface User {
    String getUsername();
    String getFirstName();
    String getLastName();
    String getEmail();
    int getId();
    
    static int generateVerificationCode() {
        Random rand = new Random();
        return 100000 + rand.nextInt(900000); 
    }

    static void updateUserInDatabase(String currentEmail, String newUsername, String newFirstName, String newLastName, String newEmail, String newPassword) {
        String query = "UPDATE users SET "
                + "user_name = COALESCE(?, user_name), "
                + "first_name = COALESCE(?, first_name), "
                + "last_name = COALESCE(?, last_name), "
                + "email = COALESCE(?, email), "
                + "password = COALESCE(SHA2(?, 256), password) "
                + "WHERE email = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set parameters (empty values mean "keep old data")
            stmt.setString(1, newUsername.isEmpty() ? null : newUsername);
            stmt.setString(2, newFirstName.isEmpty() ? null : newFirstName);
            stmt.setString(3, newLastName.isEmpty() ? null : newLastName);
            stmt.setString(4, newEmail.isEmpty() ? null : newEmail);
            stmt.setString(5, newPassword.isEmpty() ? null : newPassword);
            stmt.setString(6, currentEmail);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User updated successfully!");
            } else {
                System.out.println("User not found or no changes made.");
            }

        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
        }
    }

    static User selectUserFromDatabase(String email, String password) {
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
                            new Student(username, firstName, lastName, email, storedHashedPassword) :
                            new Teacher(username, firstName, lastName, email, storedHashedPassword);
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

    static void insertUserToDatabase(String username, String firstName, String lastName, String email, String hashedPassword, String role) {
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
                System.out.println("User registered successfully!");
            } else {
                System.out.println("Failed to register user.");
            }
        } catch (SQLException e) {
            System.out.println("Error inserting user into database: " + e.getMessage());
        }
    }
}
