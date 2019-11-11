package pieces;

import board.Board;
import board.Direction;
import board.Move;
import board.Square;
import game.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static board.Direction.*;

/**
 * Class that represents the Notes piece in Shogi.
 * @author ricksurya
 */
public class Notes extends Piece {
    public Notes(Square sq, Player player) {
        super(sq, player, PieceType.NOTES, Board.getBoardSize() - 1);
    }

    /**
     * We are overriding the previous method because the piece can move like a drive if promoted. We must also check
     * that the Notes is not moving over other pieces when arriving at its destination square.
     * @param move : move to be checked
     * @param board : the board of the game
     * @return : true if a valid move
     */
    @Override
    public boolean isLegalPieceMove(Move move, Board board) {
        Square from = move.getFrom();
        Square to = move.getTo();
        Direction moveDir = from.direction(to);
        int dx = Math.abs(to.col() - from.col());
        int dy = Math.abs(to.row() - from.row());
        if (!((getPieceDir().contains(moveDir) && dx <= getPieceRange() && dy <= getPieceRange())
                || (isPromoted() && Drive.isLegalDriveMove(move)))) {
            return false;
        }

        int fromCol = from.col();
        int fromRow = from.row();
        // Check that it is not jumping over any other element.
        for (int i = 1; i < Math.max(dx, dy); i++) {
            if (board.getPieceAt(Square.sq(fromCol + i * moveDir.getDx(), fromRow + i * moveDir.getDy()))
                    != null) {
                return false;
            }
        }
        return true;
    }

    /** The Notes piece by default moves diagonally. */
    @Override
    public List<Direction> getPieceDir() {
        return new ArrayList<>(Arrays.asList(UP, DOWN, LEFT, RIGHT));
    }
}
