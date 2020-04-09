import java.util.Scanner;

public class Main {

    private static final int defaultCols = 7;
    private static final int defaultRows = 6;
    private static final int defaultWins = 4;

    public static int xScore = 0;
    public static int oScore = 0;

    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Connect 4!");
        initialise();
    }

    private static void initialise() {
        newGame();
        String playAgain = "YES";
        String inputtedString;

        while (true) {
            System.out.println(Board.player.X + " has " + xScore + " point(s).");
            System.out.println(Board.player.O + " has " + oScore + " point(s).");

            System.out.println("Type '" + playAgain + "' to play again!");
            inputtedString = input.nextLine();

            if (!inputtedString.equalsIgnoreCase(playAgain)) {
                System.out.println("Okay, thanks for playiing!");
                break;
            }

            newGame();
        }

    }

    private static void newGame() {
        String inputtedLine;
        while (true) {
            System.out.println("\nWould you like use default dimensions? (Y/N)");

            inputtedLine = input.nextLine();
            if ((inputtedLine.equalsIgnoreCase("Y")) || (inputtedLine.equalsIgnoreCase("N"))) {
                break;
            }

            System.err.println("Invalid input. Enter 'Y' or 'N'.\n");
        }

        if (inputtedLine.equalsIgnoreCase("Y")) {
            new Board(defaultCols, defaultRows, defaultWins, getTurn());
            return;
        }

        if (inputtedLine.equalsIgnoreCase("N")) {
            int col = getValue("column size", 2, 0);
            int row = getValue("row size", 2, 0);
            int wins = getValue("consecutive pieces for a win", 2, Math.max(col, row));
            new Board(col, row, wins, getTurn());
            return;
        }

        throw new IllegalArgumentException("inputedLine is not Y or N");
    }

    private static Board.player getTurn() {
        if (((xScore + oScore) % 2) == 0) {
            return Board.player.X;

        }
        return Board.player.O;
    }

    /** max should equal 0 if there is no max **/
    private static int getValue(String valueName, int min, int max) {
        if ((max!=0) && (max<min)) {
            throw new IllegalArgumentException("max is smaller than min and max is not 0");
        }

        String currentInput = "";

        while (true) {
            System.out.println("\nEnter the " + valueName + ": ");
            currentInput = input.nextLine();

            try {
                if (max == 0) {
                    if ((Integer.parseInt(currentInput) >= min)) {
                        break;
                    }
                } else {
                    if ((Integer.parseInt(currentInput) > min) || (Integer.parseInt(currentInput) < max)) {
                        break;
                    }
                }

            } catch (NumberFormatException nfe) {}

            if (max==0) {
                System.err.println("Enter a number larger or equal to " + min + ".");
            } else {
                System.err.println("Enter an number from " + min + " to " + max + ".");
            }
        }

        return Integer.parseInt(currentInput);
    }
}
