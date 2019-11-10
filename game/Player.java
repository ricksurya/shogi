package game;

import board.Board;
import pieces.Piece;

import java.util.ArrayList;

import static game.PlayerType.LOWER;

public class Player {
    private Controller _controller;
    private final PlayerType _playerType;
    private ArrayList<Piece> capturedPieces;
    private ArrayList<Piece> currPieces;
    private int promotion_row;

    public Player(PlayerType playerType, Controller controller) {
        _playerType = playerType;
        _controller = controller;
        currPieces = new ArrayList<>();
        capturedPieces = new ArrayList<>();
        promotion_row = (playerType == LOWER) ? Board.getBoardSize() - 1 : 0;
    }

    public void addCapturedPiece(Piece p) {
        capturedPieces.add(p);
    }

    public void dropPiece(Piece p) {
        capturedPieces.remove(p);
        currPieces.add(p);
    }

    public Piece getCapturedPiece(String symbol) {
        for (Piece piece : capturedPieces) {
            if (piece.getType().toString().equals(symbol)) {
                return piece;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return _playerType.toString();
    }

    public void removeCurrPiece(Piece p) {
        currPieces.remove(p);
    }

    public void addCurrPiece(Piece p) {
        currPieces.add(p);
    }

    public ArrayList<Piece> getCapturedPieces() {
        return capturedPieces;
    }

    public ArrayList<Piece> getCurrPieces() {
        return currPieces;
    }

    public PlayerType getPlayerType() {
        return _playerType;
    }

    public int getPromotionRow() {
        return promotion_row;
    }
}
