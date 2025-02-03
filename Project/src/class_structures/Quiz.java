package class_structures;

public class Quiz {
    private int id;
    private String title;
    private int createdBy; // admin id

    public Quiz(int id, String title, int createdBy) {
        this.id = id;
        this.title = title;
        this.createdBy = createdBy;
    }
}