public class Admin extends User {
    public Admin(String username, String firstName, String lastName, String email, String password, String role) {
        super(username, firstName, lastName, email, password, "Admin");
    }
}
