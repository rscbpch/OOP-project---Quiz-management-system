import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n== Welcome to the Quiz Management System! ==");

        Scanner sc = new Scanner(System.in);
        System.out.println("Select your role \n1. Student \n2. Teacher \n3.Admin");
        int role = opChecker(0, 3);
        System.out.println();
        if(role == 1) {     // student
            System.out.println("== Quiz Management System ==");
            System.out.println("Welcome, Student! Please select an option");
            System.out.println("1. Login \n2. Register/Sign up");
            int sOption = opChecker(0, 2);
            System.out.println();
            if(sOption ==1) {
                
            } else if(sOption == 2) {
                User student = userRegister("Student");
                System.out.println("\nStudent registered successfully!");
                student.displayInfo();
            }
        } else if(role == 2) {      // teacher
            System.out.println("== Quiz Management System ==");
            System.out.println("Welcome, Teacher! Please select an option");
            System.out.println("1. Login \n2. Register/Sign up");
            int tOption = opChecker(0, 2);
            System.out.println();
            if(tOption ==1) {

            } else if(tOption == 2) {
                User student = userRegister("Teacher");
                System.out.println("\nStudent registered successfully!");
                student.displayInfo();
            }
        } else if(role == 3){       // admin
            System.out.println("Admin");



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
        if (role.equals("Student")) {
            return new Student(username, firstName, lastName, email, password, role);
        } else {
            return new Teacher(username, firstName, lastName, email, password, role);
        }
    }
}