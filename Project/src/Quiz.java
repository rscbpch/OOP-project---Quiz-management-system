import java.util.*;

public class Quiz {
    private int id;
    private String title;
    private int creator; 
    private ArrayList<Qcm> questions; //array to store the questions from qcm
    //used arraylist cuz its dynamic(?) idk bruh

    // contstuctor
    public Quiz(int id, String title, int creator) {
        this.id = id;
        this.title = title;
        this.creator = creator;
        this.questions = new ArrayList<>();
    }

    //getter methods
    public int getId() {return id;}
    public String getTitle() {return title;}
    public int getCreator() {return creator;}
    public ArrayList<Qcm> getQuestions () {return questions;}

    //method to add said questions from qcm to the arraylist
    public void addQuestion(Qcm question) {
        questions.add(question);
    }
}