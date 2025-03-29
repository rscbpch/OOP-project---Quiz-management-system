import java.sql.*;
import java.util.*;

public class QuizManager {
    private HashMap<String, Quiz> quizStorage;

    // Initialize the map
    public QuizManager() {
        this.quizStorage = new HashMap<>();
    }

    // Create Quiz (Modified to take email as creator)
    public void createQuiz(String teacherEmail) {
        Scanner scanner = new Scanner(System.in);

        try {
            Connection con = DatabaseConnection.connect();
            System.out.println("Connection Successful!");

            // Get Teacher ID
            String getTeacherIdQuery = "SELECT user_id FROM users WHERE email = ?";
            PreparedStatement psTeacher = con.prepareStatement(getTeacherIdQuery);
            psTeacher.setString(1, teacherEmail);
            ResultSet rsTeacher = psTeacher.executeQuery();
            int creatorId = -1;

            if (rsTeacher.next()) {
                creatorId = rsTeacher.getInt("user_id");
            } else {
                System.out.println("Teacher not found!");
                return;
            }

            System.out.println("Quiz title: ");
            String quizTitle = scanner.nextLine();
            System.out.println("Quiz description: ");
            String desc = scanner.nextLine();

            // Insert Quiz
            String insertQuiz = "INSERT INTO quizzes (title, description, creator_id) VALUES (?, ?, ?)";
            PreparedStatement psQuiz = con.prepareStatement(insertQuiz, Statement.RETURN_GENERATED_KEYS);
            psQuiz.setString(1, quizTitle);
            psQuiz.setString(2, desc);
            psQuiz.setInt(3, creatorId);
            psQuiz.executeUpdate();

            ResultSet rsQuiz = psQuiz.getGeneratedKeys();
            int quizId = 0;
            if (rsQuiz.next()) {
                quizId = rsQuiz.getInt(1);
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

                String insertQcm = "INSERT INTO qcms (question, option_1, option_2, option_3, option_4, correct_option, quiz_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement psQcm = con.prepareStatement(insertQcm);
                psQcm.setString(1, qText);
                psQcm.setString(2, qOption1);
                psQcm.setString(3, qOption2);
                psQcm.setString(4, qOption3);
                psQcm.setString(5, qOption4);
                psQcm.setInt(6, qAnswer);
                psQcm.setInt(7, quizId);
                psQcm.executeUpdate();

            } while (addMore == 'y' || addMore == 'Y');

            con.close();
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }

    // View Quizzes Created by Teacher
    public void viewCreatedQuizzes(String teacherEmail) {
        try {
            Connection con = DatabaseConnection.connect();
            String query = "SELECT quiz_id, title FROM quizzes WHERE creator_id = (SELECT user_id FROM users WHERE email = ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, teacherEmail);
            ResultSet rs = ps.executeQuery();

            System.out.println("Your Created Quizzes:");
            while (rs.next()) {
                int quizId = rs.getInt("quiz_id");
                String quizTitle = rs.getString("title");
                System.out.println("Quiz ID: " + quizId + " | Title: " + quizTitle);
            }
            con.close();
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }

    // Student Takes Quiz
    public void takeQuiz(String studentEmail) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the quiz ID you want to take: ");
        int playIndex = scanner.nextInt();
        scanner.nextLine();
        int correctAnswers = 0;

        try {
            Connection con = DatabaseConnection.connect();
            String selectQuery = "SELECT * FROM qcms WHERE quiz_id = ?";
            PreparedStatement psSelect = con.prepareStatement(selectQuery);
            psSelect.setInt(1, playIndex);
            ResultSet rs = psSelect.executeQuery();

            while (rs.next()) {
                String question = rs.getString("question");
                String option1 = rs.getString("option_1");
                String option2 = rs.getString("option_2");
                String option3 = rs.getString("option_3");
                String option4 = rs.getString("option_4");
                int correctAnswer = rs.getInt("correct_option");

                System.out.println("Question: " + question);
                System.out.println("1. " + option1);
                System.out.println("2. " + option2);
                System.out.println("3. " + option3);
                System.out.println("4. " + option4);
                System.out.print("Enter your answer (1-4): ");
                int userAnswer = scanner.nextInt();
                scanner.nextLine();

                if (userAnswer == correctAnswer) {
                    correctAnswers++;
                    System.out.println("Correct!");
                } else {
                    System.out.println("Wrong answer.");
                }
                System.out.println();
            }

            // Store quiz history
            String insertHistory = "INSERT INTO quiz_history (user_email, quiz_id, score) VALUES (?, ?, ?)";
            PreparedStatement psHistory = con.prepareStatement(insertHistory);
            psHistory.setString(1, studentEmail);
            psHistory.setInt(2, playIndex);
            psHistory.setInt(3, correctAnswers);
            psHistory.executeUpdate();

            System.out.println("Quiz completed! Your score: " + correctAnswers);
            con.close();
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }

    // View Student's Quiz History
    public void viewQuizHistory(String studentEmail) {
        try {
            Connection con = DatabaseConnection.connect();
            String query = "SELECT quizzes.title, quiz_history.score FROM quiz_history JOIN quizzes ON quiz_history.quiz_id = quizzes.quiz_id WHERE quiz_history.user_email = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, studentEmail);
            ResultSet rs = ps.executeQuery();

            System.out.println("Your Quiz History:");
            while (rs.next()) {
                String quizTitle = rs.getString("title");
                int score = rs.getInt("score");
                System.out.println("Quiz: " + quizTitle + " | Score: " + score);
            }
            con.close();
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }
}
