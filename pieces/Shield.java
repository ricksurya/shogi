package pieces;

import board.Board;
import board.Direction;
import board.Move;
import board.Square;
import game.Player;

import java.util.ArrayList;
import java.util.Arrays;

import static board.Direction.*;

public class Shield extends Piece {
    public Shield(Square sq, Player player) {
        super(sq, player, PieceType.SHIELD, new ArrayList<>(Arrays.asList(UP, DOWN, LEFT, RIGHT, UPRIGHT,
                UPLEFT)), 1);
    }

    @Override
    public boolean isLegalPromote(Square to) {
        return false;
    }

    public static boolean isLegalShieldMove(Move move) {
        ArrayList<Direction> shieldDir = new ArrayList<>(Arrays.asList(UP, DOWN, LEFT, RIGHT, UPRIGHT, UPLEFT));
        Direction moveDir = move.getFrom().direction(move.getTo());
        int dx = Math.abs(move.getTo().col() - move.getFrom().col());
        int dy = Math.abs(move.getTo().row() - move.getFrom().row());
        if (shieldDir.contains(moveDir) && dx <= 1 && dy <= 1) {
            return true;
        }
        return false;
    }
}
