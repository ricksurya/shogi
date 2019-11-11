package pieces;

import board.Board;
import board.Direction;
import board.Move;
import board.Square;
import game.Player;
import game.PlayerType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static board.Direction.*;

/**
 * Class that represents a Preview piece in Shogi.
 * @author ricksurya
 */
public class Preview extends Piece {
    public Preview(Square sq, Player player) {
        super(sq, player, PieceType.PREVIEW, 1);
    }

    /**
     * For the piece, we must check whether it is promoted. If it is promoted it can move like a Shield piece.
     * @param move : move to be checked
     * @param board : the board of the game
     * @return : true if a valid Preview move
     */
    @Override
    public boolean isLegalPieceMove(Move move, Board board) {
        Square from = move.getFrom();
        Square to = move.getTo();
        Direction moveDir = from.direction(to);
        int dx = Math.abs(to.col() - from.col());
        int dy = Math.abs(to.row() - from.row());
        return (getPieceDir().contains(moveDir) && dx <= getPieceRange() && dy <= getPieceRange())
                || (isPromoted() && Shield.isLegalShieldMove(move, getPlayer()));
    }

    /**
     * The function to check whether a drop is legal for the Preview is more interesting. We must check that the drop
     * does not result in an immediate checkmate against the owner's opponent, and that we are not dropping it on a
     * column with another Preview with the same owner.
     * @param to : destination
     * @param board : the board of the game
     * @return : true if a valid Preview drop
     */
    @Override
    public boolean isLegalDrop(Square to, Board board) {
        if (to.row() == getPlayer().getPromotionRow() || board.getPieceAt(to) != null) {
            return false;
        }

        // Check if another Preview with the same owner lies on the same column.
        Player player = getPlayer();
        for (int row = 0; row < Board.getBoardSize(); row++) {
            Piece p = board.getPieceAt(Square.sq(to.col(), row));
            if (p instanceof Preview && p.getPlayer() == player && !p.isPromoted()) {
                return false;
            }
        }
        // Check that it doesn't result in a checkmate.
        board.placePieceAt(this, to);
        updateLocation(to);
        getPlayer().addCurrPiece(this);
        Player opponent = board.getOpponent(player);
        boolean res = !board.isCheckMate(opponent);
        board.removePieceAt(to);
        getPlayer().removeCurrPiece(this);
        return res;
    }

    /**
     * The preview can only move UP relative to it's starting position. That means of player UPPER it can only move
     * DOWN, and for LOWER it can only move UP.
     * @return : list of directions in which the Preview can move in.
     */
    @Override
    public List<Direction> getPieceDir() {
        ArrayList<Direction> previewDir;
        if (getPlayer().getPlayerType() == PlayerType.LOWER) {
            previewDir = new ArrayList<>(Collections.singletonList(UP));
        } else {
            previewDir = new ArrayList<>(Collections.singletonList(DOWN));
        }
        return previewDir;
    }
}
