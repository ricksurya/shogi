package pieces;

import board.Board;
import board.Direction;
import board.Move;
import board.Square;
import game.Player;

import java.util.ArrayList;
import java.util.Arrays;

import static board.Direction.*;

public class Notes extends Piece {
    public Notes(Square sq, Player player, PieceType type) {
        super(sq, player, type, new ArrayList<>(Arrays.asList(UP, DOWN, LEFT, RIGHT)),
                Board.getBoardSize() - 1);
    }

    @Override
    public boolean isLegalPieceMove(Move move, Board board) {
        Square from = move.getFrom();
        Square to = move.getTo();
        Direction moveDir = from.direction(to);
        int dx = Math.abs(to.col() - from.col());
        int dy = Math.abs(to.row() - from.row());
        if (!((getPieceDir().contains(moveDir) && dx < getPieceRange() && dy < getPieceRange())
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
}
