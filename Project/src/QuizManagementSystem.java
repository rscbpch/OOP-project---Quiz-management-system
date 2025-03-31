import java.util.Scanner;

public class QuizManagementSystem {

    public static void main(String[] args) {
        System.out.println("\n== Welcome to the Quiz Management System! ==");

        Scanner sc = new Scanner(System.in);
        System.out.println("Select your role \n1. Student \n2. Teacher \n3. Admin");
        int role = opChecker(0, 3);
        System.out.println();

        // Instantiate QuizManager
        QuizManager quizManager = new QuizManager();

        if (role == 1) {  // Student
            System.out.println("== Quiz Management System ==");
            System.out.println("Welcome, Student! \nPlease select an option");
            System.out.println("1. Login \n2. Register/Sign up");
            int sOption = opChecker(0, 2);
            System.out.println();

            if (sOption == 1) {
                String email = UserAuthentication.userLogin();
                if (email != null) {
                    System.out.println("Please select an option");
                    System.out.println("1. Take a quiz \n2. View quiz history");
                    int ssOption = opChecker(0, 2);
                    System.out.println();

                    if (ssOption == 1) {
                        // Call the takeQuiz method from QuizManager
                        quizManager.playQuiz();;
                    } else if (ssOption == 2) {
                        // Call the viewQuizHistory method from QuizManager
                        System.out.println("Do smth");
                    }
                }
            } else if (sOption == 2) {
                User student = UserAuthentication.userRegister("Student");
                System.out.println("\nStudent registered successfully!");
            }
        } else if (role == 2) {  // Teacher
            System.out.println("== Quiz Management System ==");
            System.out.println("Welcome, Teacher! Please select an option");
            System.out.println("1. Login \n2. Register/Sign up");
            int tOption = opChecker(0, 2);
            System.out.println();

            if (tOption == 1) {
                String email = UserAuthentication.userLogin();
                if (email != null) {
                    System.out.println("Please select an option");
                    System.out.println("1. Create a quiz \n2. Show all created quiz");
                    int ttOption = opChecker(0, 2);
                    System.out.println();

                    if (ttOption == 1) {
                        // Call the createQuiz method from QuizManager
                        quizManager.createQuiz();
                    } else if (ttOption == 2) {
                        // Call the viewCreatedQuizzes method from QuizManager
                        quizManager.displayQQ();;
                    }
                }
            } else if (tOption == 2) {
                User teacher = UserAuthentication.userRegister("Teacher");
                System.out.println("\nTeacher registered successfully!");
            }
        } else {                // Admin
            String email = UserAuthentication.userLogin();
            if (email != null) {
                // implement admin menu here
                

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
