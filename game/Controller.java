package game;

import board.Board;
import board.Drop;
import board.Move;
import board.Square;
import pieces.*;

import java.util.List;
import java.util.Scanner;

import static game.Utils.*;

public class Controller {
    private static final int MOVE_LIMIT = 200;
    private static final String defaultPath = "game/DEFAULT.in";
    private int moveCount;
    private final Reporter _reporter;
    private final Player lowerPlayer;
    private final Player upperPlayer;
    private Board _board;
    private List<String> moves;


    /**
     * Constructor for the file mode
     * @param reporter : Reporter for the controller
     * @param testCaseName : the name of the test case file, ex: basicCheck.in
     * @throws Exception
     */
    public Controller(Reporter reporter, String testCaseName) throws Exception {
        _reporter = reporter;
        lowerPlayer = new Player(PlayerType.LOWER, this);
        upperPlayer = new Player(PlayerType.UPPER, this);
        _board = new Board();
        moveCount = 0;
        TestCase testCase = parseTestCase(testCaseName);
        initBoard(testCase.initialPieces, testCase.upperCaptures, testCase.lowerCaptures);
        moves = testCase.moves;
    }

    /**
     * Constructor for interactive mode.
     * @param reporter : Reporter for the controller
     * @throws Exception
     */
    public Controller(Reporter reporter) throws Exception {
        _reporter = reporter;
        lowerPlayer = new Player(PlayerType.LOWER, this);
        upperPlayer = new Player(PlayerType.UPPER, this);
        _board = new Board();
        moveCount = 0;
        TestCase testCase = parseTestCase(defaultPath);
        initBoard(testCase.initialPieces, testCase.upperCaptures, testCase.lowerCaptures);
    }

    /**
     * Initializes the board given the test case files.
     * @param initialPositions : initial positions of the pieces. A helper function will parse pieces.
     * @param upperCaptures : list of pieces captured by upper
     * @param lowerCaptures : list of pieces captured by lower
     */
    private void initBoard(List<InitialPosition> initialPositions, List<String> upperCaptures,
                           List<String> lowerCaptures) {
        Square location;
        Piece piece;

        for (InitialPosition initialPiece : initialPositions) {
            location = Square.sq(initialPiece.position);
            piece = parsePiece(initialPiece.piece, location);
            if (piece instanceof Drive) {
                _board.updateDrivePosition(piece, piece.getLocation());
            }
            piece.getPlayer().addCurrPiece(piece);
            _board.placePieceAt(piece, location);
        }

        location = Square.sq(0, 0);
        for (String upperCapture : upperCaptures) {
            if (upperCapture.length() == 0) {
                break;
            }
            piece = parsePiece(upperCapture, location);
            upperPlayer.addCapturedPiece(piece);
        }

        for (String lowerCapture : lowerCaptures) {
            if (lowerCapture.length() == 0) {
                break;
            }
            piece = parsePiece(lowerCapture, location);
            lowerPlayer.addCapturedPiece(piece);
        }
    }

    /**
     * Function to run file mode/test cases.
     */
    public void playFileMode() {
        Player currPlayer = lowerPlayer;
        while (moveCount < moves.size() && moveCount <= MOVE_LIMIT) {
            String[] command = moves.get(moveCount).split("\\s+");
            if (command[0].equals("move")) {
                if(!doFileMove(command, currPlayer)) {
                    break;
                }
            } else {
                if (!doFileDrop(command, currPlayer)) {
                    break;
                }
            }
            moveCount++;
            currPlayer = _board.getOpponent(currPlayer);
        }
    }

    /**
     * Helper function for file mode runner. It returns false if the given move is invalid or causes a checkmate.
     * @param command : the move in a String format.
     * @param currPlayer : player executing the move
     * @return : false if the move is illegal/results in a checkmate
     */
    private boolean doFileMove(String[] command, Player currPlayer) {
        Player opponent = _board.getOpponent(currPlayer);
        Move move = new Move(Square.sq(command[1]), Square.sq(command[2]), command.length == 4);
        if (!_board.isValidMove(move, currPlayer)) {
            _reporter.reportMove(currPlayer, move, _board, upperPlayer, lowerPlayer);
            _reporter.reportIllegalMove(opponent);
            return false;
        }
        _board.makeMove(move, currPlayer);
        if (_board.isCheckMate(opponent)) {
            _reporter.reportMove(currPlayer, move, _board, upperPlayer, lowerPlayer);
            _reporter.reportCheckMate(currPlayer);
            return false;
        }
        if (moveCount == MOVE_LIMIT - 1 || moveCount == moves.size() - 1) {
            _reporter.reportMove(currPlayer, move, _board, upperPlayer, lowerPlayer);
            if (moveCount == MOVE_LIMIT - 1) {
                _reporter.reportTie();
            } else {
                if (_board.isCheck(opponent)) {
                    _reporter.reportCheck(opponent, _board.getUncheckMoves(opponent));
                }
                _reporter.reportNextTurn(opponent);
            }
        }
        return true;
    }

