package pieces;

import board.*;
import game.Player;

import java.util.ArrayList;

import static game.PlayerType.UPPER;

/**
 * Class to represent a piece in Shogi.
 * @author ricksurya
 */
public abstract class Piece {

    private Player owner;
    private PieceType type;
    private boolean promoted;
    private int pieceRange;
    private ArrayList<Direction> pieceDir;
    private Square location;

    public Piece(Square sq, Player player, PieceType type, ArrayList<Direction> dirs, int pieceRange) {
        pieceDir = dirs;
        this.pieceRange = pieceRange;
        owner = player;
        this.type = type;
        promoted = false;
        location = sq;
    }

    public final void updateLocation(Square to) {
        location = to;
    }

    public boolean isLegalPieceMove(Move move, Board board) {
        Direction moveDir = move.getFrom().direction(move.getTo());
        int dx = Math.abs(move.getTo().col() - move.getFrom().col());
        int dy = Math.abs(move.getTo().row() - move.getFrom().row());
        if (getPieceDir().contains(moveDir) && dx <= getPieceRange() && dy <= getPieceRange()) {
            return true;
        }
        return false;
    }

    public final void capture(Player p) {
        owner = p;
        demote();
    }

    /**
     * Returns a set of valid moves for the piece, given the board and its current location.
     * @param board : board for the piece to check valid moves
     * @return : set of valid moves.
     */
    public ArrayList<Move> getValidMoves(Board board) {
        ArrayList<Move> validMoves = new ArrayList<>();
        for (int row = 0; row < Board.getBoardSize(); row++) {
            for (int col = 0; col < Board.getBoardSize(); col++) {
                Square sq = Square.sq(col, row);
                if (board.isValidMove(new Move(location, sq, false), owner)) {
                    validMoves.add(new Move(location, sq, false));
                }

            }
        }
        return validMoves;
    }

    public void promote() {
        promoted = true;
    }

    public boolean isLegalPromote(Square to) {
        if (!promoted && to.row() == owner.getPromotionRow()) {
            return true;
        }
        return false;
    }

    public boolean isLegalDrop(Square to, Board board) {
        return true;
    }

    public void demote() {
        promoted = false;
    }

    public final Player getPlayer() {
        return owner;
    }

    public final Square getLocation() {
        return location;
    }

    public ArrayList<Direction> getPieceDir() {
        return pieceDir;
    }

    public int getPieceRange() {
        return pieceRange;
    }

    public boolean isPromoted() {
        return promoted;
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
