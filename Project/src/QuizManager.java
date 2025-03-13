import java.util.*;

public class QuizManager {
    private HashMap<String, Quiz> quizStorage;

    // initailize the map
    public QuizManager () {
        this.quizStorage = new HashMap<>();
    }

    // show the quiz and questions
    public Quiz getQuiz (String title) {
        return quizStorage.get(title);
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

    public static void main(String[] args) {
        // Scanner newScanner = new Scanner(System.in);
        // int x = newScanner.nextInt() - 1;
        // System.out.println(x);
        QuizManager test = new QuizManager();
        test.createQuiz();
    }
}
