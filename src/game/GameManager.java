package game;

import menues.User;

public class GameManager {
    private BoardUnitSquare[][] board;
    private Player whitePlayer;
    private Player blackPlayer;
    private Player activePlayer;
    private BoardUnitSquare selectedSquare;
    private java.util.ArrayList<Move> allGameMoves;
    private boolean moveDone;
    private java.util.ArrayList<Chessman> killedChessmen;
    private int limit;

    public GameManager(User whiteUser, User blackUser, int limit) {
        board = new BoardUnitSquare[8][8];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j].setPosition(i, j);
            }
        }
        this.whitePlayer = new Player(whiteUser, "w");
        this.blackPlayer = new Player(blackUser, "b");
        this.limit = limit;
    }

    public String processSquareSelecting(int posX, int posY) {
        return null;
    }

    public void setMoveDone(boolean moveDone) {
        this.moveDone = moveDone;
    }

    public String processMove(int destinationPosx, int destinationPosY) {
        return null;
    }

    public String ProcessNextTurn() {
        return null;
    }

    public String printTurn() {
        return null;
    }

    public String processUndo() {
        return null;
    }

    public void printPlayerMoves() {
    }

    public void printAllMoves() {
    }

    public void printPlayerKilledChessmen() {
    }

    public void printAllKilledChessmen() {
    }

    public void printBoard() {
    }

    private void endGame(int player1Score, int player2Score) {
    }
}
