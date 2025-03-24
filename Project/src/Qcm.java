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

    // NONE OF THESE ARE USED
    //getter methods
    public int getQuizId () {return quizId;}
    public String getQuestion () {return question;}
    public String[] getOptions () {return options;}
    public int getCorrectAnswer () {return correctAnswer;}

    public void showQuestion () {
        System.out.println(getQuestion());
        String[] abc = getOptions();
        for (int counter = 0; counter < abc.length; counter++){
            System.out.println(counter+1 + ". " + abc[counter]);
        }
        int ansIndex = getCorrectAnswer();
        ansIndex++;
        System.out.println("Correct anwser: [" + ansIndex + "]");
    }

    public static void main(String[] args) {
        String[] ttest = {"dog", "cat", "horse", "hamter"}; 
        Qcm test = new Qcm(-1, -1, "which is the most popular pet ?", ttest, 1);
        test.showQuestion();
    }

}