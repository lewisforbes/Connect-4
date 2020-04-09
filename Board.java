import java.util.Scanner;

public class Board {

    private player[][] board;
    public enum player {X, O}
    private int toWin;

    private int col;
    private int row;

    private Scanner input = new Scanner(System.in);

    public Board(int col, int row, int win, player firstTurn) {
        if ((col<1) || (row<1) || (win<1)) {
            throw new IllegalArgumentException("col, row, and win must be bigger than 1");
        }
        this.col = col;
        this.row = row;
        this.toWin = win;
        this.board = new player[this.col][this.row];
        play(firstTurn);
    }

    private void play(player firstTurn) {
        player turn = firstTurn;
        int parsedMove;

        while (true) {
            displayBoard();

            parsedMove = getMove(turn);

            addMove(parsedMove, turn);
            if ((WinChecker.isWin(parsedMove, turn, board, toWin)) || noMoreMoves()) {
                break;
            }
            turn = changeTurn(turn);
        }

        displayBoard();
        if (WinChecker.isWin(parsedMove, turn, board, toWin)) {
            System.out.println(turn + " won!");

            if (turn == player.X) {
                Main.xScore++;
            } else {
                Main.oScore++;
            }

        } else {
            System.out.println("It's a draw!");
        }
    }

    private boolean noMoreMoves() {
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (board[c][r] != null) {
                    return false;
                }
            }
        }
        return true;
    }

    private void addMove(int move, player player) {
        for (int r=(row-1); r>=0; r--) {
            if (board[move][r] == null) {
                board[move][r] = player;
                return;
            }
        }
        throw new IllegalArgumentException("Move was unable to be added");
    }

    private int getMove(player turn) {
        String inputtedMove;
        while (true) {
            System.out.println("\n" + turn.name() + " to go. Enter your move: ");
            inputtedMove = input.nextLine();

            if (!correctFormat(inputtedMove)) {
                System.err.println("Move is not of the correct format.");
             } else if (!possibleMove(Integer.parseInt(inputtedMove))) {
                System.err.println("Move is not possible.");
            } else {
                break;
            }
        }
        return Integer.parseInt(inputtedMove)-1;
    }

    private boolean correctFormat(String move) {
        try {
            Integer.parseInt(move);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private boolean possibleMove(int moveIndexFromOne) {
        if ((moveIndexFromOne>col) || (moveIndexFromOne<1)) {
            return false;
        }

        if (board[moveIndexFromOne-1][0] == null) {
            return true;
        }
        return false;
    }

    private void displayBoard() {
        String hLine = "—";
        String vLine = "|";
        String emptySquare = " ";
        String leftFoot = "_/";
        String rightFoot = "\\_";
        int buffer = leftFoot.length();

        String rowMarker = " ".repeat(buffer) + hLine + hLine.repeat(4).repeat(col);
        String output = mkColLabels(buffer);
        output += "\n" + rowMarker;

        String currentPlayer;
        String currentRow = "";
        for (int r=0; r<row; r++) {
            currentRow = " ".repeat(buffer) + vLine;
            for (int c=0; c<col; c++) {
                currentPlayer = getPlayerAtPos(c,r, emptySquare);
                currentRow += " " + currentPlayer + " " + vLine;
            }
            output += "\n" + currentRow;
            output += "\n" + rowMarker;
        }

        output += "\n" + leftFoot + " ".repeat(rowMarker.length()-buffer) + rightFoot;
        System.out.println(output);
    }

    private String getPlayerAtPos(int col, int row, String blankChar) {
        if(board[col][row]==null) {
            return blankChar;
        } else {
            return board[col][row].name();
        }
    }

    private String mkColLabels(int buffer) {
        String output = " ".repeat(buffer) + " ";

        for (int c=1; c<=col; c++) {
            output += " " + c + "  ";
        }
        return output;
    }

    private player changeTurn(player turn) {
        if (turn == player.X) {
            return player.O;
        }

        if (turn == player.O) {
            return player.X;
        }

        throw new IllegalArgumentException("The turn passed in was invalid");
    }
}
