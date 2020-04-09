import java.util.ArrayList;

public class WinChecker {
    private static Board.player[][] board;
    private static int toWin;

    public static boolean isWin(int move, Board.player turn, Board.player[][] givenBoard, int givenToWin) {
        board = givenBoard;
        toWin = givenToWin;

        int rowNum = board[0].length;
        int colNum = board.length;
        int col = move;
        int row = getRow(col, rowNum, turn);


        if (rowWin(col, row, turn, colNum) || colWin(col, row, turn, rowNum) || diagWin(col, row, turn, colNum, rowNum)) {
            return true;
        }

        return false;
    }

    private static int getRow(int col, int rowNum, Board.player turn) {
        for (int r=0; r<rowNum; r++) {
            if (board[col][r] != null) {
                if (board[col][r] != turn) {
                    throw new IllegalArgumentException("The player found does not match the turn");
                }
                return r;
            }
        }
        throw new IllegalArgumentException("Entire column is blank");

    }

    private static boolean rowWin(int col, int row, Board.player turn, int colNum) {
        int found = 0;

        for (int c = col; c <= (colNum-1); c++) {
            if (board[c][row] == turn) {
                found++;
            } else {
                break;
            }
        }

        for (int c = col; c >= 0; c--) {
            if (board[c][row] == turn) {
                found++;
            } else {
                break;
            }
        }

        if ((found-1) == toWin) {
            return true;
        }
        return false;
    }

    private static boolean colWin(int col, int row, Board.player turn, int rowNum) {
        int found = 0;

        for (int r = row; r <= (rowNum-1); r++) {
            if (board[col][r] == turn) {
                found++;
            } else {
                break;
            }
        }

        for (int r = row; r >= 0; r--) {
            if (board[col][r] == turn) {
                found++;
            } else {
                break;
            }
        }

        if ((found-1) == toWin) {
            return true;
        }
        return false;
    }

    private static boolean diagWin(int col, int row, Board.player turn, int colNum, int rowNum) {
        int found = 0;

        int currentCol = col;
        int currentRow = row;
        while ((currentCol<colNum) && (currentRow<rowNum)) {
            if (board[currentCol][currentRow] == turn) {
                found++;
            } else {
                break;
            }
            currentCol++;
            currentRow++;
        }

        currentCol = col;
        currentRow = row;
        while ((currentCol>=0) && (currentRow>=0)) {
            if (board[currentCol][currentRow] == turn) {
                found++;
            } else {
                break;
            }
            currentCol--;
            currentRow--;
        }

        currentCol = col;
        currentRow = row;
        while ((currentCol<colNum) && (currentRow>=0)) {
            if (board[currentCol][currentRow] == turn) {
                found++;
            } else {
                break;
            }
            currentCol++;
            currentRow--;
        }

        currentCol = col;
        currentRow = row;
        while ((currentCol>=0) && (currentRow<row)) {
            if (board[currentCol][currentRow] == turn) {
                found++;
            } else {
                break;
            }
            currentCol--;
            currentRow++;
        }


        if ((found-3) == toWin) {
            return true;
        }
        return false;
    }
}