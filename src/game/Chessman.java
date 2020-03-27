package game;

public class Chessman {
    private Player owner;
    private char type;

    public void checkMovePosssible(BoardUnitSquare destinationSquare) {
    }

    @Override
    public String toString() {
        return type + owner.getColor();
    }

    public void killedToString() {
    }

    private static boolean moveValidatePawn(BoardUnitSquare start, BoardUnitSquare end) {
        return false;
    }

    private static boolean moveValidateRook(BoardUnitSquare start, BoardUnitSquare end) {
        return false;
    }

    private static boolean moveValidateKing(BoardUnitSquare start, BoardUnitSquare end) {
        return false;
    }

    private static boolean moveValidateQueen(BoardUnitSquare start, BoardUnitSquare end) {
        return false;
    }

    private static boolean moveValidateBishop(BoardUnitSquare start, BoardUnitSquare end) {
        return false;
    }

    private static boolean moveValidateKnight(BoardUnitSquare start, BoardUnitSquare end) {
        return false;
    }

}
