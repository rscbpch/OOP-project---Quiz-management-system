package class_structures;

public abstract class Qcm {
    protected int id;
    protected int quizId;
    protected String question;
    private String[] options; 
    private String correctAnswer;

    public Qcm(int id, int quizId, String question, String[] options, String correctAnswer) {
        this.id = id;
        this.quizId = quizId;
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
}