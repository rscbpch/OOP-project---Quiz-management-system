import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserAuthentication {
    public static String userLogin() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your email: ");
        String email = sc.nextLine();

        System.out.print("Enter your password: ");
        String password = sc.nextLine();

        // Hash the input password using SHA-256
        String hashedPassword = PasswordHasher.hashPassword(password);

        // Query to check if the email and hashed password match
        try (Connection conn = DatabaseConnection.connect()) {
            String query = "SELECT email FROM users WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, hashedPassword);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Login successful!");
                return email;  // Return email instead of user object
            } else {
                System.out.println("Invalid credentials. Try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

            if (role.equalsIgnoreCase("Teacher")) {
                int verificationCode = User.generateVerificationCode();
                System.out.println("\nYour verification code: " + verificationCode);
                System.out.print("Enter the verification code: ");

                try {
                    int enteredCode = Integer.parseInt(sc.nextLine().trim());
                    if (enteredCode != verificationCode) {
                        System.out.println("Incorrect verification code! Registration failed.");
                        return null;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid verification code! Registration failed.");
                    return null;
                }
            }

            User.insertUserToDatabase(username, firstName, lastName, email, hashedPassword, role);

            if (role.equalsIgnoreCase("Student")) {
                return new Student(username, firstName, lastName, email, hashedPassword);
            } else {
                return new Teacher(username, firstName, lastName, email, hashedPassword);
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

    public static int getUserIdByEmail(String email) {
        String query = "SELECT user_id FROM users WHERE email = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("user_id");
            }

        } catch (SQLException e) {
            System.out.println("Error fetching user ID: " + e.getMessage());
        }
        return -1; // Return -1 if user is not found
    }
}
