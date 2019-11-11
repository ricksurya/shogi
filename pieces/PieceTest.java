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
public class PieceTest {

    Board b;
    Player lower;
    Player upper;

    @Before
    public void init() throws Exception {
        b = new Board();
        Controller c = new Controller(new Reporter());
        lower = new Player(PlayerType.LOWER, c);
        upper = new Player(PlayerType.UPPER, c);
    }

    @Test
    public void testDrive() {
        Square from = Square.sq(0, 0);
        Drive drive = new Drive(from, lower);
        b.placePieceAt(drive, from);
        System.out.println(b.toString());
        Move moveUp = new Move(from, Square.sq(0, 1), false);
        assertTrue(drive.isLegalPieceMove(moveUp, b));
        Move moveUpRight = new Move(from, Square.sq(1, 1), false);
        assertTrue(drive.isLegalPieceMove(moveUpRight, b));
        Move moveRight = new Move(from, Square.sq(1, 0), false);
        assertTrue(drive.isLegalPieceMove(moveRight, b));
        Move moveUpFar = new Move(from, Square.sq(0, 2), false);
        assertFalse(drive.isLegalPieceMove(moveUpFar, b));
        Move moveUpRightWrong = new Move(from, Square.sq(1, 3), false);
        assertFalse(drive.isLegalPieceMove(moveUpRightWrong, b));
    }

    @Test
    public void testNotes() {
        Square from = Square.sq(2, 2);
        Notes notes = new Notes(from, lower);
        Preview preview = new Preview(Square.sq(2, 1), lower);
        b.placePieceAt(preview, Square.sq(2, 1));
        b.placePieceAt(notes, from);
        System.out.println(b.toString());
        Move moveUp = new Move(from, Square.sq(2, 3), false);
        assertTrue(notes.isLegalPieceMove(moveUp, b));
        Move moveUpFar = new Move(from, Square.sq(2, 4), false);
        assertTrue(notes.isLegalPieceMove(moveUpFar, b));
        Move moveUpRight = new Move(from, Square.sq(3, 3), false);
        assertFalse(notes.isLegalPieceMove(moveUpRight, b));
        Move moveRight = new Move(from, Square.sq(3, 2), false);
        assertTrue(notes.isLegalPieceMove(moveRight, b));
        Move moveUpRightWrong = new Move(from, Square.sq(3, 4), false);
        assertFalse(notes.isLegalPieceMove(moveUpRightWrong, b));
        Move moveDownOverPreview = new Move(from, Square.sq(2, 0), false);
        assertFalse(notes.isLegalPieceMove(moveDownOverPreview, b));

        notes.promote();
        assertTrue(notes.isLegalPieceMove(moveUpRight, b));
        assertTrue(notes.isLegalPieceMove(moveUpFar, b));
        System.out.println(b.toString());
    }

    @Test
    public void testGov() {
        Square from = Square.sq(2, 2);
        Governance gov = new Governance(from, lower);
        Preview preview = new Preview(Square.sq(3, 1), lower);
        b.placePieceAt(preview, Square.sq(3, 1));
        b.placePieceAt(gov, from);
        System.out.println(b.toString());
        Move moveUp = new Move(from, Square.sq(2, 3), false);
        assertFalse(gov.isLegalPieceMove(moveUp, b));
        Move moveDownLeftFar = new Move(from, Square.sq(0, 0), false);
        assertTrue(gov.isLegalPieceMove(moveDownLeftFar, b));
        Move moveDownRightFar = new Move(from, Square.sq(4, 0), false);
        assertFalse(gov.isLegalPieceMove(moveDownRightFar, b));
        gov.promote();
        System.out.println(b.toString());
        assertTrue(gov.isLegalPieceMove(moveUp, b));
        Move moveUpFar = new Move(from, Square.sq(2, 4), false);
        assertFalse(gov.isLegalPieceMove(moveUpFar, b));
    }

    @Test
    public void testShield() {
        Square from = Square.sq(2, 2);
        Shield shield = new Shield(from, lower);
        b.placePieceAt(shield, from);
        System.out.println(b.toString());
        Move moveUp = new Move(from, Square.sq(2, 3), false);
        assertTrue(shield.isLegalPieceMove(moveUp, b));
        Move moveDownRight = new Move(from, Square.sq(3, 1), false);
        assertFalse(shield.isLegalPieceMove(moveDownRight, b));
    }

    @Test
    public void testRelay() {
        Square from = Square.sq(2, 2);
        Relay relay = new Relay(from, lower);
        b.placePieceAt(relay, from);
        System.out.println(b.toString());
        Move moveUp = new Move(from, Square.sq(2, 3), false);
        assertTrue(relay.isLegalPieceMove(moveUp, b));
        Move moveDownRight = new Move(from, Square.sq(3, 1), false);
        assertTrue(relay.isLegalPieceMove(moveDownRight, b));
        Move moveRight = new Move(from, Square.sq(3, 2), false);
        assertFalse(relay.isLegalPieceMove(moveRight, b));
        Move moveDown = new Move(from, Square.sq(2, 1), false);
        assertFalse(relay.isLegalPieceMove(moveDown, b));

        relay.promote();
        System.out.println(b.toString());
        assertTrue(relay.isLegalPieceMove(moveRight, b));
        assertTrue(relay.isLegalPieceMove(moveDown, b));
        assertTrue(relay.isLegalPieceMove(moveDownRight, b));
    }

    @Test
    public void testPreview() {
        Square from = Square.sq(2, 2);
        Preview prev = new Preview(from, lower);
        b.placePieceAt(prev, from);
        System.out.println(b.toString());
        Move moveUp = new Move(from, Square.sq(2, 3), false);
        assertTrue(prev.isLegalPieceMove(moveUp, b));
        Move moveDownRight = new Move(from, Square.sq(3, 1), false);
        assertFalse(prev.isLegalPieceMove(moveDownRight, b));
        Move moveRight = new Move(from, Square.sq(3, 2), false);
        assertFalse(prev.isLegalPieceMove(moveRight, b));
        Move moveDown = new Move(from, Square.sq(2, 1), false);
        assertFalse(prev.isLegalPieceMove(moveDown, b));

        prev.promote();
        System.out.println(b.toString());
        assertTrue(prev.isLegalPieceMove(moveRight, b));
        assertTrue(prev.isLegalPieceMove(moveDown, b));
        assertFalse(prev.isLegalPieceMove(moveDownRight, b));
    }
}
