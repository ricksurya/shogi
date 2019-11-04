package BoardFiles;

import Pieces.*;

import java.util.HashSet;

/**
 * Class to represent Box Shogi board
 */
public class Board {

    Piece[][] board;
    final static int BOARD_SIZE = 5;
    private int _turn;
    HashSet<Piece> capturedP0;
    HashSet<Piece> capturedP1;

    public Board() {
        board = new Piece[BOARD_SIZE][BOARD_SIZE];
        _turn = 0;
    }

    /* Print board */
    public String toString() {
        String[][] pieces = new String[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Piece curr = (Piece) board[col][row];
                pieces[col][row] = this.isOccupied(col, row) ? board[col][row].toString() : "";
            }
        }
        return stringifyBoard(pieces);
    }

    public void makeMove(Square from, Square to) {
        if (isOccupied(to)) {
            if (_turn == 0) {
                capturedP0.add(getPieceAt(to));
            } else {
                capturedP1.add(getPieceAt(to));
            }
            getPieceAt(to).changePlayer();
            removePieceAt(to);
        }
        Piece piece = getPieceAt(from);
        board[to.col()][to.row()] = piece;
        removePieceAt(from);
        piece.updatePosition(to);
        _turn = (_turn + 1) % 2;
    }

    private boolean isOccupied(Square sq) {
        return getPieceAt(sq) != null;
    }

    private boolean isOccupied(int row, int col) {
        return board[col][row] != null;
    }

    public Piece getPieceAt(Square sq) {
        return board[sq.col()][sq.row()];
    }

    private void removePieceAt(Square sq) {
        board[sq.col()][sq.row()] = null;
    }


    private String stringifyBoard(String[][] board) {
        String str = "";

        for (int row = board.length - 1; row >= 0; row--) {

            str += Integer.toString(row + 1) + " |";
            for (int col = 0; col < board[row].length; col++) {
                str += stringifySquare(board[col][row]);
            }
            str += System.getProperty("line.separator");
        }

        str += "    a  b  c  d  e" + System.getProperty("line.separator");

        return str;
    }

    private String stringifySquare(String sq) {
        switch (sq.length()) {
            case 0:
                return "__|";
            case 1:
                return " " + sq + "|";
            case 2:
                return sq + "|";
        }

        throw new IllegalArgumentException("Board.Board must be an array of strings like \"\", \"P\", or \"+P\"");
    }
}

