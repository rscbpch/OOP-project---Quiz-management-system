public class Teacher extends User {
    public Teacher(int id, String username, String firstName, String lastName, String email, String password, String role) {
        super(id, username, firstName, lastName, email, password, "Teacher");
    }
}
