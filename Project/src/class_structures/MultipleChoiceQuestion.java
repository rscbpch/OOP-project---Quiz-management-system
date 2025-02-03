package class_structures;

public class MultipleChoiceQuestion extends Question {
    private String[] options;
    private String correctAnswer;

    public MultipleChoiceQuestion(int id, int quizId, String questionText, String[] options, String correctAnswer) {
        super(id, quizId, questionText);
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
}