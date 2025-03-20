public class Score {
    private int studentId;
    private String studentName;
    private int quizId;
    private int score;
    private int totalQuestions;

    public Score(int studentId, String studentName, int quizId, int totalQuestions) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.quizId = quizId;
        this.totalQuestions = totalQuestions;
        this.score = 0; // Default score is 0
    }

    public int getScore() { return score; }
    public int getTotalQuestions() { return totalQuestions; }

    // Method to update score based on correct answers
    public void setScore(int correctAnswers) {
        if (correctAnswers < 0) {
            this.score = 0; // Set to minimum valid score
        } else if (correctAnswers > totalQuestions) {
            this.score = totalQuestions; // Set to max valid score
        } else {
            this.score = correctAnswers;
        }
    }

    public double getPercentage() {
        return totalQuestions > 0 ? ((double) score / totalQuestions) * 100 : 0;
    }

    public String getGrade() {
        double percentage = getPercentage();
        if (percentage >= 90) return "A";
        else if (percentage >= 80) return "B";
        else if (percentage >= 70) return "C";
        else if (percentage >= 60) return "D";
        else return "F";
    }

    @Override
    public String toString() {
        return String.format("Student: %s (ID: %d) | Quiz ID: %d | Score: %d/%d | Percentage: %.2f%% | Grade: %s",
                studentName, studentId, quizId, score, totalQuestions, getPercentage(), getGrade());
    }

    public void displayScore() {
        System.out.println("\n=== Score Details ===");
        System.out.println(this); 
        System.out.println("=====================");
    }
}