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
 * Class that represents the Governance piece in Shogi.
 * @author ricksurya
 */
public class Governance extends Piece {
    public Governance(Square sq, Player player) {
        super(sq, player, PieceType.GOVERNANCE,Board.getBoardSize() - 1);
    }

    /**
     * For a Governance piece, we must check whether it is promoted. If it is promoted, a king move is valid. We must
     * also check that the Governance piece is not jumping over other pieces on the board.
     * @param move : move to be checked
     * @param board : the board of the game
     * @return : true if a valid Governance move
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

    /**
     * The Governance piece only moves horizontally and vertically.
     * @return : the list of directions in which the Governance piece can move in.
     */
    @Override
    public List<Direction> getPieceDir() {
        return new ArrayList<>(Arrays.asList(UPRIGHT, UPLEFT, DOWNRIGHT, DOWNLEFT));
    }
}
