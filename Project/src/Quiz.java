import java.util.*;

public class Quiz {
    private int id;
    private String title;
    private int creator; 
    private ArrayList<Qcm> questions; //array to store the questions from qcm
    //used arraylist cuz its dynamic(?) idk bruh

    public Quiz(int id, String title, int creator) {
        this.id = id;
        this.title = title;
        this.creator = creator;
        this.questions = new ArrayList<>();
    }

    public int getId() {return id;}
    public String getTitle() {return title;}
    public int getCreator() {return creator;}
    public void setTitle(String title) {this.title = title;}
    public void setCreator(int creator) {this.creator = creator;}

    //method to add said questions from qcm to the arraylist
    public void addQuestion(Qcm question) {
        questions.add(question);
    }
}