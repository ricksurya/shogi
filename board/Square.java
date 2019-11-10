package board;

import static board.Direction.*;

final public class Square {
    /** Return my row position, where 0 is the bottom row. */
    public int row() {
        return _row;
    }

    /** Return my column position, where 0 is the leftmost column. */
    public int col() {
        return _col;
    }

    /** All possible directions. */
    private static final Direction[] DIR = {UP, UPRIGHT, RIGHT, DOWNRIGHT, DOWN, DOWNLEFT, LEFT, UPLEFT};


    /** Return the direction as a result of move THIS-TO. If it is not a valid direction, return null. */
    public Direction direction(Square to) {
        int dx = to.col() - col();
        int dy = to.row() - row();
        if (Math.abs(dx) > 0 && Math.abs(dy) > 0 && Math.abs(dx) != Math.abs(dy)) {
            return null;
        }
        if (dx == 0) {
            if (dy > 0) {
                return DIR[0];
            }
            return DIR[4];
        } else if (dy == 0) {
            if (dx > 0) {
                return DIR[2];
            }
            return DIR[6];
        } else if (dx > 0) {
            if (dy > 0) {
                return DIR[1];
            }
            return DIR[3];
        } else {
            if (dy > 0) {
                return DIR[7];
            }
            return DIR[5];
        }
    }

    @Override
    public String toString() {
        return _str;
    }

    /** Return true iff COL ROW is a legal square. */
    public static boolean exists(int col, int row) {
        return row >= 0 && col >= 0 && row < Board.getBoardSize() && col < Board.getBoardSize();
    }

    /** Return the (unique) Square denoting COL ROW. */
    public static Square sq(int col, int row) {
        if (!exists(col, row)) {
            throw new IllegalArgumentException("Row or column out of bounds");
        }
        return SQUARES[col][row];
    }

    /** Return the (unique) Square denoting the position COL ROW, where
     *  COL ROW is the standard text format for a square (e.g., a4). */
    public static Square sq(String col, String row) {
        int c = col.charAt(0) - 'a';
        int r = Integer.parseInt(row) - 1;
        return sq(c, r);
    }

    /** Return the (unique) Square denoting the position in POSN, in the
     *  standard text format for a square (e.g. a4). POSN must be a
     *  valid square designation. */
    public static Square sq(String posn) {
        return sq(posn.substring(0, 1), posn.substring(1));
    }

    /** Return the Square with index INDEX. */
    private Square(int col, int row) {
        _row = row;
        _col = col;
        _str = (char) (_col + 'a') + Integer.toString(_row + 1);
    }

    /** The cache of all created squares, by index. */
    private static final Square[][] SQUARES =
            new Square[Board.getBoardSize()][Board.getBoardSize()];

    static {
        for (int col = Board.BOARD_SIZE - 1; col >= 0; col -= 1) {
            for (int row = Board.BOARD_SIZE - 1; row >= 0; row -= 1) {
                SQUARES[col][row] = new Square(col, row);
            }
        }
    }

    /** My row and column (redundant, since these are determined by _index). */
    private final int _row, _col;

    /** My String denotation. */
    private final String _str;

}
