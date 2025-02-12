// use abstract class becuase we dont need to create object from this class
// we only need this class as the base class for admin, teacher and student
public abstract class User { 
    protected int id;
    protected String username;
    protected String first_name;
    protected String last_name;
    protected String email;
    private String password;
    protected String role;

    public User(int id, String username, String first_name, String last_name, String email, String password, String role) {
        this.id = id;
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return new String(new char[password.length()]).replace("\0", "*"); //cant see the password (password: NIGKA -> *****)
    }
}
