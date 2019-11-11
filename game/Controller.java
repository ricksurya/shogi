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
    private boolean isFileMode;
    private List<String> moves;


    public Controller(Reporter reporter, String testCasePath) throws Exception {
        _reporter = reporter;
        lowerPlayer = new Player(PlayerType.LOWER, this);
        upperPlayer = new Player(PlayerType.UPPER, this);
        _board = new Board();
        moveCount = 0;
        TestCase testCase = parseTestCase(testCasePath);
        initBoard(testCase.initialPieces, testCase.upperCaptures, testCase.lowerCaptures);
        moves = testCase.moves;
        isFileMode = true;
    }

    public Controller(Reporter reporter) throws Exception {
        _reporter = reporter;
        lowerPlayer = new Player(PlayerType.LOWER, this);
        upperPlayer = new Player(PlayerType.UPPER, this);
        _board = new Board();
        moveCount = 0;
        TestCase testCase = parseTestCase(defaultPath);
        initBoard(testCase.initialPieces, testCase.upperCaptures, testCase.lowerCaptures);
        isFileMode = false;
    }

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

    public void play(List<String> moves) {
        Player currPlayer = lowerPlayer;
        int i = 0;
        while (i < moves.size() && moveCount <= MOVE_LIMIT) {
            _reporter.reportNextTurn(currPlayer);
            Player opponent = _board.getOpponent(currPlayer);
            String[] command = moves.get(i).split("\\s+");
            if (command[0].equals("move")) {
                Move move = new Move(Square.sq(command[1]), Square.sq(command[2]), (command.length == 4) ? true : false);
                if (!_board.isValidMove(move, currPlayer)) {
                    _reporter.reportIllegalMove(opponent);
                    break;
                }
                _board.makeMove(move, currPlayer);
                _reporter.reportMove(currPlayer, move);
            } else {
                Drop drop = new Drop(Square.sq(command[2]), currPlayer.getCapturedPiece(command[1]));
                if (!_board.isValidDrop(drop, currPlayer)) {
                    _reporter.reportIllegalMove(opponent);
                    break;
                }
                _board.makeDrop(drop, currPlayer);
                _reporter.reportDrop(currPlayer, drop);
            }
            _reporter.reportBoard(_board);
            _reporter.reportCaptures(upperPlayer, lowerPlayer);
            moveCount++;
            i++;
            if (_board.isCheckMate(opponent)) {
                _reporter.reportCheckMate(currPlayer);
                break;
            }
            if (_board.isCheck(opponent)) {
                _reporter.reportCheck(opponent, _board.getUncheckMoves(opponent));
            }
            currPlayer = _board.getOpponent(currPlayer);
            if (moveCount == MOVE_LIMIT) {
                _reporter.reportTie();
            }
        }
    }

    public void play() {
        Move lastMove;
        Drop lastDrop;
        Player currPlayer = lowerPlayer;
        if (isFileMode) {
            int i = 0;
            while (i < moves.size() && moveCount <= MOVE_LIMIT) {
                Player opponent = _board.getOpponent(currPlayer);
                String[] command = moves.get(i).split("\\s+");
                if (command[0].equals("move")) {
                    Move move = new Move(Square.sq(command[1]), Square.sq(command[2]), (command.length == 4) ? true : false);
                    if (!_board.isValidMove(move, currPlayer)) {
                        _reporter.reportMove(currPlayer, move);
                        _reporter.reportBoard(_board);
                        _reporter.reportCaptures(upperPlayer, lowerPlayer);
                        _reporter.reportIllegalMove(opponent);
                        break;
                    }
                    _board.makeMove(move, currPlayer);
                    if (_board.isCheckMate(opponent)) {
                        _reporter.reportMove(currPlayer, move);
                        _reporter.reportBoard(_board);
                        _reporter.reportCaptures(upperPlayer, lowerPlayer);
                        _reporter.reportCheckMate(currPlayer);
                        break;
                    }
                    if (moveCount == MOVE_LIMIT - 1|| i == moves.size() - 1) {
                        _reporter.reportMove(currPlayer, move);
                        _reporter.reportBoard(_board);
                        _reporter.reportCaptures(upperPlayer, lowerPlayer);
                        if (moveCount == MOVE_LIMIT - 1) {
                            _reporter.reportTie();
                        } else {
                            if (_board.isCheck(opponent)) {
                                _reporter.reportCheck(opponent, _board.getUncheckMoves(opponent));
                            }
                            _reporter.reportNextTurn(opponent);
                        }
                    }
                } else {
                    Drop drop = new Drop(Square.sq(command[2]), currPlayer.getCapturedPiece(command[1]));
                    if (!_board.isValidDrop(drop, currPlayer)) {
                        _reporter.reportIllegalDrop(currPlayer, drop, command[1]);
                        _reporter.reportBoard(_board);
                        _reporter.reportCaptures(upperPlayer, lowerPlayer);
                        _reporter.reportIllegalMove(opponent);
                        break;
                    }
                    _board.makeDrop(drop, currPlayer);
                    if (_board.isCheckMate(opponent)) {
                        _reporter.reportDrop(currPlayer, drop);
                        _reporter.reportBoard(_board);
                        _reporter.reportCaptures(upperPlayer, lowerPlayer);
                        _reporter.reportCheckMate(currPlayer);
                        break;
                    }
                    if (moveCount == MOVE_LIMIT - 1|| i == moves.size() - 1) {
                        _reporter.reportDrop(currPlayer, drop);
                        _reporter.reportBoard(_board);
                        _reporter.reportCaptures(upperPlayer, lowerPlayer);
                        if (moveCount == MOVE_LIMIT - 1) {
                            _reporter.reportTie();
                        } else {
                            if (_board.isCheck(opponent)) {
                                _reporter.reportCheck(opponent, _board.getUncheckMoves(opponent));
                            }
                            _reporter.reportNextTurn(opponent);
                        }
                    }
                }
                moveCount++;
                i++;
                currPlayer = _board.getOpponent(currPlayer);
            }
        } else {
            _reporter.reportBoard(_board);
            _reporter.reportCaptures(upperPlayer, lowerPlayer);
            Scanner sc = new Scanner(System.in);
            while (moveCount <= MOVE_LIMIT) {
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
