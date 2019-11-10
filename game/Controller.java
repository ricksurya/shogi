package game;

import board.Board;

public class Controller {
    public Controller(Reporter reporter) {

    }

    public Board getBoard() {
        return _board;
    }

    private Board _board = new Board();
}
