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
    public Shield(Square sq, Player player, PieceType type) {
        super(sq, player, type, new ArrayList<>(Arrays.asList(UP, DOWN, LEFT, RIGHT, UPRIGHT,
                UPLEFT)), 1);
    }

    @Override
    public boolean isLegalPromote(Square to) {
        return false;
    }

    public static boolean isLegalShieldMove(Move move) {
        Direction moveDir = move.getFrom().direction(move.getTo());
        int dx = Math.abs(move.getTo().col() - move.getFrom().col());
        int dy = Math.abs(move.getTo().row() - move.getFrom().row());
        if (getPieceDir().contains(moveDir) && dx < getPieceRange() && dy < getPieceRange()) {
            return true;
        }
        return false;
    }
}
