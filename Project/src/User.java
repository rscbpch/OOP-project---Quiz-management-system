// use abstract class becuase we dont need to create object from this class
// we only need this class as the base class for admin, teacher and student
public abstract class User { 
    protected int id;
    protected String first_name;
    protected String last_name;
    protected String email;
    private String password;

    public User() {
        this.id = 0;
        this.first_name = "Unknown";
        this.last_name = "Unknown";
        this.email = "unknown@example.com";
        this.password = "default";
    }

    public User(int id, String first_name, String last_name, String email, String password) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return new String(new char[password.length()]).replace("\0", "*"); //cant see the password (password: NIGKA -> *****)
    }
}
