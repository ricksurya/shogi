package board;

import game.Controller;
import game.Player;
import game.PlayerType;
import game.Reporter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import pieces.Piece;
import pieces.PieceType;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class BoardTest {

    Board b;
    Player lower;
    Player upper;

    @Before
    public void init() {
        b = new Board();
        Controller c = new Controller(new Reporter());
        lower = new Player(PlayerType.LOWER, c);
        upper = new Player(PlayerType.UPPER, c);
    }

    @Test
    public void testBasicPutGet() {
        Square sq = Square.sq(2, 2);
        Piece p = new Piece(sq, lower, PieceType.PREVIEW);
        b.placePieceAt(p, sq);
        assertEquals(b.getPieceAt(sq), p);
    }
}
