package class_structures;


public class Admin extends User {
    public Admin(int id, String name, String email, String password) {
        super(id, name, email, password); // use 'super' keyword to call the consturct in User class
    }
}
