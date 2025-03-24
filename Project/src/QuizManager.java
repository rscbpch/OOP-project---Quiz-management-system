import java.sql.*;
import java.util.*;

public class QuizManager {
    private HashMap<String, Quiz> quizStorage;

    // initailize the map
    public QuizManager () {
        this.quizStorage = new HashMap<>();
    }

    public void createQuiz () {
        Scanner scanner = new Scanner(System.in);
        
        

        try {
            Connection con = DatabaseConnection.connect();
            System.out.println("Connection Successful!");

            System.out.println("Quiz title: ");
            String quizTitle = scanner.nextLine();
            System.out.println("Creator ID: ");
            int cid = scanner.nextInt();
            scanner.nextLine();
            int qzId=0;
            System.out.println("Quiz desctiption: ");
            String desc = scanner.nextLine();

            String insertQuiz = "insert into quizzes (title, description) values (?, ?)";
            PreparedStatement psQuiz = con.prepareStatement(insertQuiz, Statement.RETURN_GENERATED_KEYS);
            psQuiz.setString(1, quizTitle);
            // psQuiz.setInt(2, cid);
            psQuiz.setString(2, desc);

            psQuiz.executeUpdate();
            ResultSet rsQuiz = psQuiz.getGeneratedKeys();
            if (rsQuiz.next()) {
                qzId = rsQuiz.getInt(1);
            }

            char addMore;
            do {
                System.out.println("Enter the question: ");
                String qText = scanner.nextLine();
                System.out.println("Enter the 4 options: ");
                String qOption1 = scanner.nextLine();
                String qOption2 = scanner.nextLine();
                String qOption3 = scanner.nextLine();
                String qOption4 = scanner.nextLine();
                System.out.println("Enter the correct answer (1-4): ");
                int qAnswer = scanner.nextInt();
                scanner.nextLine();
                while (qAnswer < 1 || qAnswer > 4) {
                    System.out.println("Enter the correct answer (1-4): ");
                    qAnswer = scanner.nextInt();
                    scanner.nextLine();
                }
    
                System.out.println("Do you want to add more questions? (y/n)");
                addMore = scanner.next().charAt(0);
                scanner.nextLine();
                while (addMore != 'y' && addMore != 'Y' && addMore != 'n' && addMore != 'N') {
                    System.out.println("(y/n)");
                    addMore = scanner.next().charAt(0);
                    scanner.nextLine();
                }

                String insertQcm = "insert into qcms (question, option_1, option_2, option_3, option_4, correct_option, quiz_id) values (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement psQcm = con.prepareStatement(insertQcm);
                psQcm.setString(1, qText);
                psQcm.setString(2, qOption1);
                psQcm.setString(3, qOption2);
                psQcm.setString(4, qOption3);
                psQcm.setString(5, qOption4);
                psQcm.setInt(6, qAnswer);
                psQcm.setInt(7, qzId);
                psQcm.executeUpdate();
                
            } while (addMore != 'n' && addMore != 'N');

            con.close();
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }

    public void getQuiz () {

        try {
            Connection con = DatabaseConnection.connect();
            System.out.println("Connection Successful!");
            String query = "SELECT * FROM quiz";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Fetch and display results
                int quizId = rs.getInt("quizId");
                String quizTitle = rs.getString("quizTitle");
                int creatorId = rs.getInt("quizCreatorId");
                System.out.println("Quiz ID: " + quizId + ", Title: " + quizTitle + ", Creator ID: " + creatorId);
                System.out.println();
            }
            con.close();
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }

    public void getQuestions (int qid) {
         

        try {
            Connection con = DatabaseConnection.connect();
            String query2 = "SELECT * FROM quiz where quizId = ?";
            PreparedStatement ps2 = con.prepareStatement(query2);
            ps2.setInt(1, qid); // 'title' is the user input you're querying by
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                // Fetch and display results
                int quizId = rs2.getInt("quizId");
                String quizTitle = rs2.getString("quizTitle");
                int creatorId = rs2.getInt("quizCreatorId");
                System.out.println("Quiz ID: " + quizId + ", Title: " + quizTitle + ", Creator ID: " + creatorId);
                System.out.println();
            }

            String selectQuery = "select * from qcm where quizId = ?";
            PreparedStatement psSelect = con.prepareStatement(selectQuery);
            psSelect.setInt(1, qid);
            ResultSet rs = psSelect.executeQuery();
            while (rs.next()) {
                int questionId = rs.getInt("qcmId"); // Assuming 'qcmId' is the ID column for the question
                String question = rs.getString("qcmQuestion"); // Get the question text
                String option1 = rs.getString("qcm1"); // Option 1
                String option2 = rs.getString("qcm2"); // Option 2
                String option3 = rs.getString("qcm3"); // Option 3
                String option4 = rs.getString("qcm4"); // Option 4
                int correctAnswer = rs.getInt("qcmAnswer"); // Get the correct answer (index 0-3)

                // Display the question and options
                System.out.println("Question: " + question);
                System.out.println("1. " + option1);
                System.out.println("2. " + option2);
                System.out.println("3. " + option3);
                System.out.println("4. " + option4);
                System.out.println("Correct Answer: " + (correctAnswer + 1)); // Display correct answer (1-based index)
                System.out.println();
                }
            con.close();
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }

    public void displayQQ () {
        int qid = 0;
        Scanner scanner = new Scanner(System.in);
        getQuiz();
        System.out.println("Enter the quizId you want to view:");
        qid = scanner.nextInt();
        scanner.nextLine();

        try {
            Connection con = DatabaseConnection.connect();
            String query2 = "SELECT * FROM quiz where quizId = ?";
            PreparedStatement ps2 = con.prepareStatement(query2);
            ps2.setInt(1, qid); // 'title' is the user input you're querying by
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                // Fetch and display results
                int quizId = rs2.getInt("quizId");
                String quizTitle = rs2.getString("quizTitle");
                int creatorId = rs2.getInt("quizCreatorId");
                System.out.println("Quiz ID: " + quizId + ", Title: " + quizTitle + ", Creator ID: " + creatorId);
            }

            
            con.close();
        } catch (SQLException err) {
            err.printStackTrace();
        }

    }

