package game;

public enum PlayerType {
    LOWER("lower"), UPPER("UPPER");

    private final String _name;

    PlayerType(String name) {
        _name = name;
    }

    @Override
    public String toString() {
        return _name;
    }
}
