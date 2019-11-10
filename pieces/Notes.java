package pieces;

import board.Square;
import game.Player;

import java.util.ArrayList;
import java.util.Arrays;

import static board.Direction.*;

public class Notes extends Piece {
    public Notes(Square sq, Player player, PieceType type) {
        super(sq, player, type);
    }

    @Override
    public ArrayList getPieceDir() {
        return new ArrayList<>(Arrays.asList(UP, RIGHT, DOWN, LEFT));
    }

}
