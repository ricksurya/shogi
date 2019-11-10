package board;

/** A move in the game of Shogi
 *  @author ricksurya
 */
public class Move {

    /** The components of a Move. */
    private final Square _from, _to;
    /** The printed form of a Move. */
    private String _str;

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

    public boolean isPromote() {return _promote;}

    @Override
    public String toString() {
        return _str;
    }
}

