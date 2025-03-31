import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mysql.cj.xdevapi.PreparableStatement;

public class Admin implements User {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public Admin(String username, String firstName, String lastName, String email, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = PasswordHasher.hashPassword(password);
    }   

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    
    @Override
    public int getId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }

    public void adminMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int option;
    
        do {
            System.out.println("\nAdmin Main Menu:");
            System.out.println("1. Manage Users");
            System.out.println("2. Manage Quizzes");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");
    
            option = scanner.nextInt();
    
            switch (option) {
                case 1:
                    manageUser(); // Call user management menu
                    break;
                case 2:
                    manageQuiz(); // Call quiz management menu
                    break;
                case 0:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (option != 0);
    
        scanner.close();
    }
    


    
    //
    private void manageUser() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int option;
        
        do {
            System.out.println("\nUser Management Menu:");
            System.out.println("1. Add Admin");
            System.out.println("2. View All Users");
            System.out.println("3. View User by ID");
            System.out.println("4. Edit User");
            System.out.println("5. Remove User");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            
            option = scanner.nextInt();
            
            switch (option) {
                case 1:
                    addAdmin();
                    break;
                case 2:
                    viewAllUsers();
                    break;
                case 3:
                    viewUserById();
                    break;
                case 4:
                    editUser();
                    break;
                case 5:
                    removeUser();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (option != 0);
        
        scanner.close();
    }

    private void manageQuiz() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int option;
    
        do {
            System.out.println("\nQuiz Management Menu:");
            System.out.println("1. List All Quizzes");
            System.out.println("2. View Quiz by ID");
            System.out.println("3. Edit Quiz");
            System.out.println("4. Remove Quiz");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
    
            option = scanner.nextInt();
    
            switch (option) {
                case 1:
                    listAllQuiz();
                    break;
                case 2:
                    listQuizById();
                    break;
                case 3:
                    editQuiz();
                    break;
                case 4:
                    removeQuiz();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (option != 0);
    
        scanner.close();
    }
    

    //helper function for checking if the user_name is already exist
    private boolean isUser_nameExist(String user_name){
        String query = "SELECT COUNT(*) FROM users WHERE user_name = ?";
        try (Connection conn = DatabaseConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user_name);
            // Execute query and check if the user_name exists
            var resultSet = stmt.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error checking user_name: " + e.getMessage());
        }
        return false;
    }

    //helper function to check if the user with inputID exist 
    private boolean userExists(int userId) throws SQLException{
        String query = "select user_id from users where user_id = ?";
        try(Connection conn = DatabaseConnection.connect();
        PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1,userId);
            try(ResultSet rs = stmt.executeQuery()){
                return rs.next();
            }
        }
    }

        //helper function to check if the user with inputID exist 
        private boolean quizExists(int quizId) throws SQLException{
            String query = "select quiz_id from quizzes where quiz_id = ?";
            try(Connection conn = DatabaseConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query)){
                stmt.setInt(1,quizId);
                try(ResultSet rs = stmt.executeQuery()){
                    return rs.next();
                }
            }
        }

    //helper function for update the user information
    private boolean updateUserFields(int userId,String field,String newValue) throws SQLException{
        String query = "UPDATE users SET  "+ field + " = ? WHERE user_id = ?";
        try(Connection conn = DatabaseConnection.connect();
        PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, newValue);
            stmt.setInt(2,userId);
            return stmt.executeUpdate() > 0;
        }
    }

    // helper function 
    private String inputUser_name(){
        Scanner scanner = new Scanner(System.in);
        String user_name;

        while (true){
            System.out.println("Input user_name :");
            user_name = scanner.nextLine();

            if(isUser_nameExist(user_name)){
                System.out.println("User_name already exists. Please choose another user_name.");
            }else{
                break;
            }
        }

        return user_name;
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
    //helper fucntion for input  ID
    private int inputID (){
        Scanner scanner = new Scanner(System.in);
        int id;

        
        while (true){
            System.out.println("Input ID:");
            if (scanner.hasNextInt()) {
                id = scanner.nextInt();
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid  ID.");
                scanner.next();
            }
        }
        return id;
    }



    //addUser function (admin)
    private void addAdmin(){
        String user_name = inputUser_name(); 
        String firstName = inputFirstName();
        String lastName = inputLastName();
        String email = inputEmail();
        String password = inputPassword();
        String role = "admin";

        String hashedPassword = PasswordHasher.hashPassword(password);

        // Admin newAdmin = new Admin(user_name, firstName, lastName, email, hashedPassword, role);
        User.insertUserToDatabase(user_name, firstName, lastName, email, hashedPassword, "Admin");
        System.out.println("New admin added successfully!");
    };


    //function for get all existen user in system including admin,teacher,student 
    private void viewAllUsers() {
    String query = "SELECT user_id, user_name, first_name, last_name, email, role FROM users"; // Exclude password

    try (Connection conn = DatabaseConnection.connect();
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery()) {

        System.out.println("--------------------------------------------------------");

        while (rs.next()) {
            int id = rs.getInt("user_id");
            String userName = rs.getString("user_name");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String email = rs.getString("email");
            String role = rs.getString("role");

            System.out.println("User Details:");
                System.out.println("--------------------------------------------------------");
                System.out.println("ID: " + id);
                System.out.println("Username: " + userName);
                System.out.println("First Name: " + firstName);
                System.out.println("Last Name: " + lastName);
                System.out.println("Email: " + email);
                System.out.println("Role: " + role);
        }

    } catch (SQLException e) {
        System.out.println("Error retrieving users from database: " + e.getMessage());
    }
}


    private void viewUserById() throws SQLException{
        int userId = inputID();
        String query = "SELECT user_id, user_name, first_name, last_name, email, role FROM users where user_id = ?" ;

        try(Connection conn = DatabaseConnection.connect();
        PreparedStatement stmt = conn.prepareStatement(query)){
        stmt.setInt(1,userId);
        try (ResultSet rs = stmt.executeQuery()){
            if (rs.next()){
                int id = rs.getInt("user_id");
                String userName= rs.getString("user_name");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String role = rs.getString("role");

                System.out.println("User Details:");
                System.out.println("--------------------------------------------------------");
                System.out.println("ID: " + id);
                System.out.println("Username: " + userName);
                System.out.println("First Name: " + firstName);
                System.out.println("Last Name: " + lastName);
                System.out.println("Email: " + email);
                System.out.println("Role: " + role);
            }else{
                System.out.println("This user with ID: "+userId+" does not exist");
            }
        }
        }
        
    }

    private void editUser()throws SQLException{
        System.out.println("Please input user ID you want to edit information");
        int userId = inputID();
        Scanner scanner = new Scanner(System.in);
        
        if (!userExists(userId)){
            System.out.println("Error no user with this ID found pls try again");
        }

        while(true){
            System.out.println("\nWhat do you want to edit?");
            System.out.println("1. Username");
            System.out.println("2. First Name");
            System.out.println("3. Last Name");
            System.out.println("4. Email");
            System.out.println("5. Role");
            System.out.println("6. Exit");
            System.out.print("Choose an option (1-6): ");

            int choice = scanner.nextInt();
            // Ensure the user inputs a valid choice (1-4)
            while (true) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine(); // consume the newline
    
                    if (choice >= 1 && choice <= 6) {
                        break; // Exit the loop if the choice is valid
                    } else {
                        System.out.println("Error: Invalid choice. Please enter a number between 1 and 6.");
                    }
                } else {
                    System.out.println("Error: Please enter a valid number.");
                    scanner.nextLine(); // consume the invalid input
                }
            }

            
            switch (choice) {
                case 1:
                System.out.println("Input a new Username : ");
                String newUsername = scanner.nextLine();
                updateUserFields(userId, "user_name", newUsername);
                System.out.println("Username updated successfully.");
                break;
                case 2:
                System.out.println("Input a new firstname : ");
                String newFirstname = scanner.nextLine();
                updateUserFields(userId, "first_name", newFirstname);
                System.out.println("Firstname updated successfully.");
                break;
                case 3:
                System.out.println("Input a new Lastname : ");
                String newLastname = scanner.nextLine();
                updateUserFields(userId, "last_name", newLastname);
                System.out.println("Lastname updated successfully.");
                break;
                case 4:
                System.out.println("Input a new Email : ");
                String newEmail = scanner.nextLine();
                updateUserFields(userId, "email", newEmail);
                System.out.println("Email updated successfully.");
                break;
                case 5:
                System.out.println("Input a new role : ");
                String newRole = scanner.nextLine();
                updateUserFields(userId, "role", newRole);
                System.out.println("Role updated successfully.");
                break;
                case 6:
                System.out.println("Exit the program........");
                return;
                default:
                System.out.println("Error Invalid choice");
            }
        }
    }

    //a function that allow only admin to delete any user from the system
    private void removeUser() throws SQLException{
        int userId = inputID();

        if(!userExists(userId)){
            System.out.println("Error: No user found with ID " + userId);
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you sure you want to remove this user? (yes/no)");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if(!confirmation.equals("yes")){
            System.out.println("User removal cancled");
            return;
        }
        String query = "DELETE from users where user_id = ?";
        try (Connection conn = DatabaseConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User with ID " + userId + " has been removed successfully.");
            } else {
                System.out.println("Error: Unable to remove user.");
            }
        } catch (SQLException e) {
            System.out.println("Error removing user: " + e.getMessage());
        }
    }


    

    //function that list all the existing quiz
    private void listAllQuiz(){
        String query = "select * from quizzes";

        try (Connection conn = DatabaseConnection.connect();
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery()) {

        System.out.println("--------------------------------------------------------");

        while (rs.next()) {
            int id = rs.getInt("quiz_id");
            int creator_id = rs.getInt("creator_id");
            String title = rs.getString("title");
            String description = rs.getString("description");

            System.out.println("Quiz Details:");
                System.out.println("--------------------------------------------------------");
                System.out.println("ID: " + id);
                System.out.println("Creator ID: " + creator_id);
                System.out.println("Title: " + title);
                System.out.println("Description: " + description);
        }

    } catch (SQLException e) {
        System.out.println("Error retrieving quizzes from database: " + e.getMessage());
    }


    };
    // list the quiz by input id 
    private void listQuizById() throws SQLException{
        int quizId = inputID();
        String query = "SELECT * from quizzes where quiz_id = ?" ;

        try(Connection conn = DatabaseConnection.connect();
        PreparedStatement stmt = conn.prepareStatement(query)){
        stmt.setInt(1,quizId);
        try (ResultSet rs = stmt.executeQuery()){
            if (rs.next()){
                int id = rs.getInt("quiz_id");
            int creator_id = rs.getInt("creator_id");
            String title = rs.getString("title");
            String description = rs.getString("description");

            System.out.println("Quiz Details:");
                System.out.println("--------------------------------------------------------");
                System.out.println("ID: " + id);
                System.out.println("Creator ID: " + creator_id);
                System.out.println("Title: " + title);
                System.out.println("Description: " + description);
            }else{
                System.out.println("The quiz with ID: "+quizId+" does not exist");
            }
        }
        }
    };

    //helper function for update the user information
    private boolean updateQuizFields(int quizId,String field,String newValue) throws SQLException{
        String query = "UPDATE quizzes SET  "+ field + " = ? WHERE quiz_id = ?";
        try(Connection conn = DatabaseConnection.connect();
        PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, newValue);
            stmt.setInt(2,quizId);
            return stmt.executeUpdate() > 0;
        }
    }
    //edit quiz
    private void editQuiz() throws SQLException {
        Scanner scanner = new Scanner(System.in);
    
        // Loop until a valid quiz ID is entered
        int quizId;
        while (true) {
            System.out.println("Please input quiz ID you want to edit information:");
            quizId = inputID();
    
            if (!quizExists(quizId)) {
                System.out.println("Error: No quiz with this ID found. Please try again.");
            } else {
                break; // Exit the loop if the quiz ID is valid
            }
        }
    
        // Loop to handle editing options until valid choice is made or exit is selected
        while (true) {
            System.out.println("\nWhat do you want to edit?");
            System.out.println("1. Title");
            System.out.println("2. Creator");
            System.out.println("3. Description");
            System.out.println("4. Exit");
            System.out.print("Choose an option (1-4): ");
    
            int choice;
            // Ensure the user inputs a valid choice (1-4)
            while (true) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine(); // consume the newline
    
                    if (choice >= 1 && choice <= 4) {
                        break; // Exit the loop if the choice is valid
                    } else {
                        System.out.println("Error: Invalid choice. Please enter a number between 1 and 4.");
                    }
                } else {
                    System.out.println("Error: Please enter a valid number.");
                    scanner.nextLine(); // consume the invalid input
                }
            }
    
            switch (choice) {
                case 1:
                    System.out.println("Input a new Title: ");
                    String newTitle = scanner.nextLine();
                    updateUserFields(quizId, "title", newTitle);
                    System.out.println("Title updated successfully.");
                    break;
    
                case 2:
                    System.out.println("Input a new CreatorID: ");
                    String newCreator = scanner.nextLine();
                    updateUserFields(quizId, "creator_id", newCreator);
                    System.out.println("Creator updated successfully.");
                    break;
    
                case 3:
                    System.out.println("Input a new Description: ");
                    String newDescription = scanner.nextLine();
                    updateUserFields(quizId, "description", newDescription);
                    System.out.println("Description updated successfully.");
                    break;
    
                case 4:
                    System.out.println("Exiting the program...");
                    return;
    
                default:
                    System.out.println("Error: Invalid choice.");
            }
        }
    }
    
    //function to remove any quiz by input it quizId
    private void removeQuiz() throws SQLException{
        int quizId = inputID();

        if(!quizExists(quizId)){
            System.out.println("Error: No quiz found with ID " + quizId);
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you sure you want to remove this quiz? (yes/no)");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if(!confirmation.equals("yes")){
            System.out.println("Quiz removal cancled");
            return;
        }
        String query = "DELETE from quizzes where quiz_id = ?";
        try (Connection conn = DatabaseConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, quizId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Quiz with ID " + quizId + " has been removed successfully.");
            } else {
                System.out.println("Error: Unable to remove quiz.");
            }
        } catch (SQLException e) {
            System.out.println("Error removing quiz: " + e.getMessage());
        }
    };

    public static void main(String[] args) throws SQLException {
        // Simulate user input for testing
        Admin admin = new Admin("admin123", "John", "Doe", "johndoe@example.com", "password123");

        // Call addAdmin to test the function
        // admin.addAdmin();
        // admin.viewAllUsers();
        // admin.viewUserById();
        // admin.editUser();
        // admin.removeUser();
        // admin.manageUser();
        // admin.listAllQuiz();
        // admin.listQuizById();
        // admin.editQuiz();
        // admin.removeQuiz();
        admin.adminMenu();
    }
}

