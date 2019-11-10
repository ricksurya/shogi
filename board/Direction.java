package board;

public enum Direction {
    UP(0,1), UPRIGHT(1, 1), RIGHT(1, 0), DOWNRIGHT(1, -1), DOWN(0, -1),
    DOWNLEFT(-1, -1), LEFT(-1 ,0), UPLEFT(-1 ,1);

    private final int _dx;
    private final int _dy;

    Direction(int dx, int dy) {
        _dx = dx;
        _dy = dy;
    }

    public int getDx() {
        return _dx;
    }

    public int getDy() {
        return _dy;
    }
}
