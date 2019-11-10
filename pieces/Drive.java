package pieces;

import board.Direction;
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
    public Drive(Square sq, Player player, PieceType type) {
        super(sq, player, type);
    }

    @Override
    public boolean isLegalPromote(Square to) {
        return false;
    }

    @Override
    public static int getPieceRange() {
        return 1;
    }
}
