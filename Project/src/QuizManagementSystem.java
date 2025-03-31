import java.sql.SQLException;
import java.util.Scanner;

public class QuizManagementSystem {
    public static void main(String[] args) throws SQLException {
        while (true) {
            System.out.println("\n== Welcome to the Quiz Management System! ==");
            Scanner sc = new Scanner(System.in);
            System.out.println("Select your role \n1. Student \n2. Teacher \n3. Admin \n4. Exit");
            int role = opChecker(0, 4);
            System.out.println();
    
            QuizManager quizManager = new QuizManager();
    
            if (role == 1) {  // Student
                while (true) {
                    System.out.println("== Quiz Management System ==");
                    System.out.println("Welcome, Student! \nPlease select an option");
                    System.out.println("1. Login \n2. Register/Sign up \n3. Go back");
                    int sOption = opChecker(1, 3);
                    System.out.println();
    
                    if (sOption == 1) {
                        String email = UserAuthentication.userLogin("student");
                        if (email != null) {
                            while (true) {  // Stay in the student menu until they exit
                                System.out.println("Please select an option");
                                System.out.println("1. Take a quiz \n2. View quiz history \n3. Update profile \n4. Logout");
                                int ssOption = opChecker(1, 4);
                                System.out.println();
    
                                if (ssOption == 1) {
                                    int studentId = UserAuthentication.getUserIdByEmail(email);
                                    quizManager.playQuiz(studentId);
                                } else if (ssOption == 2) {
                                    int studentId = UserAuthentication.getUserIdByEmail(email);
                                    QuizAttempt.viewQuizAttempts(studentId);
                                } else if (ssOption == 3) {
                                    updateUser();
                                } else {
                                    break; 
                                }
                            }
                        }
                    } else if (sOption == 2) {
                        User student = UserAuthentication.userRegister("Student");
                        System.out.println("\nStudent registered successfully!");
                    } else {
                        break;  
                    }
                }
            } else if (role == 2) {  // Teacher
                while (true) {
                    System.out.println("== Quiz Management System ==");
                    System.out.println("Welcome, Teacher! Please select an option");
                    System.out.println("1. Login \n2. Register/Sign up \n3. Go back");
                    int tOption = opChecker(1, 3);
                    System.out.println();
    
                    if (tOption == 1) {
                        String email = UserAuthentication.userLogin("teacher");
                        if (email != null) {
                            while (true) {  // Stay in teacher menu until they exit
                                System.out.println("Please select an option");
                                System.out.println("1. Create a quiz \n2. Show all created quizzes \n3. Update profile \n4. Logout");
                                int ttOption = opChecker(1, 4);
                                System.out.println();
    
                                if (ttOption == 1) {
                                    quizManager.createQuiz();
                                } else if (ttOption == 2) {
                                    quizManager.displayQQ();
                                } else if (ttOption == 3) {
                                    updateUser();
                                } else {
                                    break;  
                                }
                            }
                        }
                    } else if (tOption == 2) {
                        User teacher = UserAuthentication.userRegister("Teacher");
                        System.out.println("\nTeacher registered successfully!");
                    } else {
                        break;  
                    }
                }
            } else if (role == 3) {  // Admin
                String email = UserAuthentication.userLogin("admin");
                if (email != null) {
                    Admin admin = new Admin("superadmin", "super", "admin", "superadmin@gmail.com", "admin123");
                    admin.adminMenu();
                }
            } else {
                break;  
            }
        }
    }
    

    // Function to check for available option
    public static int opChecker(int input, int option) {
        Scanner sc = new Scanner(System.in);
        while (input <= 0 || input > option) {
            System.out.print("Enter your choice: ");
            if (sc.hasNextInt()) {
                input = sc.nextInt();
                if (input <= 0 || input > option) {
                    System.out.println("Please enter a valid choice!\n");
                }
            } else {
                System.out.println("Invalid input! Please enter a number!\n");
                sc.next();
            }
        }
        return input;
    }

    public static void updateUser() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your current email: ");
        String currentEmail = sc.nextLine();

        System.out.print("New username (leave empty to keep old): ");
        String newUsername = sc.nextLine();

        System.out.print("New first name (leave empty to keep old): ");
        String newFirstName = sc.nextLine();

        System.out.print("New last name (leave empty to keep old): ");
        String newLastName = sc.nextLine();

        System.out.print("New email (leave empty to keep old): ");
        String newEmail = sc.nextLine();

        System.out.print("New password (leave empty to keep old): ");
        String newPassword = sc.nextLine();

        User.updateUserInDatabase(currentEmail, newUsername, newFirstName, newLastName, newEmail, newPassword);
        sc.close();
    }
}
