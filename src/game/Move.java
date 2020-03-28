package game;

public class Move {
    private BoardUnitSquare startSquare;
    private BoardUnitSquare endSquare;
    private Player moverPlayer;
    private Chessman movedChessman;
    private Chessman destroyedChessman;

    public Move(BoardUnitSquare startSquare, BoardUnitSquare endSquare, Chessman movedChessman, Chessman destroyedChessman , Player moverPlayer) {
        this.startSquare = startSquare;
        this.endSquare = endSquare;
        this.movedChessman = movedChessman;
        this.destroyedChessman = destroyedChessman;
        this.moverPlayer = moverPlayer;
    }

    @Override
    public String toString() {
        String noDestroy =  movedChessman + " " + startSquare + " to " + endSquare ;
        if (destroyedChessman == null)
            return noDestroy;
        return noDestroy + " destroyed " + destroyedChessman;
    }

    public Player getMoverPlayer() {
        return moverPlayer;
    }

    public Chessman getDestroyedChessman() {
        return destroyedChessman;
    }

    public void undoMove(){
        startSquare.setCurrentChessman(movedChessman);
        endSquare.setCurrentChessman(destroyedChessman);
        movedChessman.changeMoved(-1);
        destroyedChessman.setDeathSpot(null);
    }

}
