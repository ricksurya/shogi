package pieces;

import board.Board;
import board.Direction;
import board.Move;
import board.Square;
import game.Player;

import java.util.ArrayList;
import java.util.Arrays;

import static board.Direction.*;

public class Relay extends Piece{
    public Relay(Square sq, Player player, PieceType type) {
        super(sq, player, type, new ArrayList<>(Arrays.asList(UP, UPRIGHT,
                UPLEFT, DOWNRIGHT, DOWNLEFT)), 1);
    }

    @Override
    public boolean isLegalPieceMove(Move move, Board board) {
        Square from = move.getFrom();
        Square to = move.getTo();
        Direction moveDir = from.direction(to);
        int dx = Math.abs(to.col() - from.col());
        int dy = Math.abs(to.row() - from.row());
        if ((getPieceDir().contains(moveDir) && dx < getPieceRange() && dy < getPieceRange())
                || (isPromoted() && Shield.isLegalShieldMove(move))) {
            return true;
        }
        return false;
    }
}
