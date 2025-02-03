package class_structures;

public class Student extends User {
    public Student(int id, String name, String email, String password) {
        super(id, name, email, password); // use 'super' keyword to call the consturct in User class
    }
}
