package Pieces;

public enum PieceType {

    DRIVE("d", "Drive"), NOTES("n", "Notes"), GOVERNANCE("g", "Governance"),
    SHIELD("s", "Shield"), RELAY("r", "Relay"), PREVIEW("p", "Preview");

    private final String _symbol;
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
