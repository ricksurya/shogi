package game;

import board.Board;
import pieces.Piece;

import java.util.ArrayList;

import static game.PlayerType.LOWER;

/**
 * This class represents a player in the game of Shogi.
 * @author ricksurya
 */
public class Player {
    /** The controller of which the player belongs to. */
    private Controller _controller;
    /** The type of player. Can only be UPPER or lower. */
    private final PlayerType _playerType;
    /** The list of captured pieces for the player; these pieces can be dropped onto the board. */
    private ArrayList<Piece> capturedPieces;
    /** A list of player's pieces currently on the board of the game. */
    private ArrayList<Piece> currPieces;
    /** The row where promotion is valid for the player. It is the topmost row for LOWER, and the lowest row for UPPER. */
    private int promotion_row;

    /**
     * Constructor for a player
     * @param playerType : either UPPER or LOWER
     * @param controller : the controller for the player
     */
    public Player(PlayerType playerType, Controller controller) {
        _playerType = playerType;
        _controller = controller;
        currPieces = new ArrayList<>();
        capturedPieces = new ArrayList<>();
        promotion_row = (playerType == LOWER) ? Board.getBoardSize() - 1 : 0;
    }

    /**
     * Adds a captured piece to the list of pieces held by player.
     * @param p : the captured piece
     */
    public void addCapturedPiece(Piece p) {
        capturedPieces.add(p);
    }

    /**
     * Drops a piece from the list of captured pieces of the player. This adds it to the current pieces list.
     * @param p : the piece to be dropped
     */
    public void dropPiece(Piece p) {
        capturedPieces.remove(p);
        currPieces.add(p);
    }

    /**
     * A helper function to parse whether the player has a piece given its symbol. Returns the piece if present, else
     * returns null
     * @param symbol : the symbol of the piece
     * @return : the Piece given the symbol
     */
    Piece getCapturedPiece(String symbol) {
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
