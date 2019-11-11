package game;

/**
 * Class to be run from the terminal for a game of Minishogi.
 * @author ricksurya
 */
public class Main {
    /**
     * The main method
     * @param args : arguments from terminal. "-i" flag will run interactive mode, "-f <filename>" would run file mode
     *              on file <filename>
     */
    public static void main(String[] args) {
        try {
            if (args[0].equals("-i")) {
                Reporter reporter = new Reporter();
                Controller controller = new Controller(reporter);
                controller.playInteractiveMode();
            } else if (args[0].equals("-f")) {
                Reporter reporter = new Reporter();
                Controller controller = new Controller(reporter, args[1]);
                controller.playFileMode();
            } else {
                System.out.println("Illegal argument");
            }
        } catch (Exception e) {
            System.out.println("some error: " + e.getMessage());
        }
    }
}
