package Pieces;

import BoardFiles.*;

public class Piece {

    private int owner;
    private PieceType type;
    private boolean captured;
    private Square location;

    public Piece(Square sq, int player, PieceType type) {
        this.location = sq;
        this.owner = player;
        this.type = type;
        this.captured = false;
    }

    public boolean isValidMove(Square to) {
        return true;
    }

    public void updatePosition(Square sq) {
        location = sq;
    }

    public boolean isCaptured() {
        return captured;
    }

    public void setCaptured() {
        captured = true;
    }

    public int getPlayer() {
        return owner;
    }

    public void changePlayer() {
        owner = (owner + 1) % 2;
    }


    public String toString() {
        if (type != null) {
            return owner == 1 ? type.toString().toUpperCase() : type.toString();
        }
        return "";
    }
}