    //play time
    public void playQuiz() {
        Scanner scanner = new Scanner(System.in);
        getQuiz();
        System.out.println("Enter the quiz you want to play: ");
        int playIndex = scanner.nextInt();
        scanner.nextLine();
        int correctStorage = 0;

        try {
            Connection con = DatabaseConnection.connect();
            String selectQuery = "select * from qcm where quizId = ?";
            PreparedStatement psSelect = con.prepareStatement(selectQuery);
            psSelect.setInt(1, playIndex);
            ResultSet rs = psSelect.executeQuery();
            while (rs.next()) {
                int questionId = rs.getInt("qcmId"); // Assuming 'qcmId' is the ID column for the question
                String question = rs.getString("qcmQuestion"); // Get the question text
                String option1 = rs.getString("qcm1"); // Option 1
                String option2 = rs.getString("qcm2"); // Option 2
                String option3 = rs.getString("qcm3"); // Option 3
                String option4 = rs.getString("qcm4"); // Option 4
                int correctAnswer = rs.getInt("qcmAnswer"); // Get the correct answer (index 0-3)

                // Display the question and options
                System.out.println("Question: " + question);
                System.out.println("1. " + option1);
                System.out.println("2. " + option2);
                System.out.println("3. " + option3);
                System.out.println("4. " + option4);
                System.out.println("Enter you option (1-4):");                    
                int userAnswer = scanner.nextInt();
                scanner.nextLine();
                if ( userAnswer == correctAnswer){
                    System.out.println("correct");
                    correctStorage++;
                } else {
                    System.out.println("Not correct");
                }
                System.out.println();
            }
            con.close();
        } catch (SQLException err) {
            err.printStackTrace();
        }
        

    }


    // TO BE IMPLEMENTED IN MAIN CHAT
    public static void main(String[] args) {
        QuizManager test = new QuizManager(); // Initialize the quiz system
        test.createQuiz();

        // Scanner scanner = new Scanner(System.in);

        // while (true) {
        //     System.out.println("\nWelcome to the Quiz System!");
        //     System.out.println("1. Create a new quiz");
        //     System.out.println("2. View an existing quiz");
        //     System.out.println("3. Exit");
        //     System.out.print("Choose an option: ");
        //     int choice = scanner.nextInt();
        //     scanner.nextLine(); // Consume newline

        //     switch (choice) {
        //         case 1:
        //             test.createQuiz();
        //             break;
        //         case 2:
        //             System.out.println("Enter quiz title: ");
        //             String testTitle = scanner.nextLine();
        //             test.getQuiz(testTitle);
        //             break;
        //         case 3:
        //             System.out.println("Goodbye monkey");
        //             return;
        //         default:
        //         System.out.println("No option.");
        //             break;
        //     }
        // }
    }

}
