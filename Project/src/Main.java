import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("\nWelcome to the Quiz Management System!\n");

        Scanner myObj = new Scanner(System.in);
        System.out.println("Select your role \n1. Student \n2. Teacher \n3.Admin");
        int role = opChecker(0, 3, myObj);
        if(role == 1) {
            System.out.println("im 1");
        } else if(role == 2) {
            System.out.println("im 2");
        } else if(role == 3){
            System.out.println("im 3");
        }
    }

    public static int opChecker(int input, int option, Scanner myObj) {
        while (input <= 0 || input > option) {
            System.out.print("Enter your choice: ");
            if (myObj.hasNextInt()) {
                input = myObj.nextInt();
                if (input <= 0 || input > option) {
                    System.out.println("Please enter a valid choice!\n");
                }
            } else {
                System.out.println("Invalid input! Please enter a number!\n");
                myObj.next();  
            }
        }
        return input;
    }
}