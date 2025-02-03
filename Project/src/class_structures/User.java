package class_structures;

// use abstract class becuase we dont need to create object from this class
// we only need this class as the base class for admin and student
public abstract class User { 
    public int id;
    public String name;
    public String email;
    private String password;

    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
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
