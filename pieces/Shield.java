package pieces;

import board.Direction;
import board.Move;
import board.Square;
import game.Player;
import game.PlayerType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static board.Direction.*;

public class Shield extends Piece {
    public Shield(Square sq, Player player) {
        super(sq, player, PieceType.SHIELD, 1);
    }

    /** The Shield piece cannot be promoted. */
    @Override
    public boolean isLegalPromote(Square to) {
        return false;
    }

    /** A static method that helps other pieces determine whether the move is a valid Shield move. */
    static boolean isLegalShieldMove(Move move, Player player) {
        ArrayList<Direction> shieldDir;
        if (player.getPlayerType() == PlayerType.LOWER) {
            shieldDir = new ArrayList<>(Arrays.asList(UP, DOWN, LEFT, RIGHT, UPRIGHT, UPLEFT));
        } else {
            shieldDir = new ArrayList<>(Arrays.asList(UP, DOWN, LEFT, RIGHT, DOWNLEFT, DOWNRIGHT));
        }
        Direction moveDir = move.getFrom().direction(move.getTo());
        int dx = Math.abs(move.getTo().col() - move.getFrom().col());
        int dy = Math.abs(move.getTo().row() - move.getFrom().row());
        return shieldDir.contains(moveDir) && dx <= 1 && dy <= 1;
    }

    /**
     * The Shield cannot move backwards diagonally, relative to its starting position.
     * @return : the list of valid Shield directions.
     */
    @Override
    public List<Direction> getPieceDir() {
        ArrayList<Direction> shieldDir;
        if (getPlayer().getPlayerType() == PlayerType.LOWER) {
            shieldDir = new ArrayList<>(Arrays.asList(UP, DOWN, LEFT, RIGHT, UPRIGHT, UPLEFT));
        } else {
            shieldDir = new ArrayList<>(Arrays.asList(UP, DOWN, LEFT, RIGHT, DOWNLEFT, DOWNRIGHT));
        }
        return shieldDir;
    }
}
