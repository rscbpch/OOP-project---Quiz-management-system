import java.util.List;

public class Score {
    private int id;
    private int studentId;
    private int quizId;
    private int score;

    public Score(int id, int studentId, int quizId, int score) {
        this.id = id;
        this.studentId = studentId;
        this.quizId = quizId;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getQuizId() {
        return quizId;
    }
    
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void calculateScore(List<Qcm> questions, List<String> studentAnswers) {
        int correctCount = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (i < studentAnswers.size() && studentAnswers.get(i).equals(questions.get(i).getCorrectAnswer())) {
                correctCount++;
            }
        }
        this.score = correctCount; 
    }
}