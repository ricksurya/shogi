package board;

import game.Controller;
import game.Player;
import game.PlayerType;
import game.Reporter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import pieces.*;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class BoardTest {

    Board b;
    Player lower;
    Player upper;

    @Before
    public void init() throws Exception {
        b = new Board();
        Controller c = new Controller(new Reporter());
        lower = new Player(PlayerType.LOWER, c);
        upper = new Player(PlayerType.UPPER, c);
        Drive dLower = new Drive(Square.sq(0, 0), lower);
        b.updateDrivePosition(dLower, Square.sq(0, 0));
        Drive dUpper = new Drive(Square.sq(4, 4), upper);
        b.placePieceAt(dLower, Square.sq(0, 0));
        b.updateDrivePosition(dUpper, Square.sq(4, 4));
        b.placePieceAt(dUpper, Square.sq(4, 4));
        upper.addCurrPiece(dUpper);
        lower.addCurrPiece(dLower);
    }

    @Test
    public void testBasicPutGet() {
        Square sq = Square.sq(2, 2);
        Piece p = new Drive(sq, lower);
        b.placePieceAt(p, sq);
        assertEquals(b.getPieceAt(sq), p);
    }

    @Test
    public void testBasicMove() {
        Square sq = Square.sq(2, 2);
        Preview p = new Preview(sq, lower);
        lower.addCurrPiece(p);
        b.placePieceAt(p, sq);
        Preview p2 = new Preview(sq, upper);
        upper.addCurrPiece(p2);
        b.placePieceAt(p2, Square.sq(2,4));
        System.out.println(b.toString());
        Move moveUp = new Move(sq, Square.sq(2, 3), false);
        b.makeMove(moveUp, lower);
        assertEquals(b.getPieceAt(sq), null);
        System.out.println(b.toString());
        moveUp = new Move(Square.sq(2, 3), Square.sq(2, 4), false);
        b.makeMove(moveUp, lower);
        assertEquals(b.getPieceAt(Square.sq(2, 4)), p);
        System.out.println(b.toString());
        assertTrue(p.isPromoted());
        assertEquals(lower.getCapturedPieces().size(), 1);
        assertEquals(upper.getCurrPieces().size(), 1);
        assertFalse(b.isValidDrop(new Drop(Square.sq(0,4), p2), lower));
        assertTrue(b.isValidDrop(new Drop(Square.sq(0,3), p2), lower));
        b.makeDrop(new Drop(Square.sq(0,3), p2), lower);
        System.out.println(b.toString());
        assertEquals(lower.getCapturedPieces().size(), 0);
        assertEquals(lower.getCurrPieces().size(), 3);
    }

    @Test
    public void testCheck() {
        Square sq = Square.sq(4, 3);
        Preview p = new Preview(sq, lower);
        lower.addCurrPiece(p);
        b.placePieceAt(p, sq);
        System.out.println(b.toString());
        assertTrue(b.isCheck(upper));
    }
    @Test
    public void testCheckMate() {
        Square sq = Square.sq(4, 3);
        Preview p = new Preview(sq, lower);
        lower.addCurrPiece(p);
        b.placePieceAt(p, sq);
        System.out.println(b.toString());
        assertFalse(b.isCheckMate(upper));
        assertEquals(b.getUncheckMoves(upper).size(), 3);
        sq = Square.sq(3, 4);
        p = new Preview(sq, lower);
        lower.addCurrPiece(p);
        b.placePieceAt(p, sq);
        sq = Square.sq(3, 3);
        p = new Preview(sq, lower);
        lower.addCurrPiece(p);
        b.placePieceAt(p, sq);
        System.out.println(b.toString());
        assertTrue(b.isCheckMate(upper));
    }
}
