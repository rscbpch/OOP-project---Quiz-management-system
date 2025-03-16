import java.util.*;

public class QuizManager {
    private HashMap<String, Quiz> quizStorage;

    // initailize the map
    public QuizManager () {
        this.quizStorage = new HashMap<>();
    }

    // create the quiz and questions for the quiz
    public void createQuiz () {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Quiz title: ");
        String title = scanner.nextLine();

        System.out.println("Creator ID: ");
        int cid = scanner.nextInt();
        scanner.nextLine();

        int qzid = quizStorage.size()+1;

        Quiz newQuiz = new Quiz(qzid, title, cid);
        quizStorage.put(title, newQuiz);

        char addMore = ' ';
        do {
            System.out.println("Enter the question: ");
            String qText = scanner.nextLine();

            System.out.println("Enter the options: ");
            String[] qOption = scanner.nextLine().split(",");

            System.out.println("Enter the correct answer (1-4): ");
            int qAnswer = scanner.nextInt() - 1;
            scanner.nextLine();

            int qsid = newQuiz.getQuestions().size() + 1;

            Qcm newQuestion = new Qcm(qsid, qzid, qText, qOption, qAnswer);
            newQuiz.addQuestion(newQuestion);

            System.out.println("Do you want to add more questions? (y/n)");
            addMore = scanner.next().charAt(0);
            scanner.nextLine();
            while (addMore != 'y' && addMore != 'Y' && addMore != 'n' && addMore != 'N') {
                System.out.println("(y/n)");
                addMore = scanner.next().charAt(0);
                scanner.nextLine();
            }
        } while (addMore != 'n' && addMore != 'N');

    }

    // show the quiz and questions
    // public Quiz getQuiz (String title) {
    //     return quizStorage.get(title);
    // }

    public void getQuiz (String title) {
        Quiz checkQuiz = quizStorage.get(title);

        if (checkQuiz != null) {
            System.out.println(checkQuiz.getTitle());
            ArrayList<Qcm> checkQuestion = checkQuiz.getQuestions();
            for (int tracker = 0; tracker < checkQuestion.size(); tracker++) {
                Qcm ques = checkQuestion.get(tracker);
                int qIndex = tracker+1;
                System.out.println("Question " + qIndex);
                ques.showQuestion();
            }
        } else {
            System.out.println(title + " does not exist.");
        }
    }


    // TO BE IMPLEMENTED IN MAIN CHAT
    public static void main(String[] args) {
        QuizManager test = new QuizManager(); // Initialize the quiz system
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nWelcome to the Quiz System!");
            System.out.println("1. Create a new quiz");
            System.out.println("2. View an existing quiz");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    test.createQuiz();
                    break;
                case 2:
                    System.out.println("Enter quiz title: ");
                    String testTitle = scanner.nextLine();
                    test.getQuiz(testTitle);
                    break;
                case 3:
                    System.out.println("Goodbye monkey");
                    return;
                default:
                System.out.println("No option.");
                    break;
            }
        }
    }

}
