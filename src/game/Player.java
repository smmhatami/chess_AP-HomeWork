package game;

import menues.User;

public class Player {
    private User user;
    private int undoRemaining;
    private char color;
    public boolean isWon;

    public Player(User user, char color) {
        this.user = user;
        this.undoRemaining = 2;
        this.color = color;
        this.isWon = false;
    }

    public int getUndoRemaining() {
        return undoRemaining;
    }

    public void setUndoRemaining(int undoRemaining) {
        this.undoRemaining = undoRemaining;
    }

    public char getColor() {
        return color;
    }

    public void addUserScore(int amount) {
        this.user.addScore(amount);
    }

    public User getUser() {
        return user;
    }
}
