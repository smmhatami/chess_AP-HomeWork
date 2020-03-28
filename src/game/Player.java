package game;

import menues.User;

public class Player {
    private User user;
    private int undoRemaining;
    private String color;

    public Player(User user, String color) {
        this.user = user;
        this.undoRemaining = 2;
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
