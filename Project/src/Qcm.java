public class Qcm {
    public int id;
    public int quizId;
    public String question;
    private String[] options; // 4 options
    private int correctAnswer;// change maybe to index of options

    //constructor
    public Qcm(int id, int quizId, String question, String[] options, int correctAnswer) {
        this.id = id;
        this.quizId = quizId;
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    //getter methods
    public int getQuizId () { return quizId;}
    public String getQuestion () {return question;}
    public String[] getOptions () {return options;}
    public int getCorrectAnswer () {return correctAnswer;}

}