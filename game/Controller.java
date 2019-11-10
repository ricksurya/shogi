package game;

import board.Board;

public class Controller {
    Controller(Reporter reporter, Player lower, Player upper) {

    }

    public Board getBoard() {
        return _board;
    }

    private Board _board = new Board();
}
