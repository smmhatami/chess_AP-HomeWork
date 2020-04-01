package menues;

import java.util.regex.Pattern;

public class UserMenu extends Menu {
    private User activeUser;

    public UserMenu(User activeUser) {
        this.activeUser = activeUser;
    }

    public void processInputCommand(String command) {
        if (command.endsWith(" ") || command.startsWith(" ")) {
            System.out.println("invalid command");
            return;
        }
        String[] splitCommand = command.split(" ");
        if (splitCommand[0].equals("new_game") && splitCommand.length == 3) {
            Pattern numPattern = Pattern.compile("^-?\\d+$");
            if (numPattern.matcher(splitCommand[2]).find()) {
                processNewGame(Integer.parseInt(splitCommand[2]), splitCommand[1], splitCommand[2]);
                System.out.println("new game started successfully between " + activeUser.getUsername() + " and " + splitCommand[1] + " with limit " + splitCommand[2]);
                return;
            }
        }
        if (splitCommand[0].equals("scoreboard") && splitCommand.length == 1) {
            printScoreBoard();
            return;
        }
        if (splitCommand[0].equals("list_users") && splitCommand.length == 1) {
            User.printUsersList();
            return;
        }
        if (splitCommand[0].equals("help") && splitCommand.length == 1) {
            printHelp();
            return;
        }
        if (splitCommand[0].equals("logout") && splitCommand.length == 1) {
            System.out.println("logout successful");
            logout();
            return;
        }
        System.out.println("invalid command");
    }

    private void printScoreBoard() {
        User.sortScoreBoard();
        for (User user : User.getScoreboard()) {
            System.out.println(user);
        }
    }

    private void processNewGame(int limit, String inputUsername, String gameLimit) {
        if (User.isUserPassInvalid(inputUsername, "noPass"))
            return;
        if (Integer.parseInt(gameLimit) < 0) {
            System.out.println("number should be positive to have a limit or 0 for no limit");
            return;
        }
        if (activeUser.getUsername().equals(inputUsername)) {
            System.out.println("you must choose another player to start a game");
            return;
        }
        if (!User.userExists(inputUsername)) {
            System.out.println("no user exists with this username");
            return;
        }
        Menu.setActiveMenu(new GameMenu(activeUser, User.getUserByName(inputUsername), limit));
    }

    private static void printHelp() {
        System.out.println("new_game [username] [limit]\n" +
                "scoreboard\n" +
                "list_users\n" +
                "help\n" +
                "logout");
    }

    private void logout() {
        Menu.setActiveMenu(new RegisterMenu());
    }
}
