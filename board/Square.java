package board;

import static board.Direction.*;

/**
 * The square class represents a location on the Shogi board. Although we do not keep track of what is on a Square,
 * the main purpose of the Square is to represent a location for purposes of keeping track of movements of pieces or
 * drops.
 * @author ricksurya
 */
final public class Square {
    /** Return the row position, where 0 is the bottom row (this represents row 1). */
    public int row() {
        return _row;
    }

    /** Return the column position, where 0 is the leftmost column (this represents column a). */
    public int col() {
        return _col;
    }

    /** All possible directions in the game of Shogi. */
    private static final Direction[] DIR = {UP, UPRIGHT, RIGHT, DOWNRIGHT, DOWN, DOWNLEFT, LEFT, UPLEFT};


    /** Return the direction as a result of move the current square to TO.
     * If it is not a valid direction, return null. */
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

    /** Returns true if the square is in bounds. */
    public static boolean exists(int col, int row) {
        return row >= 0 && col >= 0 && row < Board.getBoardSize() && col < Board.getBoardSize();
    }

    /** Return the Square with position COL ROW. */
    public static Square sq(int col, int row) {
        if (!exists(col, row)) {
            throw new IllegalArgumentException("Row or column out of bounds");
        }
        return SQUARES[col][row];
    }

    /** Return the Square given the input as a string of col, row (ex: a, 4). */
    public static Square sq(String col, String row) {
        int c = col.charAt(0) - 'a';
        int r = Integer.parseInt(row) - 1;
        return sq(c, r);
    }

    /** Return the Square given the input as a single string (ex: a4). */
    public static Square sq(String posn) {
        return sq(posn.substring(0, 1), posn.substring(1));
    }

    /** Return the Square with the given col, row. */
    private Square(int col, int row) {
        _row = row;
        _col = col;
        _str = (char) (_col + 'a') + Integer.toString(_row + 1);
    }

    /** All possible squares by col, row. */
    private static final Square[][] SQUARES =
            new Square[Board.getBoardSize()][Board.getBoardSize()];

    static {
        for (int col = Board.BOARD_SIZE - 1; col >= 0; col -= 1) {
            for (int row = Board.BOARD_SIZE - 1; row >= 0; row -= 1) {
                SQUARES[col][row] = new Square(col, row);
            }
        }
    }

    /** Row and column of the square. */
    private final int _row, _col;

    /** The string form of the square. */
    private final String _str;

}
