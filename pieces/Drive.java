package pieces;

import board.Board;
import board.Direction;
import board.Move;
import board.Square;
import game.Player;

import static board.Direction.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class that represents the Drive (King)
 * @author ricksurya
 */
public class Drive extends Piece {
    public Drive(Square sq, Player player) {
        super(sq, player, PieceType.DRIVE, new ArrayList<>(Arrays.asList(UP, DOWN, LEFT, RIGHT, UPRIGHT,
                UPLEFT, DOWNRIGHT, DOWNLEFT)), 1);
    }

    @Override
    public boolean isLegalPromote(Square to) {
        return false;
    }

    public static boolean isLegalDriveMove(Move move) {
        Direction moveDir = move.getFrom().direction(move.getTo());
        int dx = Math.abs(move.getTo().col() - move.getFrom().col());
        int dy = Math.abs(move.getTo().row() - move.getFrom().row());
        if (getPieceDir().contains(moveDir) && dx < getPieceRange() && dy < getPieceRange()) {
            return true;
        }
        return false;
    }

}
