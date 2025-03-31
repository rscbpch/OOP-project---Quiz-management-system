import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuizAttempt {
    private int attemptId;
    private int studentId;
    private int quizId;
    private int score;

    // Constructor
    public QuizAttempt(int studentId, int quizId, int score) {
        this.studentId = studentId;
        this.quizId = quizId;
        this.score = score;
    }

    // Method to insert a quiz attempt into the database
    public boolean insertAttemptToDatabase() {
        String query = "INSERT INTO quiz_attempts (student_id, quiz_id, score) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, studentId);
            stmt.setInt(2, quizId);
            stmt.setInt(3, score);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println("Error inserting quiz attempt: " + e.getMessage());
            return false;
        }
    }

    public static void viewQuizAttempts(int studentId) {
        String query = "SELECT q.title, qa.score " +  // Fixed space issue
                       "FROM quiz_attempts qa " +
                       "JOIN quizzes q ON qa.quiz_id = q.quiz_id " +
                       "WHERE qa.student_id = ?";
    
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
    
            System.out.println("\n== Your Quiz History ==");
            boolean hasAttempts = false;
    
            while (rs.next()) {
                hasAttempts = true;
                String title = rs.getString("title");
                int score = rs.getInt("score");
    
                System.out.println("\nQuiz: " + title);
                System.out.println("Score: " + score);
                System.out.println("-------------------------------");
            }
    
            if (!hasAttempts) {
                System.out.println("No quiz attempts found.");
            }
    
        } catch (SQLException e) {
            System.out.println("Error retrieving quiz attempts: " + e.getMessage());
        }
    }
    

    // Getters and Setters
    public int getAttemptId() { return attemptId; }
    public int getStudentId() { return studentId; }
    public int getQuizId() { return quizId; }
    public int getScore() { return score; }

    public void setScore(int score) { this.score = score; }
}
