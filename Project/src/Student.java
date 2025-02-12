public class Student extends User {
    public Student(int id, String username, String first_name, String last_name, String email, String password, String role) {
        super(id, username, first_name, last_name, email, password, "Student");
    }
}
