package BoardFiles;

public class Player {
    private Controller _controller;
    private final int _playerNo;

    Player(int playerNo, Controller controller) {
        _playerNo = playerNo;
        _controller = controller;
    }

    public int getPlayerNo() {
        return _playerNo;
    }
}
