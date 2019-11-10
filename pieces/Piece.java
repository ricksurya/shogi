package pieces;

import board.*;
import game.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static board.Direction.*;
import static game.PlayerType.UPPER;

public class Piece {

    private Player owner;
    private PieceType type;
    private boolean promoted;
    private final int PIECE_RANGE = Board.getBoardSize();
    private static Set<Direction> PIECE_DIR = new HashSet<>(Arrays.asList(UP, UPRIGHT, RIGHT, DOWNRIGHT, DOWN,
            DOWNLEFT, LEFT, UPLEFT));
    private Square location;

    public Piece(Square sq, Player player, PieceType type) {
        owner = player;
        this.type = type;
        promoted = false;
        location = sq;
    }

    public void updateLocation(Square to) {
        location = to;
    }

    public boolean isLegalMove(Move move, Board board) {
        if (move.getFrom() == move.getTo()) {
            return false;
        }
        if (board.getPieceAt(move.getTo()).getPlayer() == owner) {
            return false;
        }
        return isLegalPieceMove(move);
    }

    private boolean isLegalPieceMove(Move move) {
        Direction moveDir = move.getFrom().direction(move.getTo());
        int dx = Math.abs(move.getTo().col() - move.getFrom().col());
        int dy = Math.abs(move.getTo().row() - move.getFrom().row());
        if (PIECE_DIR.contains(moveDir) && dx < PIECE_RANGE && dy < PIECE_RANGE) {
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
    public Set<Move> getValidMoves(Board board) {
        Set<Move> validMoves = new HashSet<>();
        for (int i = 1; i <= PIECE_RANGE; i++) {
            for (Direction dir : PIECE_DIR) {
                int newRow = location.row()+ i * dir.getDy();
                int newCol = location.col()+ i * dir.getDx();
                if (Square.exists(newCol, newRow) && board.getPieceAt(Square.sq(newCol, newRow)).getPlayer() != owner) {
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
