package game;

public class Chessman {
    private Player owner;
    private char type;
    private BoardUnitSquare[][] gameBoard;
    private int movesDone;
    private BoardUnitSquare deathSpot;

    public Player getOwner() {
        return owner;
    }

    public Chessman(Player owner, char type, GameManager gameManager) {
        this.owner = owner;
        this.type = type;
        gameBoard = gameManager.getBoard();
        movesDone = 0;
        deathSpot = null;
    }

    public void changeMoved(int count) {
        movesDone += count;
    }

    public boolean isMovePossible(BoardUnitSquare startSquare, BoardUnitSquare destinationSquare) {
        if (destinationSquare.getChessmanPlayer() == this.owner)
            return false;
        int frontDistance = tellFrontDistance(startSquare, destinationSquare);
        int rightDistance = tellRightDistance(startSquare, destinationSquare);

        if (type == 'P')
            return moveValidatePawn(startSquare, destinationSquare, owner);
        if (type == 'R')
            return moveValidateRook(startSquare, destinationSquare, frontDistance, rightDistance);
        if (type == 'N')
            return moveValidateKnight(startSquare, destinationSquare, frontDistance, rightDistance);
        if (type == 'B')
            return moveValidateBishop(startSquare, destinationSquare, frontDistance, rightDistance);
        if (type == 'Q')
            return moveValidateQueen(startSquare, destinationSquare, frontDistance, rightDistance);
        //if (type == 'K')
        return moveValidateKing(startSquare, destinationSquare, frontDistance, rightDistance);
    }

    @Override
    public String toString() {
        return Character.toString(type) + owner.getColor();
    }

    public String killedToString() {
        return this + " killed in spot " + deathSpot;
    }

    public void setDeathSpot(BoardUnitSquare deathSpot) {
        this.deathSpot = deathSpot;
    }

    public char getType() {
        return type;
    }

    private boolean moveValidatePawn(BoardUnitSquare startSquare, BoardUnitSquare destinationSquare, Player owner) {
        int playerMovingSide = owner.getColor() == 'w' ? 1 : -1;
        if (destinationSquare.getCurrentChessman() == null) {
            if (movesDone == 0) {
                if (startSquare.getSquareFrontRow(playerMovingSide).getCurrentChessman() != null) {
                    return false;
                }
                return startSquare.getSquareFrontRow(playerMovingSide) == destinationSquare || startSquare.getSquareFrontRow(playerMovingSide * 2) == destinationSquare;
            }
            return startSquare.getSquareFrontRow(playerMovingSide) == destinationSquare;
        }
        if (startSquare.getSquareFrontRow(playerMovingSide).getSquareRightRow(1) == destinationSquare)
            return true;
        return startSquare.getSquareFrontRow(playerMovingSide).getSquareRightRow(-1) == destinationSquare;
    }

    private boolean moveValidateRook(BoardUnitSquare startSquare, BoardUnitSquare destinationSquare, int frontDistance, int rightDistance) {
        if (frontDistance * rightDistance != 0)
            return false;
        BoardUnitSquare square = startSquare;
        if (frontDistance > 0) {
            while ((square = square.getSquareFrontRow(1)) != destinationSquare) {
                if (square.getCurrentChessman() != null)
                    return false;
            }
        }
        if (frontDistance < 0) {
            while ((square = square.getSquareFrontRow(-1)) != destinationSquare) {
                if (square.getCurrentChessman() != null)
                    return false;
            }
        }
        if (rightDistance > 0) {
            while ((square = square.getSquareRightRow(1)) != destinationSquare) {
                if (square.getCurrentChessman() != null)
                    return false;
            }
        }
        if (rightDistance < 0) {
            while ((square = square.getSquareRightRow(-1)) != destinationSquare) {
                if (square.getCurrentChessman() != null)
                    return false;
            }
        }
        return true;
    }

    private boolean moveValidateKing(BoardUnitSquare startSquare, BoardUnitSquare destinationSquare, int frontDistance, int rightDistance) {
        return frontDistance <= 1 && frontDistance >= -1 && rightDistance <= 1 && rightDistance >= -1;
    }

    private boolean moveValidateQueen(BoardUnitSquare startSquare, BoardUnitSquare destinationSquare, int frontDistance, int rightDistance) {
        if (frontDistance * rightDistance == 0)
            return moveValidateRook(startSquare, destinationSquare, frontDistance, rightDistance);
        if (frontDistance == rightDistance || frontDistance == -rightDistance)
            return moveValidateBishop(startSquare, destinationSquare, frontDistance, rightDistance);
        return false;
    }

    private boolean moveValidateBishop(BoardUnitSquare startSquare, BoardUnitSquare destinationSquare, int frontDistance, int rightDistance) {
        BoardUnitSquare square = startSquare;
        if (frontDistance == rightDistance) {
            if (frontDistance > 0) {
                while ((square = square.getSquareVerticalUpRight(1)) != destinationSquare) {
                    if (square.getCurrentChessman() != null)
                        return false;
                }
                return true;
            }
            if (frontDistance < 0) {
                while ((square = square.getSquareVerticalUpRight(-1)) != destinationSquare) {
                    if (square.getCurrentChessman() != null)
                        return false;
                }
                return true;
            }
        }
        if (frontDistance == -rightDistance) {
            if (frontDistance > 0) {
                while ((square = square.getSquareVerticalUpLeft(1)) != destinationSquare) {
                    if (square.getCurrentChessman() != null)
                        return false;
                }
                return true;
            }
            if (frontDistance < 0) {
                while ((square = square.getSquareVerticalUpLeft(-1)) != destinationSquare) {
                    if (square.getCurrentChessman() != null)
                        return false;
                }
                return true;
            }
        }
        return false;
    }

    private boolean moveValidateKnight(BoardUnitSquare startSquare, BoardUnitSquare destinationSquare, int frontDistance, int rightDistance) {
        return frontDistance * rightDistance == 2 || frontDistance * rightDistance == -2;
    }

    private static int tellRightDistance(BoardUnitSquare startSquare, BoardUnitSquare destinationSquare) {
        return destinationSquare.getPosition()[1] - startSquare.getPosition()[1];
    }

    private static int tellFrontDistance(BoardUnitSquare startSquare, BoardUnitSquare destinationSquare) {
        return destinationSquare.getPosition()[0] - startSquare.getPosition()[0];
    }
}
