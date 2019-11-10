package board;

import game.Player;
import pieces.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to represent Box Shogi board
 * @author ricksurya
 */
public class Board {

    Piece[][] board;
    final static int BOARD_SIZE = 5;
    private Map<Player, Square> driver_positions;

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

    /**
     * Checks if the given move is valid. A move is valid if: it is not in the same position, it is not moving a NULL
     * piece, the piece is currPlayer's, it is not capturing currPlayer's piece, the promotion is valid, it is a legal
     * piece move, and the move does not cause currPlayer to be in a check position.
     * @param move : move to be checked
     * @param currPlayer : the moving player
     * @param promote : whether the move is promoting the piece
     * @return : true if valid
     */
    public boolean isValidMove(Move move, Player currPlayer, boolean promote) {
        Square to = move.getTo();
        Square from = move.getFrom();
        Piece p = getPieceAt(from);
        if (from == to || p == null || p.getPlayer() != currPlayer ||
                getPieceAt(to).getPlayer() == currPlayer || !p.isLegalPieceMove(move, this)) {
            return false;
        }

        if (promote && !p.isLegalPromote(to)) {
            return false;
        }

        // Check if the move causes currPlayer to be in a check position
        Piece oppPiece = getPieceAt(move.getTo());
        if (oppPiece != null) {
            removePieceAt(move.getTo());
        }
        removePieceAt(move.getFrom());
        placePieceAt(p, move.getTo());
        if(isCheck(currPlayer)) {
            return false;
        }
        placePieceAt(p, move.getFrom());
        placePieceAt(oppPiece, move.getTo());

        return true;
    }

    /**
     * Executes the move by currPlayer. The game must first check whether the move is valid before executing the move.
     * @param move : move to be executed
     * @param currPlayer : the player who is moving
     * @param promote : whether the player wishes to promote a piece
     */
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

    /**
     * Checks whether the drop is valid by currPlayer. A drop is valid if: the target square is empty, currPlayer has
     * the piece for dropping, and if the target square is legal for the piece.
     * @param drop : the drop to be checked
     * @param currPlayer : player to drop
     * @return : true if drop is valid
     */
    public boolean isValidDrop(Drop drop, Player currPlayer) {
        Piece p = drop.getPiece();
        Square to = drop.getTo();
        if (!currPlayer.getCapturedPieces().contains(p) || getPieceAt(to) != null || !p.isLegalDrop(to, this)) {
            return false;
        }
        return true;
    }

    /**
     * Executes the drop.
     * @param drop : drop to be executed
     * @param currPlayer : player executing the drop
     */
    public void makeDrop(Drop drop, Player currPlayer) {
        Piece p = drop.getPiece();
        Square to = drop.getTo();
        placePieceAt(p, to);
        currPlayer.dropPiece(p);
        p.updateLocation(to);
    }

    /**
     * Checks if the player is in a checked position, given the current situation of the board.
     * @param player : we are checking if this player is checked by the opponent.
     * @return true if the player is checked.
     */
    public boolean isCheck(Player player) {
        Player opponent = getOpponent(player);
        Square driverPosition = driver_positions.get(player);
        for (Piece p : opponent.getCurrPieces()) {
            if (isValidMove(new Move(p.getLocation(), driverPosition), opponent, false)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the player is in checkmate.
     * @param player : player to be checked
     * @return : true if player is in checkmate
     */
    public boolean isCheckMate(Player player) {
        if (!isCheck(player)) {
            return false;
        }
        return getUncheckMoves(player).size() == 0;
    }

    /**
     * Returns all available moves and drops to uncheck the player. This function should only be called if the player
     * is checked.
     * @param player : player who is in check
     * @return : list of moves to uncheck
     */
    public ArrayList<Move> getUncheckMoves(Player player) {
        ArrayList<Move> moves = new ArrayList<>();
        for (Piece piece : player.getCurrPieces()) {
            for (Move move : piece.getValidMoves(this)) {
                if (canMoveUncheck(move, player)) {
                    moves.add(move);
                }
            }
        }

        for (Piece piece : player.getCapturedPieces()) {
            for (int r = 0; r < BOARD_SIZE; r++) {
                for (int c = 0; c < BOARD_SIZE; c++) {
                    Drop drop = new Drop(Square.sq(c, r), piece);
                    if (isValidDrop(drop, player) && canDropUncheck(drop, player)) {
                        moves.add(drop);
                    }
                }
            }
        }
        return moves;
    }

    /**
     * This function checks whether move can cause the player's Drive to be un-checked.
     * @param move : move to be checked
     * @param player : player that is currently in check
     * @return : true if the move can uncheck the player
     */
    private boolean canMoveUncheck(Move move, Player player) {
        if (!isValidMove(move, player, false)) {
            return false;
        }
        boolean res;
        Player opponent = getOpponent(player);
        Piece oppPiece = getPieceAt(move.getTo());
        if (oppPiece != null) {
            removePieceAt(move.getTo());
        }
        Piece p = getPieceAt(move.getFrom());
        removePieceAt(move.getFrom());
        placePieceAt(p, move.getTo());
        res = !isCheck(player);
        placePieceAt(p, move.getFrom());
        placePieceAt(oppPiece, move.getTo());
        return res;
    }

    /**
     * This function checks whether a drop can cause the player's Drive to be un-checked.
     * @param drop : drop to be checked
     * @param player : player that is currently in check
     * @return : true if the move can uncheck the player
     */
    private boolean canDropUncheck(Drop drop, Player player) {
        if (!isValidDrop(drop, player)) {
            return false;
        }
        placePieceAt(drop.getPiece(), drop.getTo());
        boolean res = !isCheck(player);
        removePieceAt(drop.getTo());
        return res;
    }

    /**
     * Update the drive position to keep track of its location.
     * @param drive : drive to be updated
     * @param to : new location of the drive
     */
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

    void removePieceAt(Square sq) {
        board[sq.col()][sq.row()] = null;
    }

    void placePieceAt(Piece p, Square sq) {
        board[sq.col()][sq.row()] = p;
    }

    public static int getBoardSize() {
        return BOARD_SIZE;
    }

    /**
     * Gets the opponent of the player. Ex: an input of UPPER would return LOWER player.
     * @return : opponent player
     */
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

