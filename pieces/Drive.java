package pieces;

import board.Direction;
import board.Move;
import board.Square;
import game.Player;

import static board.Direction.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class that represents the Drive (King) in Shogi.
 * @author ricksurya
 */
public class Drive extends Piece {
    public Drive(Square sq, Player player) {
        super(sq, player, PieceType.DRIVE, 1);
    }

    @Override
    public boolean isLegalPromote(Square to) {
        return false;
    }

    /**
     * The drive can move in all directions but only by 1 square. This is a static function that helps check whether the
     * move is legal or not, to be used by promoted pieces that can move like the Drive.
     * @param move : move to be checked
     * @return : true if a valid Drive move
     */
    static boolean isLegalDriveMove(Move move) {
        List<Direction> driveDir = new ArrayList<>(Arrays.asList(UP, DOWN, LEFT, RIGHT, UPRIGHT,
                UPLEFT, DOWNRIGHT, DOWNLEFT));
        Direction moveDir = move.getFrom().direction(move.getTo());
        int dx = Math.abs(move.getTo().col() - move.getFrom().col());
        int dy = Math.abs(move.getTo().row() - move.getFrom().row());
        return driveDir.contains(moveDir) && dx <= 1 && dy <= 1;
    }

    /**
     * The drive can move in all directions.
     * @return : list of directions the drive can move in
     */
    @Override
    public List<Direction> getPieceDir() {
        return new ArrayList<>(Arrays.asList(UP, DOWN, LEFT, RIGHT, UPRIGHT, UPLEFT, DOWNRIGHT, DOWNLEFT));
    }
}
