import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<User> students = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("\n== Welcome to the Quiz Management System! ==");

        Scanner sc = new Scanner(System.in);
        System.out.println("Select your role \n1. Student \n2. Teacher \n3. Admin");
        int role = opChecker(0, 3);
        System.out.println();
        
        if (role == 1) {  // Student
            System.out.println("== Quiz Management System ==");
            System.out.println("Welcome, Student! \nPlease select an option");
            System.out.println("1. Login \n2. Register/Sign up");
            int sOption = opChecker(0, 2);
            System.out.println();
        
            if (sOption == 1) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter email: ");
                String email = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
        
                User loggedInUser = User.loginUser(email, password);
                if (loggedInUser != null) {
                    System.out.println("\n== Welcome, " + loggedInUser.firstName + " " + loggedInUser.lastName + " ==\n");
                }
            } else if (sOption == 2) {
                User student = User.userRegister("Student");
                students.add(student);
                System.out.println("\nStudent registered successfully!");
                System.out.println("\n== Welcome, " + student.firstName + " " + student.lastName + " ==\n");
            }
        } else if (role == 2) {  // Teacher
            System.out.println("== Quiz Management System ==");
            System.out.println("Welcome, Teacher! Please select an option");
            System.out.println("1. Login \n2. Register/Sign up");
            int tOption = opChecker(0, 2);
            System.out.println();
        
            if (tOption == 1) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter email: ");
                String email = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
        
                User loggedInUser = User.loginUser(email, password);
                if (loggedInUser != null) {
                    System.out.println("\n== Welcome, " + loggedInUser.firstName + " " + loggedInUser.lastName + " ==\n");
                }
            } else if (tOption == 2) {
                User teacher = User.userRegister("Teacher");
                System.out.println("\nTeacher registered successfully!");
                System.out.println("\n== Welcome, " + teacher.firstName + " " + teacher.lastName + " ==\n");
            }
        } else if(role == 3){  // Admin
            System.out.println("== Admin Panel ==");
            System.out.println("1. View All Students");
            int adminChoice = opChecker(0, 1);
            if(adminChoice == 1) {
                printAllStudents();
            }
        }
    }

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

    // Function to print all student data
    public static void printAllStudents() {
        System.out.println("\n== List of Registered Students ==");
        if (students.isEmpty()) {
            System.out.println("No students registered yet.");
        } else {
            for (User student : students) {
                student.displayInfo();
                System.out.println("----------------------------");
            }
        }
    }
}
