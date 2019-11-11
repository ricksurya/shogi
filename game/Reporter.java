package game;

import board.Board;
import board.Drop;
import board.Move;
import pieces.Piece;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Reporter {

    public Reporter()  {
//        PrintStream o = new PrintStream(new File("test-cases/debug.out"));
//        System.setOut(o);
    }

    public void reportBoard(Board board) {
        System.out.println(board.toString());
    }

    public void reportMove(Player player, Move move) {
        String s = player.toString() + " player action: " + move.toString();
        if (move.isPromote()) {
            s += " promote";
        }
        System.out.println(s);
    }

    public void reportDrop(Player player, Drop drop) {
        System.out.println(player.toString() + " player action: " + drop.toString().toLowerCase());
    }

    public void reportIllegalMove(Player winner) {
        System.out.println(winner.toString() + " player wins.  Illegal move.");
    }

    public void reportCheck(Player inCheck, List<Move> moves) {
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

    public void reportCheckMate(Player winner) {
        System.out.println(winner.toString() + " player wins.  Checkmate.");
    }

    public void reportTie() {
        System.out.println("Tie game.  Too many moves.");
    }

    public void reportNextTurn(Player player) {
        System.out.println(player.toString() + ">");
    }

    public void reportCaptures(Player upper, Player lower) {
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

    public void reportIllegalDrop(Player player, Drop drop, String piece) {
        System.out.println(player.toString() + " player action: " + "drop " + piece + " " + drop.getTo().toString());
    }
}
