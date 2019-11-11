package game;

import board.Board;
import board.Drop;
import board.Move;
import pieces.Piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Reporter {

    /**
     * Prints the current state of the board
     * @param board : the board to be printed
     */
    void reportBoard(Board board) {
        System.out.println(board.toString());
    }

    /**
     * Reports a move by player. The board and captures will subsequently be printed.
     * @param player : player that executes the move
     * @param move : the move to be executed
     * @param board : board for the move
     * @param upper : upper player
     * @param lower : lower player
     */
    void reportMove(Player player, Move move, Board board, Player upper, Player lower) {
        String s = player.toString() + " player action: " + move.toString();
        if (move.isPromote()) {
            s += " promote";
        }
        System.out.println(s);
        reportBoard(board);
        reportCaptures(upper, lower);
    }

    /**
     * Reports a drop by a player. The board and captures will subsequently be printed.
     * @param player : player that executes the drop
     * @param drop : the drop to be executed
     * @param board : board for the drop
     * @param upper : upper player
     * @param lower : lower player
     */
    void reportDrop(Player player, Drop drop, Board board, Player upper, Player lower) {
        System.out.println(player.toString() + " player action: " + drop.toString().toLowerCase());
        reportBoard(board);
        reportCaptures(upper, lower);
    }

    /**
     * Reports an illegal move, resulting in the inputted player winning.
     * @param winner : the player that wins as a result
     */
    void reportIllegalMove(Player winner) {
        System.out.println(winner.toString() + " player wins.  Illegal move.");
    }

    /**
     * Reports if the inputted player is in a check position. It will print out the list of available moves to get
     * out of the check position
     * @param inCheck : player in a check position
     * @param moves : the moves to get out of check
     */
    void reportCheck(Player inCheck, List<Move> moves) {
        System.out.println(inCheck.toString() + " player is in check!");
        System.out.println("Available moves:");
        ArrayList<String> moveStrings = new ArrayList<>();
        for (Move move : moves) {
            if (move instanceof Drop) {
                moveStrings.add(move.toString().toLowerCase());
            } else {
                moveStrings.add(move.toString());
            }
        }
        Collections.sort(moveStrings);
        for (String move : moveStrings) {
            System.out.println(move);
        }
    }

    /**
     * Reports a checkmate that ends the game.
     * @param winner : player who wins
     */
    void reportCheckMate(Player winner) {
        System.out.println(winner.toString() + " player wins.  Checkmate.");
    }

    /**
     * Reports a tie as the end of a game.
     */
    void reportTie() {
        System.out.println("Tie game.  Too many moves.");
    }

    /**
     * The next player to have turn
     * @param player : player with the next turn
     */
    void reportNextTurn(Player player) {
        System.out.println(player.toString() + ">");
    }

    /**
     * Prints out a list of currently captured pieces by upper and lower.
     * @param upper : upper player
     * @param lower : lower player
     */
    void reportCaptures(Player upper, Player lower) {
        System.out.print("Captures UPPER:");
        for (Piece p : upper.getCapturedPieces()) {
            System.out.print(" " + p.toString());
        }
        System.out.println();
        System.out.print("Captures lower:");
        for (Piece p : lower.getCapturedPieces()) {
            System.out.print(" " + p.toString());
        }
        System.out.println();
        System.out.println();
    }

    /**
     * Reports an illegal drop move by the inputted player, which ends the game.
     * @param player : player who executed the drop
     * @param drop : the drop that is illegal
     * @param piece : the piece that was intended to be dropped.
     * @param board : the board of the game
     * @param upper : upper player
     * @param lower : lower player
     */
    void reportIllegalDrop(Player player, Drop drop, String piece, Board board, Player upper, Player lower) {
        System.out.println(player.toString() + " player action: " + "drop " + piece + " " + drop.getTo().toString());
        reportBoard(board);
        reportCaptures(upper, lower);
        reportIllegalMove(board.getOpponent(player));
    }
}
