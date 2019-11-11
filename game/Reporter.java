package game;

import board.Board;
import board.Drop;
import board.Move;
import pieces.Piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Reporter {

    void reportBoard(Board board) {
        System.out.println(board.toString());
    }

    void reportMove(Player player, Move move, Board board, Player upper, Player lower) {
        String s = player.toString() + " player action: " + move.toString();
        if (move.isPromote()) {
            s += " promote";
        }
        System.out.println(s);
        reportBoard(board);
        reportCaptures(upper, lower);
    }

    void reportDrop(Player player, Drop drop, Board board, Player upper, Player lower) {
        System.out.println(player.toString() + " player action: " + drop.toString().toLowerCase());
        reportBoard(board);
        reportCaptures(upper, lower);
    }

    void reportIllegalMove(Player winner) {
        System.out.println(winner.toString() + " player wins.  Illegal move.");
    }

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

    void reportCheckMate(Player winner) {
        System.out.println(winner.toString() + " player wins.  Checkmate.");
    }

    void reportTie() {
        System.out.println("Tie game.  Too many moves.");
    }

    void reportNextTurn(Player player) {
        System.out.println(player.toString() + ">");
    }

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

    void reportIllegalDrop(Player player, Drop drop, String piece, Board board, Player upper, Player lower) {
        System.out.println(player.toString() + " player action: " + "drop " + piece + " " + drop.getTo().toString());
        reportBoard(board);
        reportCaptures(upper, lower);
        reportIllegalMove(board.getOpponent(player));
    }
}
