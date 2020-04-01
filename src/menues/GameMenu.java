package menues;

import game.GameManager;

import java.util.regex.Pattern;

public class GameMenu extends Menu {
    private game.GameManager gameManager;

    public GameMenu(User whitePlayer, User blackPlayer, int gameLimit) {
        this.gameManager = new GameManager(whitePlayer, blackPlayer, gameLimit);
    }

    public void processInputCommand(String command) {
        if (command.endsWith(" ") || command.startsWith(" ")) {
            System.out.println("invalid command");
            return;
        }
        String[] splitCommand = command.split(" ");
        if (splitCommand[0].equals("select") && splitCommand.length == 2) {
            Pattern positionPattern = Pattern.compile("^-?\\d+,-?\\d+$");
            if (positionPattern.matcher(splitCommand[1]).find()) {
                processSelect(splitCommand[1]);
                return;
            }
        }
        if (splitCommand[0].equals("deselect") && splitCommand.length == 1) {
            System.out.println(gameManager.deselect());
            return;
        }
        if (splitCommand[0].equals("move") && splitCommand.length == 2) {
            Pattern positionPattern = Pattern.compile("^-?\\d+,-?\\d+$");
            if (positionPattern.matcher(splitCommand[1]).find()) {
                processMove(splitCommand[1]);
                return;
            }
        }
        if (splitCommand[0].equals("next_turn") && splitCommand.length == 1) {
            System.out.println(gameManager.processNextTurn());
            gameManager.checkEndGame();
            return;
        }
        if (splitCommand[0].equals("show_turn") && splitCommand.length == 1) {
            System.out.println(gameManager.printTurn());
            return;
        }
        if (splitCommand[0].equals("undo") && splitCommand.length == 1) {
            System.out.println(gameManager.processUndo());
            return;
        }
        if (splitCommand[0].equals("undo_number") && splitCommand.length == 1) {
            System.out.println(gameManager.printUndoRemaining());
            return;
        }
        if (splitCommand[0].equals("show_moves") && splitCommand.length == 1) {
            gameManager.printPlayerMoves();
            return;
        }
        if (splitCommand[0].equals("show_moves") && splitCommand[1].equals("-all") && splitCommand.length == 2) {
            gameManager.printAllMoves();
            return;
        }
        if (splitCommand[0].equals("show_killed") && splitCommand.length == 1) {
            gameManager.printPlayerKilledChessmen();
            return;
        }
        if (splitCommand[0].equals("show_killed") && splitCommand[1].equals("-all") && splitCommand.length == 2) {
            gameManager.printAllKilledChessmen();
            return;
        }
        if (splitCommand[0].equals("show_board") && splitCommand.length == 1) {
            gameManager.printBoard();
            return;
        }
        if (splitCommand[0].equals("help") && splitCommand.length == 1) {
            printHelp();
            return;
        }
        if (splitCommand[0].equals("forfeit") && splitCommand.length == 1) {
            processForfeit();
            return;
        }
        System.out.println("invalid command");
    }

    private void processSelect(String position) {
        String[] posArray = position.split(",");
        Pattern positionPattern = Pattern.compile("^[1-8],[1-8]$");
        if (!positionPattern.matcher(position).find()) {
            System.out.println("wrong coordination");
            return;
        }
        int posX = Integer.parseInt(posArray[0]);
        int posY = Integer.parseInt(posArray[1]);
        System.out.println(gameManager.processChessmanSelecting(posX, posY));
    }

    private void processMove(String destinationString) {
        if (gameManager.isMoveDone()) {
            System.out.println("already moved");
            return;
        }
        Pattern positionPattern = Pattern.compile("^[1-8],[1-8]$");
        if (!positionPattern.matcher(destinationString).find()) {
            System.out.println("wrong coordination");
            return;
        }
        int destinationX = Integer.parseInt(destinationString.split(",")[0]);
        int destinationY = Integer.parseInt(destinationString.split(",")[1]);
        System.out.println(gameManager.processMove(destinationX, destinationY));
    }


    private void printHelp() {
        System.out.println("select [x],[y]\n" +
                "deselect\n" +
                "move [x],[y]\n" +
                "next_turn\n" +
                "show_turn\n" +
                "undo\n" +
                "undo_number\n" +
                "show_moves [-all]\n" +
                "show_killed [-all]\n" +
                "show_board\n" +
                "help\n" +
                "forfeit");
    }

    private void processForfeit() {
        System.out.println("you have forfeited");
        gameManager.processForfeit();
    }
}
