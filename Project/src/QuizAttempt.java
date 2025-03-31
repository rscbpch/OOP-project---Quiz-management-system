import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class QuizAttempt {
    private int attemptId;
    private int studentId;
    private int quizId;
    private Timestamp startTime;
    private Timestamp endTime;
    private int score;
    private String status;

    // Constructor
    public QuizAttempt(int studentId, int quizId, Timestamp startTime, Timestamp endTime, int score, String status) {
        this.studentId = studentId;
        this.quizId = quizId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.score = score;
        this.status = status;
    }

    // Method to insert a quiz attempt into the database
    public boolean insertAttemptToDatabase() {
        String query = "INSERT INTO quiz_attempts (student_id, quiz_id, start_time, end_time, score, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, studentId);
            stmt.setInt(2, quizId);
            stmt.setTimestamp(3, startTime);
            stmt.setTimestamp(4, endTime);
            stmt.setInt(5, score);
            stmt.setString(6, status);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println("Error inserting quiz attempt: " + e.getMessage());
            return false;
        }
    }

    public static void viewQuizAttempts(int studentId) {
        String query = "SELECT q.title, qa.start_time, qa.end_time, qa.score, qa.status " +
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
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");
                int score = rs.getInt("score");
                String status = rs.getString("status");

                System.out.println("\nQuiz: " + title);
                System.out.println("Start Time: " + startTime);
                System.out.println("End Time: " + endTime);
                System.out.println("Score: " + score);
                System.out.println("Status: " + status);
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
    public Timestamp getStartTime() { return startTime; }
    public Timestamp getEndTime() { return endTime; }
    public int getScore() { return score; }
    public String getStatus() { return status; }

    public void setScore(int score) { this.score = score; }
    public void setStatus(String status) { this.status = status; }
}
