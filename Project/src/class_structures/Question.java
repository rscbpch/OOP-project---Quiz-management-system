package class_structures;

public abstract class Question {
    public int id;
    public int quizId;
    public String questionText;

    public Question(int id, int quizId, String questionText) {
        this.id = id;
        this.quizId = quizId;
        this.questionText = questionText;
    }
}