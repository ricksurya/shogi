package pieces;

import board.Board;
import board.Direction;
import board.Move;
import board.Square;
import game.Player;
import game.PlayerType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static board.Direction.*;

public class Relay extends Piece{
    public Relay(Square sq, Player player) {
        super(sq, player, PieceType.RELAY, 1);
    }

    @Override
    public boolean isLegalPieceMove(Move move, Board board) {
        Square from = move.getFrom();
        Square to = move.getTo();
        Direction moveDir = from.direction(to);
        int dx = Math.abs(to.col() - from.col());
        int dy = Math.abs(to.row() - from.row());
        if (isPromoted() && Shield.isLegalShieldMove(move, getPlayer())) {
            return true;
        }
        if (!isPromoted() && getPieceDir().contains(moveDir) && dx <= getPieceRange() && dy <= getPieceRange()) {
            return true;
        }
        return false;
    }

    @Override
    public List<Direction> getPieceDir() {
        ArrayList<Direction> relayDir;
        if (getPlayer().getPlayerType() == PlayerType.LOWER) {
            relayDir = new ArrayList<>(Arrays.asList(UP, UPRIGHT, UPLEFT, DOWNRIGHT, DOWNLEFT));
        } else {
            relayDir = new ArrayList<>(Arrays.asList(DOWN, DOWNLEFT, DOWNRIGHT, UPLEFT, UPRIGHT));
        }
        return relayDir;
    }
}
