package menues;

import game.GameManager;

import javax.jws.soap.SOAPBinding;

public class GameMenu extends Menu {
    private game.GameManager gameManager;
    private int gameLimit;

    public GameMenu(User whitePlayer, User blackPlayer, int gameLimit) {
        this.gameLimit = gameLimit;
        this.gameManager = new GameManager(whitePlayer, blackPlayer, gameLimit);
    }

    public void processInputCommand(String input) {
    }

    private void processSelect(int posX, int posY) {
    }

    private void processDeselect() {
    }

    private void processMove(int destinnationX, int destinationY) {
    }

    private void processNextTurn() {
    }

    private void processShowTurn() {
    }

    private void printUndoNumber() {
    }

    private void processUndo() {
    }

    private void processShowMoves(String type) {
    }

    private void processShowKilled(String type) {
    }

    private void processShowBoard() {
    }

    private void printHelp() {
    }

    private void processForfiet() {
    }
}
