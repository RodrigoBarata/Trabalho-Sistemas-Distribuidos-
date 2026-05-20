package utils;

public class Validator {

    public static boolean isValidMove(char[][] board, int row, int col) {
        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            return false;
        }
        return board[row][col] == ' ';
    }

    public static String checkWinner(char[][] board) {
        
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return String.valueOf(board[i][0]);
            }
        }

        
        for (int i = 0; i < 3; i++) {
            if (board[0][i] != ' ' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return String.valueOf(board[0][i]);
            }
        }

        
        if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return String.valueOf(board[0][0]);
        }
        if (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return String.valueOf(board[0][2]);
        }

        
        boolean full = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    full = false;
                    break;
                }
            }
        }

        if (full) {
            return "Empate";
        }

        return null;
    }
}
