public class Student extends User {
    public Student(String username, String firstName, String lastName, String email, String password, String role) {
        super(username, firstName, lastName, email, password, "Student");
    }
}
