package board;

import game.Player;
import pieces.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to represent Box Shogi board
 */
public class Board {

    Piece[][] board;
    final static int BOARD_SIZE = 5;
    private Map<Player, Square> driver_positions;
    private final static int LOWER_PROMOTION_ROW = BOARD_SIZE - 1;
    private final static int UPPER_PROMOTION_ROW = 0;

    public Board() {
        board = new Piece[BOARD_SIZE][BOARD_SIZE];
        driver_positions = new HashMap<>();
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

    public boolean isValidMove(Move move, Player currPlayer, boolean promote) {
        //TODO: Check if the move will cause currPlayer to be in a Check.
        Square to = move.getTo();
        Square from = move.getFrom();
        if (from == to || getPieceAt(from) == null || getPieceAt(from).getPlayer() != currPlayer ||
                getPieceAt(to).getPlayer() == currPlayer) {
            return false;
        }

        if (promote && !getPieceAt(from).isLegalPromote(to)) {
            return false;
        }
        return true;
    }

    public void makeMove(Move move, Player currPlayer, boolean promote) {
        Square to = move.getTo();
        Square from = move.getFrom();
        Piece p = getPieceAt(from);

        if (isOccupied(to)) {
            Piece opponentPiece = getPieceAt(to);
            opponentPiece.capture(currPlayer);
            currPlayer.addCapturedPiece(opponentPiece);
            getOpponent(currPlayer).removeCurrPiece(opponentPiece);
            removePieceAt(to);
        }

        if (promote || p instanceof Preview && to.row() == currPlayer.getPromotionRow()) {
            p.promote();
        }

        if (p instanceof Drive) {
            updateDrivePosition(p, to);
        }

        placePieceAt(p, to);
        removePieceAt(from);
        p.updateLocation(to);
    }

    public boolean isValidDrop(Piece p, Player currPlayer, Square to) {
        if (!currPlayer.getCapturedPieces().contains(p) || getPieceAt(to) != null || !p.isLegalDrop(to, this)) {
            return false;
        }
        return true;
    }

    public void makeDrop(Piece p, Square to, Player currPlayer) {
        placePieceAt(p, to);
        currPlayer.dropPiece(p);
        p.updateLocation(to);
    }

    private void updateDrivePosition(Piece drive, Square to) {
        driver_positions.put(drive.getPlayer(), to);
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

    private void placePieceAt(Piece p, Square sq) {
        board[sq.col()][sq.row()] = p;
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

    public static int getBoardSize() {
        return BOARD_SIZE;
    }

    public Player getOpponent(Player player) {
        Player opponent = null;
        for (Player p : driver_positions.keySet()) {
            if (p != player) {
                opponent =  p;
                break;
            }
        }
        assert(opponent != null);
        return opponent;
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

