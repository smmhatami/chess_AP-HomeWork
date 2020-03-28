package game;

public class BoardUnitSquare {
    private Chessman currentChessman;
    private int[] position;

    public BoardUnitSquare(int posX, int posY) {
        this.position = new int[2];
        this.position[0] = posX;
        this.position[1] = posY;
    }

    public void setCurrentChessman(Chessman currentChessman) {
        this.currentChessman = currentChessman;
    }

    public void setPosition(int posX, int posY) {
        this.position = new int[2];
        this.position[0] = posX;
        this.position[1] = posY;
    }

    public Player getChessmanPlayer() {
        return null;
    }
}
