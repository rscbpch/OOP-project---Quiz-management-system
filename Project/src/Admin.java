public class Admin extends User {
    public Admin(String username, String firstName, String lastName, String email, String password, String role) {
        super(username, firstName, lastName, email, password, "Admin");
    }
    
    //
    public void manageUser(int option,int userId,String username, String firstName, String lastName, String email, String password, String role){
        switch (option){
            case 1:
            addAdmin(username,firstName,lastName,email,password,role);
            break;

            case 2:
            viewAllUser();
            break;

            case 3:
            viewUserById(userId);
            break;

            case 4:
            editUser(userId);
            break;

            case 5:
            removeUser(userId);
            break;
            
        }
    }

    
    //addUser function (admin)
    private void addAdmin(String username, String firstName, String lastName, String email, String password, String role){
        //will be complete later

    };

    //function for get all existen user in system including admin,teacher,student 
    private void viewAllUser(){
        //will be complete later
    };

    private void viewUserById(int userId){};

    private void editUser(int userId){};

    //a function that allow only admin to delete any user from the system
    private void removeUser(int userId){
        //
    };


    public void manageQuiz(int option,int quizId){
        switch (option) {
            case 1:
            listAllQuiz();
            break;

            case 2:
            if (quizId != -1){
                listQuizById(quizId);
            }else{
                System.out.println("Please provide a quiz ID.");
            }
            break;

            case 3:
            if (quizId != -1){
                editQuiz(quizId);
            }else{
                System.out.println("Please provide a quiz ID.");
            }
            break;
            
            case 4:
            if (quizId != -1){
                removeQuiz(quizId);
            }else{
                System.out.println("Please provide a quiz ID.");
            }
            break;
        }
    }

    //function that list all the existing quiz
    private void listAllQuiz(){};
    // list the quiz by input id 
    private void listQuizById(int quizId){};
    //edit quiz
    private void editQuiz(int quizId){};
    //function to remove any quiz by input it quizId
    private void removeQuiz(int quizId){};






}
