package pieces;

/**
 * This class represents all possible piece types.
 */
public enum PieceType {

    DRIVE("d", "Drive"), NOTES("n", "Notes"), GOVERNANCE("g", "Governance"),
    SHIELD("s", "Shield"), RELAY("r", "Relay"), PREVIEW("p", "Preview");

    /** Symbol of the piece on the board. */
    private final String _symbol;
    /** The full name of the piece. */
    private final String _name;

    PieceType(String symbol, String name) {
        _symbol = symbol;
        _name = name;
    }

    @Override
    public String toString() {
        return _symbol;
    }
}
