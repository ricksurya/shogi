package board;

/** A move in the game of Shogi. A move consists of a FROM square and a TO (destination) square.
 *  @author ricksurya
 */
public class Move {

    /** The components of a Move. */
    private final Square _from, _to;
    /** The printed form of a Move. */
    private String _str;
    /** Whether the move intends to promote the moved piece. */
    private final boolean _promote;

    /** Construct the Move FROM-TO. */
    public Move(Square from, Square to, boolean promote) {
        _promote = promote;
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

    /** Return whether the move intends to promote the piece moved. */
    public boolean isPromote() {return _promote;}

    @Override
    public String toString() {
        return _str;
    }
}

