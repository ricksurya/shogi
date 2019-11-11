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
import static board.Direction.UPRIGHT;

public class Preview extends Piece {
    public Preview(Square sq, Player player) {
        super(sq, player, PieceType.PREVIEW, 1);
    }

    @Override
    public boolean isLegalPieceMove(Move move, Board board) {
        Square from = move.getFrom();
        Square to = move.getTo();
        Direction moveDir = from.direction(to);
        int dx = Math.abs(to.col() - from.col());
        int dy = Math.abs(to.row() - from.row());
        if ((getPieceDir().contains(moveDir) && dx <= getPieceRange() && dy <= getPieceRange())
                || (isPromoted() && Shield.isLegalShieldMove(move, getPlayer()))) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isLegalDrop(Square to, Board board) {
        if (to.row() == getPlayer().getPromotionRow() || board.getPieceAt(to) != null) {
            return false;
        }

        Player player = getPlayer();
        for (int row = 0; row < Board.getBoardSize(); row++) {
            Piece p = board.getPieceAt(Square.sq(to.col(), row));
            if (p != null && p instanceof Preview && p.getPlayer() == player && !p.isPromoted()) {
                return false;
            }
        }

        board.placePieceAt(this, to);
        updateLocation(to);
        getPlayer().addCurrPiece(this);
        Player opponent = board.getOpponent(player);
        boolean res = !board.isCheckMate(opponent);
        board.removePieceAt(to);
        getPlayer().removeCurrPiece(this);
        return res;
    }

    @Override
    public List<Direction> getPieceDir() {
        ArrayList<Direction> previewDir;
        if (getPlayer().getPlayerType() == PlayerType.LOWER) {
            previewDir = new ArrayList<>(Arrays.asList(UP));
        } else {
            previewDir = new ArrayList<>(Arrays.asList(DOWN));
        }
        return previewDir;
    }
}
