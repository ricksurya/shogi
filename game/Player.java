package game;

import board.Controller;

public class Player {
    private board.Controller _controller;
    private final PlayerType _playerType;

    Player(PlayerType playerType, Controller controller) {
        _playerType = playerType;
        _controller = controller;
    }

    public PlayerType getPlayerType() {
        return _playerType;
    }
}
