package board;

import pieces.Piece;

/**
 * Class to represent a drop move. It is an extension of the Move class but consists of only a piece and a destination
 * square. I decided to make this an extension of a Move because it is convenient when taking into consideration that
 * finding valid moves for escaping a check includes drops.
 * @author ricksurya
 */
public class Drop extends Move {
    /** The piece to be dropped. */
    private Piece piece;

    /** Constructs a drop to destination square TO and piece P. */
    public Drop(Square to, Piece p) {
        super(null, to, false);
        piece = p;
    }

    @Override
    public String toString() {
        return String.format("drop %s %s", piece,  getTo());
    }

    /** Returns the piece to be dropped. */
    public Piece getPiece() {
        return piece;
    }
}
