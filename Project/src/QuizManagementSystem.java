import java.util.Scanner;

public class QuizManagementSystem {
    public static void main(String[] args) {
        System.out.println("\n== Welcome to the Quiz Management System! ==");

        Scanner sc = new Scanner(System.in);
        System.out.println("Select your role \n1. Student \n2. Teacher");
        int role = opChecker(0, 2);
        System.out.println();

        if (role == 1) {  // Student
            System.out.println("== Quiz Management System ==");
            System.out.println("Welcome, Student! \nPlease select an option");
            System.out.println("1. Login \n2. Register/Sign up");
            int sOption = opChecker(0, 2);
            System.out.println();

            if (sOption == 1) {
                User loggedInUser = UserAuthentication.userLogin();
                if (loggedInUser instanceof Student) {
                    System.out.println("Please select an option");
                    System.out.println("1. Take a quiz \n2. View quiz history \n3. Exit");
                    int ssOption = opChecker(0, 3);
                    System.out.println();

                    if (ssOption == 1) {
                        System.out.println("op1 student");
                    } else if (ssOption == 2) {
                        System.out.println("op2 student");
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
                User loggedInUser = UserAuthentication.userLogin();
                if (loggedInUser instanceof Teacher) {
                    System.out.println("Please select an option");
                    System.out.println("1. Create a quiz \n2. Show all created quiz \n3. Exit");
                    int ttOption = opChecker(0, 3);
                    System.out.println();

                    if (ttOption == 1) {
                        System.out.println("op1 teacher");
                    } else if (ttOption == 2) {
                        System.out.println("op2 teacher");
                    }
                }
            } else if (tOption == 2) {
                User teacher = UserAuthentication.userRegister("Teacher");
                System.out.println("\nTeacher registered successfully!");
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
}
