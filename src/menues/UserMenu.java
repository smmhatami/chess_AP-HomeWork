package menues;

import java.util.ArrayList;

public class UserMenu extends Menu {
    private User activeUser;

    public UserMenu(User activeUser) {
        this.activeUser = activeUser;
    }

    public void processInputCommand(String input) {
    }

    private void ProcessNewGame(String username, int limit) {
    }

    public User getActiveUser() {
        return activeUser;
    }

    private void printScoreboard() {
    }

    private static void updateScoreboard() {
    }

    private void processPrintScoreboard() {
    }

    private static void printHelp() {
    }

    private void logout() {
    }
}
