package class_structures;

public class Qcm {
    public int id;
    public int quizId;
    public String question;
    private String[] options; // 4 options
    private String correctAnswer;// change maybe to index of options

    public Qcm(int id, int quizId, String question, String[] options, String correctAnswer) {
        this.id = id;
        this.quizId = quizId;
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public static void main(String[] args) {
        Qcm q1 = new Qcm(1, 1, "what is life", new String[]{"a.joe mama","b.jeff"}, "a");
        System.out.println("this is one qs: ");
        System.out.println(q1.id);
        System.out.println(q1.quizId);
        System.out.println(q1.question);
        System.out.println(q1.options[0] + " " + q1.options[1]);
        System.out.println(q1.correctAnswer);
    }
}