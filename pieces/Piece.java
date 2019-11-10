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
    private static int pieceRange;
    private static ArrayList<Direction> pieceDir;
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

    public static boolean isLegalPieceMove(Move move, Board board) {
        Direction moveDir = move.getFrom().direction(move.getTo());
        int dx = Math.abs(move.getTo().col() - move.getFrom().col());
        int dy = Math.abs(move.getTo().row() - move.getFrom().row());
        if (pieceDir.contains(moveDir) && dx < pieceRange && dy < pieceRange) {
            return true;
        }
        return false;
    }

    public final void capture(Player p) {
        owner = p;
        if (promoted) {
            demote();
        }
    }

    /**
     * Returns a set of valid moves for the piece, given the board and its current location.
     * @param board : board for the piece to check valid moves
     * @return : set of valid moves.
     */
    public ArrayList<Move> getValidMoves(Board board) {
        ArrayList<Move> validMoves = new ArrayList<>();
        for (int i = 1; i <= pieceRange; i++) {
            for (Direction dir : pieceDir) {
                int newRow = location.row()+ i * dir.getDy();
                int newCol = location.col()+ i * dir.getDx();
                if (Square.exists(newCol, newRow)
                        && board.isValidMove(new Move(location, Square.sq(newCol, newRow)), owner, false)) {
                    validMoves.add(new Move(location, Square.sq(newCol, newRow)));
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

    public final String toString() {
        String res = "";
        if (promoted) {
            res += "+";
        }
        res += type.toString();
        return (owner.getPlayerType() == UPPER) ? res.toUpperCase() : res;
    }
}
