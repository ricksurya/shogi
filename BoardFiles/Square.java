package BoardFiles;

final public class Square {
    /** Return my row position, where 0 is the bottom row. */
    int row() {
        return _row;
    }

    /** Return my column position, where 0 is the leftmost column. */
    int col() {
        return _col;
    }

    /** Return my index position (0-99).  0 represents square a1, and 99
     *  is square j10. */
    int index() {
        return _index;
    }

    /** All possible directions. */
    private static final int[][] DIR = {
            { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 },
            { 0, -1 }, { -1, -1 }, { -1, 0 }, { -1, 1 }
    };


    /** Return the direction (an int as defined in the documentation
     *  for queenMove) of the queen move THIS-TO. */
    int[] direction(Square to) {
        int dx = to.col() - col();
        int dy = to.row() - row();
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
    static boolean exists(int col, int row) {
        return row >= 0 && col >= 0 && row < Board.BOARD_SIZE && col < Board.BOARD_SIZE;
    }

    /** Return the (unique) Square denoting COL ROW. */
    static Square sq(int col, int row) {
        if (!exists(row, col)) {
            throw new IllegalArgumentException("Row or column out of bounds");
        }
        return sq(col * 10 + row);
    }

    /** Return the (unique) Square denoting the position with index INDEX. */
    static Square sq(int index) {
        return SQUARES[index];
    }

    /** Return the (unique) Square denoting the position COL ROW, where
     *  COL ROW is the standard text format for a square (e.g., a4). */
    static Square sq(String col, String row) {
        int c = col.charAt(0) - 'a';
        int r = Integer.parseInt(row) - 1;
        return sq(c, r);
    }

    /** Return the (unique) Square denoting the position in POSN, in the
     *  standard text format for a square (e.g. a4). POSN must be a
     *  valid square designation. */
    static Square sq(String posn) {
        return sq(posn.substring(0, 1), posn.substring(1));
    }

    /** Return the Square with index INDEX. */
    private Square(int index) {
        _index = index;
        _row = index % 10;
        _col = index / 10;
        int s = _col * 10 + _row;
        _str = (char) (_col + 'a') + Integer.toString(_row + 1);
    }

    /** The cache of all created squares, by index. */
    private static final Square[] SQUARES =
            new Square[Board.BOARD_SIZE * Board.BOARD_SIZE];

    static {
        for (int i = Board.BOARD_SIZE * Board.BOARD_SIZE - 1; i >= 0; i -= 1) {
            SQUARES[i] = new Square(i);
        }
    }

    /** My index position. */
    private final int _index;

    /** My row and column (redundant, since these are determined by _index). */
    private final int _row, _col;

    /** My String denotation. */
    private final String _str;

}
