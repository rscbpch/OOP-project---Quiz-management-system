public class Quiz {
    private int id;
    private String title;
    private int creator; 

    public Quiz(int id, String title, int creator) {
        this.id = id;
        this.title = title;
        this.creator = creator;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getCreator() {
        return creator;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }
}