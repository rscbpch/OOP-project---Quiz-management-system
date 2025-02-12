public class Admin extends User {
    public Admin(int id, String username, String firstName, String lastName, String email, String password, String role) {
        super(id, username, firstName, lastName, email, password, "Admin");
    }
}
