package board;

import pieces.Piece;

public class Drop extends Move {

    private Piece piece;

    public Drop(Square to, Piece p) {
        super(null, to);
        piece = p;
    }

    @Override
    public String toString() {
        return String.format("drop %s %s", piece,  getTo());
    }

    public Piece getPiece() {
        return piece;
    }
}
