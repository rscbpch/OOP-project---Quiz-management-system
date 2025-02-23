public class Admin extends User {
    public Admin(String username, String firstName, String lastName, String email, String password, String role) {
        super(username, firstName, lastName, email, password, "Admin");
    }
    
    //addUser function (admin)
    public void addAdmin(String username, String firstName, String lastName, String email, String password, String role){
        //will be complete later
    };

    //function for get all existen user in system including admin,teacher,student 
    public void viewAllUser(){
        //will be complete later
    };

    //a function that allow only admin to delete any user from the system
    public void removeUser(int id){
        //
    };

    //function that list all the existing quiz
    public void viewAllQuizz(){};

    //function to remove any quiz by input it quizId
    public void removeQuiz(int quizId){};






}
