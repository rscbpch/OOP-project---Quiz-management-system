public abstract class User { 
    int id;
    String username;
    String firstName;
    String lastName;
    String email;
    private String password;
    String role;

    public User(String username, String firstName, String lastName, String email, String password, String role) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return new String(new char[password.length()]).replace("\0", "*");
    }

    public void displayInfo() {
        System.out.println("Username: " + username);
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Email: " + email);
        System.out.println("Role: " + role);
    }
} 
