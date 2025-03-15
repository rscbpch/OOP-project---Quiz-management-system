import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Admin extends User {
    public Admin(String username, String firstName, String lastName, String email, String password, String role) {
        super(username, firstName, lastName, email, password, "Admin");
    }


    
    //
    public void manageUser(int option,int userId,String username, String firstName, String lastName, String email, String password, String role){
        switch (option){
            case 1:
            addAdmin();
            break;

            case 2:
            viewAllUser();
            break;

            case 3:
            viewUserById(userId);
            break;

            case 4:
            editUser(userId);
            break;

            case 5:
            removeUser(userId);
            break;
            
        }
    }

    //helper function for checking if the username is already exist
    private boolean isUsernameExist(String username){
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            // Execute query and check if the username exists
            var resultSet = stmt.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error checking username: " + e.getMessage());
        }
        return false;
    }

    // helper function 
    private String inputUsername(){
        Scanner scanner = new Scanner(System.in);
        String username;

        while (true){
            System.out.println("Input username :");
            username = scanner.nextLine();

            if(isUsernameExist(username)){
                System.out.println("Username already exists. Please choose another username.");
            }else{
                break;
            }
        }

        return username;
    }

    // Helper function to input a valid first name (non-empty)
    private String inputFirstName(){
        Scanner scanner = new Scanner(System.in);
        String firstName;

        while (true) {
            System.out.println("Input first name:");
            firstName = scanner.nextLine().trim();

            if (firstName.isEmpty()) {
                System.out.println("First name cannot be empty. Please try again.");
            } else {
                break;
            }
        }

        return firstName;
    }

    // Helper function to input a valid last name (non-empty)
    private String inputLastName(){
        Scanner scanner = new Scanner(System.in);
        String lastName;

        while (true) {
            System.out.println("Input last name:");
            lastName = scanner.nextLine().trim();

            if (lastName.isEmpty()) {
                System.out.println("Last name cannot be empty. Please try again.");
            } else {
                break;
            }
        }

        return lastName;
    }

    // Helper function to input a valid email (basic regex check)
    private String inputEmail(){
        Scanner scanner = new Scanner(System.in);
        String email;
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

        while (true) {
            System.out.println("Input email:");
            email = scanner.nextLine().trim();

            Matcher matcher = emailPattern.matcher(email);
            if (matcher.matches()) {
                break;
            } else {
                System.out.println("Invalid email format. Please try again.");
            }
        }

        return email;
    }

    // Helper function to input a valid password (minimum 6 characters)
    private String inputPassword(){
        Scanner scanner = new Scanner(System.in);
        String password;

        while (true) {
            System.out.println("Input password (at least 6 characters):");
            password = scanner.nextLine().trim();

            if (password.length() < 6) {
                System.out.println("Password must be at least 6 characters long. Please try again.");
            } else {
                break;
            }
        }

        return password;
    }



    //addUser function (admin)
    private void addAdmin(){
        String username = inputUsername(); 
        String firstName = inputFirstName();
        String lastName = inputLastName();
        String email = inputEmail();
        String password = inputPassword();
        String role = "Admin";

        String hashedPassword = PasswordHasher.hashPassword(password);

        Admin newAdmin = new Admin(username, firstName, lastName, email, hashedPassword, role);
        insertUserToDatabase(username, firstName, lastName, email, hashedPassword, "Admin");
        System.out.println("New admin added successfully!");
    };

    //function for get all existen user in system including admin,teacher,student 
    private void viewAllUser(){
        //will be complete later
    };

    private void viewUserById(int userId){};

    private void editUser(int userId){};

    //a function that allow only admin to delete any user from the system
    private void removeUser(int userId){
        //
    };


    public void manageQuiz(int option,int quizId){
        switch (option) {
            case 1:
            listAllQuiz();
            break;

            case 2:
            if (quizId != -1){
                listQuizById(quizId);
            }else{
                System.out.println("Please provide a quiz ID.");
            }
            break;

            case 3:
            if (quizId != -1){
                editQuiz(quizId);
            }else{
                System.out.println("Please provide a quiz ID.");
            }
            break;
            
            case 4:
            if (quizId != -1){
                removeQuiz(quizId);
            }else{
                System.out.println("Please provide a quiz ID.");
            }
            break;
        }
    }

    //function that list all the existing quiz
    private void listAllQuiz(){};
    // list the quiz by input id 
    private void listQuizById(int quizId){};
    //edit quiz
    private void editQuiz(int quizId){};
    //function to remove any quiz by input it quizId
    private void removeQuiz(int quizId){};

    public static void main(String[] args) {
        // Simulate user input for testing
        Admin admin = new Admin("admin123", "John", "Doe", "johndoe@example.com", "password123", "Admin");

        // Call addAdmin to test the function
        admin.addAdmin();
    }
}

