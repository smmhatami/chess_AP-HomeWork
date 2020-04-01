package game;

import menues.Menu;
import menues.User;
import menues.UserMenu;

import java.util.ArrayList;

public class GameManager {
    private BoardUnitSquare[][] board;
    private Player whitePlayer;
    private Player blackPlayer;
    private Player activePlayer;
    private Chessman selectedChessman;
    private java.util.ArrayList<Move> allGameMoves;
    private boolean moveDone;
    private java.util.ArrayList<Chessman> killedChessmen;
    private int limit;
    private boolean undoUsed;

    public GameManager(User whiteUser, User blackUser, int limit) {
        board = new BoardUnitSquare[8][8];
        killedChessmen = new ArrayList<>();
        allGameMoves = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new BoardUnitSquare(i, j, board);
            }
        }
        this.whitePlayer = new Player(whiteUser, 'w');
        this.blackPlayer = new Player(blackUser, 'b');
        this.limit = limit == 0 ? -1 : limit;
        putInitialChessmen();
        activePlayer = whitePlayer;
        selectedChessman = null;
        moveDone = false;
        undoUsed = false;
    }

    public String processChessmanSelecting(int posX, int posY) {
        BoardUnitSquare inputSquare = getSquareByPosition(posX, posY);
        if (inputSquare.getChessmanPlayer() != activePlayer) {
            if (inputSquare.getCurrentChessman() == null)
                return "no piece on this spot";
            return "you can only select one of your pieces";
        }
        selectedChessman = inputSquare.getCurrentChessman();
        return "selected";
    }

    public String deselect() {
        if (selectedChessman == null)
            return "no piece is selected";
        selectedChessman = null;
        return "deselected";
    }

    public BoardUnitSquare getSquareByPosition(int posX, int posY) {
        return board[posX - 1][posY - 1];
    }

    public void setMoveDone(boolean moveDone) {
        this.moveDone = moveDone;
    }

    public boolean isMoveDone() {
        return moveDone;
    }

    public String processMove(int destinationPosX, int destinationPosY) {
        if (selectedChessman == null)
            return "do not have any selected piece";
        BoardUnitSquare destinationSquare = getSquareByPosition(destinationPosX, destinationPosY);
        if (selectedChessman.isMovePossible(selectedChessman.getCurrentPosition(), destinationSquare)) {
            setMoveDone(true);
            Move lastMove = new Move(selectedChessman.getCurrentPosition(), destinationSquare, selectedChessman, destinationSquare.getCurrentChessman(), activePlayer);
            allGameMoves.add(lastMove);
            if (destinationSquare.getCurrentChessman() != null) {
                if (destinationSquare.getCurrentChessman().getType() == 'K')
                    activePlayer.isWon = true;
                killedChessmen.add(destinationSquare.getCurrentChessman());
                destinationSquare.getCurrentChessman().setDeathSpot(destinationSquare);
            }
            destinationSquare.setCurrentChessman(selectedChessman);
            selectedChessman.changeMoved(1);
            selectedChessman.getCurrentPosition().setCurrentChessman(null);
            selectedChessman.setCurrentPosition(destinationSquare);
            if (!lastMove.didDestroyChessman())
                return "moved";
            return "rival piece destroyed";

        }
        return "cannot move to the spot";
    }

    public String processNextTurn() {
        if (!moveDone)
            return "you must move then proceed to next turn";
        switchActivePlayer();
        limit -= 1;
        return "turn completed";
    }

    public void checkEndGame() {
        if (blackPlayer.isWon) {
            endGame(0, 3);
            return;
        }
        if (whitePlayer.isWon) {
            endGame(3, 0);
            return;
        }
        if (limit == 0) {
            endGame(1, 1);
        }
    }

    public String printTurn() {
        return "it is player " + activePlayer.getUser().getUsername() + " turn with color " + (activePlayer.getColor() == 'w' ? "white" : "black");
    }

    public String processUndo() {
        if (activePlayer.getUndoRemaining() == 0) {
            return "you cannot undo anymore";
        }
        if (!moveDone) {
            return "you must move before undo";
        }
        if (undoUsed) {
            return "you have used your undo for this turn";
        }
        activePlayer.setUndoRemaining(activePlayer.getUndoRemaining() - 1);
        undoUsed = true;
        moveDone = false;
        Move lastMove = allGameMoves.get(allGameMoves.size() - 1);
        lastMove.undoMove();
        if (lastMove.didDestroyChessman())
            killedChessmen.remove(lastMove.getDestroyedChessman());
        allGameMoves.remove(lastMove);
        activePlayer.isWon = false;
        return "undo completed";
    }

    public String printUndoRemaining() {
        return "you have " + activePlayer.getUndoRemaining() + " undo moves";
    }

    public void printPlayerMoves() {
        for (Move move : allGameMoves) {
            if (move.getMoverPlayer() != activePlayer)
                continue;
            System.out.println(move);
        }
    }

    public void printAllMoves() {
        for (Move move : allGameMoves) {
            System.out.println(move);
        }
    }

    public void printPlayerKilledChessmen() {
        for (Chessman killedChessman : killedChessmen) {
            if (killedChessman.getOwner() != activePlayer)
                continue;
            System.out.println(killedChessman.killedToString());
        }
    }

    public void printAllKilledChessmen() {
        for (Chessman killedChessman : killedChessmen) {
            System.out.println(killedChessman.killedToString());
        }
    }

    public void printBoard() {

        for (int i = 7; i >= 0; i--) {
            BoardUnitSquare[] boardRow = board[i];
            StringBuilder row = new StringBuilder();
            for (BoardUnitSquare square : boardRow) {
                if (square.getCurrentChessman() == null)
                    row.append("  |");
                else
                    row.append(square.getCurrentChessman()).append("|");
            }
            System.out.println(row);
        }
    }

    private void endGame(int player1Score, int player2Score) {
        whitePlayer.addUserScore(player1Score);
        blackPlayer.addUserScore(player2Score);
        if (player1Score > player2Score) {
            System.out.println("player " + whitePlayer.getUser().getUsername() + " with color white won");
            blackPlayer.getUser().addLoosesCount();
            whitePlayer.getUser().addWinsCount();
        }
        if (player2Score > player1Score) {
            System.out.println("player " + blackPlayer.getUser().getUsername() + " with color black won");
            blackPlayer.getUser().addWinsCount();
            whitePlayer.getUser().addLoosesCount();
        }
        if (player1Score == player2Score) {
            System.out.println("draw");
            blackPlayer.getUser().addDrawsCount();
            whitePlayer.getUser().addDrawsCount();
        }
        Menu.setActiveMenu(new UserMenu(whitePlayer.getUser()));
    }

    private void switchActivePlayer() {
        activePlayer = activePlayer == whitePlayer ? blackPlayer : whitePlayer;
        selectedChessman = null;
        setMoveDone(false);
        undoUsed = false;
    }

    public void processForfeit() {
        if (activePlayer == whitePlayer)
            endGame(-1, 2);
        else
            endGame(2, -1);
    }

    private void putInitialChessmen() {
        //Pawns
        for (int i = 0; i < 8; i++) {
            board[1][i].setCurrentChessman(new Chessman(whitePlayer, 'P', board[1][i]));
            board[6][i].setCurrentChessman(new Chessman(blackPlayer, 'P', board[6][i]));
        }
        // White back
        board[0][0].setCurrentChessman(new Chessman(whitePlayer, 'R', board[0][0]));
        board[0][7].setCurrentChessman(new Chessman(whitePlayer, 'R', board[0][7]));
        board[0][1].setCurrentChessman(new Chessman(whitePlayer, 'N', board[0][1]));
        board[0][6].setCurrentChessman(new Chessman(whitePlayer, 'N', board[0][6]));
        board[0][2].setCurrentChessman(new Chessman(whitePlayer, 'B', board[0][2]));
        board[0][5].setCurrentChessman(new Chessman(whitePlayer, 'B', board[0][5]));
        board[0][3].setCurrentChessman(new Chessman(whitePlayer, 'Q', board[0][3]));
        board[0][4].setCurrentChessman(new Chessman(whitePlayer, 'K', board[0][4]));
        // Black back
        board[7][0].setCurrentChessman(new Chessman(blackPlayer, 'R', board[7][0]));
        board[7][7].setCurrentChessman(new Chessman(blackPlayer, 'R', board[7][7]));
        board[7][1].setCurrentChessman(new Chessman(blackPlayer, 'N', board[7][1]));
        board[7][6].setCurrentChessman(new Chessman(blackPlayer, 'N', board[7][6]));
        board[7][2].setCurrentChessman(new Chessman(blackPlayer, 'B', board[7][2]));
        board[7][5].setCurrentChessman(new Chessman(blackPlayer, 'B', board[7][5]));
        board[7][3].setCurrentChessman(new Chessman(blackPlayer, 'Q', board[7][3]));
        board[7][4].setCurrentChessman(new Chessman(blackPlayer, 'K', board[7][4]));
    }
}