    /**
     * Helper function for file mode runner. It returns false if the given drop is invalid or causes a checkmate.
     * @param command : the drop in a String format.
     * @param currPlayer : player executing the drop
     * @return : false if the drop is illegal/results in a checkmate
     */

    private boolean doFileDrop(String[] command, Player currPlayer) {
        Player opponent = _board.getOpponent(currPlayer);
        Drop drop = new Drop(Square.sq(command[2]), currPlayer.getCapturedPiece(command[1]));
        if (!_board.isValidDrop(drop, currPlayer)) {
            _reporter.reportIllegalDrop(currPlayer, drop, command[1], _board, upperPlayer, lowerPlayer);
            return false;
        }
        _board.makeDrop(drop, currPlayer);
        if (_board.isCheckMate(opponent)) {
            _reporter.reportDrop(currPlayer, drop, _board, upperPlayer,lowerPlayer);
            _reporter.reportCheckMate(currPlayer);
            return false;
        }
        if (moveCount == MOVE_LIMIT - 1 || moveCount == moves.size() - 1) {
            _reporter.reportDrop(currPlayer, drop, _board, upperPlayer, lowerPlayer);
            if (moveCount == MOVE_LIMIT - 1) {
                _reporter.reportTie();
            } else {
                if (_board.isCheck(opponent)) {
                    _reporter.reportCheck(opponent, _board.getUncheckMoves(opponent));
                }
                _reporter.reportNextTurn(opponent);
            }
        }
        return true;
    }

    /**
     * Function to run the interactive mode.
     */
    public void playInteractiveMode() {
        Player currPlayer = lowerPlayer;
        Scanner sc = new Scanner(System.in);
        while (moveCount <= MOVE_LIMIT) {
            _reporter.reportBoard(_board);
            _reporter.reportCaptures(upperPlayer, lowerPlayer);
            _reporter.reportNextTurn(currPlayer);
            Player opponent = _board.getOpponent(currPlayer);
            String[] command = (sc.nextLine()).split("\\s+");
            if (command[0].equals("move")) {
                Move move = new Move(Square.sq(command[1]), Square.sq(command[2]), (command.length == 4) ? true : false);
                if (!_board.isValidMove(move, currPlayer)) {
                    _reporter.reportIllegalMove(opponent);
                    break;
                }
                _board.makeMove(move, currPlayer);
            } else {
                Drop drop = new Drop(Square.sq(command[2]), currPlayer.getCapturedPiece(command[1]));
                if (!_board.isValidDrop(drop, currPlayer)) {
                    _reporter.reportIllegalMove(opponent);
                    break;
                }
                _board.makeDrop(drop, currPlayer);
            }
            moveCount++;
            if (_board.isCheckMate(opponent)) {
                _reporter.reportCheckMate(currPlayer);
                break;
            }
            currPlayer = _board.getOpponent(currPlayer);
            if (moveCount == MOVE_LIMIT) {
                _reporter.reportTie();
            }
        }
        sc.close();
    }


    /**
     * Parsing a piece from string based on the symbol and location
     * @param symbol : the symbol input
     * @param location : Square location of the piece
     * @return : the piece based on the symbol
     */
    private Piece parsePiece(String symbol, Square location) {
        boolean promoted = false;
        if (symbol.length() == 2) {
            promoted = true;
            symbol = symbol.substring(1);
        }

        char pieceSymbol = symbol.charAt(0);

        Player owner;
        if (Character.isUpperCase(pieceSymbol)) {
            owner = upperPlayer;
        } else {
            owner = lowerPlayer;
        }

        Piece p;
        switch (Character.toLowerCase(pieceSymbol)) {
            case 'd' :
                p = new Drive(location, owner);
                break;
            case 's' :
                p = new Shield(location, owner);
                break;
            case 'r' :
                p = new Relay(location, owner);
                break;
            case 'g' :
                p = new Governance(location, owner);
                break;
            case 'n' :
                p = new Notes(location, owner);
                break;
            default :
                p = new Preview(location, owner);
        }

        if (promoted) {
            p.promote();
        }

        return p;
    }

    public Board getBoard() {
        return _board;
    }
}
