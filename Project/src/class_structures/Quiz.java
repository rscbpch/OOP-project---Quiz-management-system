package class_structures;

public class Quiz {
    private int id;
    private String title;
    private int creator; // admin id

    public Quiz(int id, String title, int creator) {
        this.id = id;
        this.title = title;
        this.creator = creator;
    }
}