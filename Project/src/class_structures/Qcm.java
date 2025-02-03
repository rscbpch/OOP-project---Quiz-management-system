package class_structures;

public abstract class Qcm {
    public int id;
    public int quizId;
    public String question;
    private String[] options; // 4 options
    private String correctAnswer;

    public Qcm(int id, int quizId, String question, String[] options, String correctAnswer) {
        this.id = id;
        this.quizId = quizId;
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
}