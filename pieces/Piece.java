package pieces;

import board.*;
import game.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static game.PlayerType.UPPER;

/**
 * Class to represent a piece in Shogi. Each piece has a unique owner - either UPPER or LOWER. We also keep track of the
 * location of a piece through a variable. Some pieces can also be promoted - this typically enhances their movement
 * abilities.
 * @author ricksurya
 */
public abstract class Piece {
    /** The player that owns the piece. */
    private Player owner;
    /** The type of the piece, ex: Drive. */
    private PieceType type;
    /** True if the piece is promoted, false otherwise. */
    private boolean promoted;
    /** The number of squares in which the piece can move. */
    private int pieceRange;
    /** The location of the piece on the game. */
    private Square location;

    /**
     * Constructor for a piece.
     * @param sq : the location of the piece on the board
     * @param player : the player that owns the piece
     * @param type : type of the piece
     * @param pieceRange : how many squares the piece can move
     */
    public Piece(Square sq, Player player, PieceType type, int pieceRange) {
        this.pieceRange = pieceRange;
        owner = player;
        this.type = type;
        promoted = false;
        location = sq;
    }

    /**
     * Updating the location of the piece
     * @param to : new location
     */
    public final void updateLocation(Square to) {
        location = to;
    }

    /**
     * Returns true if the give move is a valid one, given the piece's capabilities and restrictions on the current
     * board. The board is an input because for some pieces, we must check whether it is skipping over other pieces.
     * @param move : move to be checked
     * @param board : the board of the game
     * @return : true if a valid move
     */
    public boolean isLegalPieceMove(Move move, Board board) {
        Direction moveDir = move.getFrom().direction(move.getTo());
        int dx = Math.abs(move.getTo().col() - move.getFrom().col());
        int dy = Math.abs(move.getTo().row() - move.getFrom().row());
        return getPieceDir().contains(moveDir) && dx <= getPieceRange() && dy <= getPieceRange();
    }

    /**
     * The piece is captured by another player. Hence, we must remove promotions and change the owner.
     * @param p : the player that captured the piece
     */
    public final void capture(Player p) {
        owner = p;
        demote();
    }

    /**
     * Returns a set of all valid moves for the piece, given the board and its current location.
     * @param board : board for the piece to check valid moves
     * @return : set of valid moves.
     */
    public ArrayList<Move> getValidMoves(Board board) {
        ArrayList<Move> validMoves = new ArrayList<>();
        for (int col = 0; col < Board.getBoardSize(); col++) {
            for (int row = 0; row < Board.getBoardSize(); row++) {
                Square sq = Square.sq(col, row);
                if (board.isValidMove(new Move(location, sq, false), owner)) {
                    validMoves.add(new Move(location, sq, false));
                }

            }
        }
        return validMoves;
    }

    /** Promoting the piece. */
    public void promote() {
        promoted = true;
    }

    /** A function to check whether it is legal to promote the piece given square to.
     * @param to : the destination of the square where we check if it's legal to promote
     * @return : true if valid promotion
     */
    public boolean isLegalPromote(Square to) {
        return !promoted && to.row() == owner.getPromotionRow();
    }

    /**
     * A function to check whether it is legal to drop the piece at to.
     * @param to : destination
     * @param board : the board of the game
     * @return : true if it is legal by the piece definitions.
     */
    public boolean isLegalDrop(Square to, Board board) {
        return true;
    }

    /** Remove promotion of the piece. */
    public void demote() {
        promoted = false;
    }

    /** Returns a list of directions in which the piece can move in. */
    public List<Direction> getPieceDir() {
        return Collections.emptyList();
    }

    public final Player getPlayer() {
        return owner;
    }

    public final Square getLocation() {
        return location;
    }

    int getPieceRange() {
        return pieceRange;
    }

    public boolean isPromoted() {
        return promoted;
    }

    public PieceType getType() {
        return type;
    }

    public final String toString() {
        String res = "";
        if (promoted) {
            res += "+";
        }
        res += type.toString();
        return (owner.getPlayerType() == UPPER) ? res.toUpperCase() : res;
    }
}
