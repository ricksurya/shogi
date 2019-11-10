package board;

/** A move in the game of Shogi
 *  @author Rick Surya
 */
public class Move {

    /** The components of a Move. */
    private final Square _from, _to;
    /** The printed form of a Move. */
    private String _str;

    /** Construct the Move FROM-TO. */
    public Move(Square from, Square to) {
        _from = from;
        _to = to;
        _str = String.format("move %s %s", from, to);
    }

    /** Return the square moved from. */
    public Square getFrom() {
        return _from;
    }

    /** Return the square moved to. */
    public Square getTo() {
        return _to;
    }

    @Override
    public String toString() {
        return _str;
    }
}

