package ap.mini_project.server.controller.game.xo;

import ap.mini_project.server.controller.game.Game;
import ap.mini_project.server.model.Side;
import ap.mini_project.shared.model.Board;
import ap.mini_project.shared.model.Cell;

import java.awt.*;
import java.util.EnumMap;
import java.util.Map;

public class XOGame implements Game {
    private final static Object value = new Object();
    private final static int[] dxs = {1, 1, 0, -1}, dys = {0, 1, 1, 1};
    private final int width, height, winningRace;
    private final GameState gameState;

    public XOGame(int width, int height, int winningRace) {
        this.width = width;
        this.height = height;
        this.winningRace = winningRace;
        Side side = ((int) (Math.random() * 2)) == 0 ? Side.PLAYER_ONE : Side.PLAYER_TWO;
        Piece pieceP1, pieceP2;
        if (((int) (Math.random() * 2)) == 0) {
            pieceP1 = Piece.O;
            pieceP2 = Piece.X;
        } else {
            pieceP1 = Piece.X;
            pieceP2 = Piece.O;
        }
        gameState = new GameState(width, height, side, pieceP1, pieceP2);
    }


    public synchronized void click(int i, int j, Side side) {
        if (side != gameState.getSideToTurn())
            return;
        if (-1 >= i || i >= width || -1 >= j || j >= height) {
            return;
        }
        if (gameState.getPiece(i, j) != Piece.EMPTY)
            return;
        gameState.putPiece(i, j, side);
        check(i, j);
        checkDraw();
        gameState.setSideToTurn(gameState.getSideToTurn().getOther());
    }

    @Override
    public Board getBoard(Side side) {
        Board board = new Board(height, width);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Cell cell = board.getCells()[i][j];
                if (gameState.getPiece(i, j) == Piece.O) {
                    cell.setColor(Color.GREEN);
                } else if (gameState.getPiece(i, j) == Piece.X) {
                    cell.setColor(Color.YELLOW);
                }
            }
        }
        if (gameState.getSideToTurn() == side) {
            board.setMessage("your turn");
        } else {
            board.setMessage("enemy turn");
        }
        return board;
    }

    @Override
    public int getResult() {
        return gameState.getResult();
    }

    private void check(int i, int j) {
        for (int k = 0; k < dxs.length; k++) {
            int dx = dxs[k], dy = dys[k];
            for (int m = 1 - winningRace; m < 1; m++) {
                Map<Piece, Object> map = new EnumMap<>(Piece.class);
                for (int n = m; n < winningRace + m; n++) {
                    map.put(gameState.getPiece(i + n * dx, j + n * dy), value);
                }
                if (map.size() == 1) {
                    gameState.setResult(gameState.getSideToTurn().getIndex());
                }
            }
        }
    }

    private void checkDraw() {
        Map<Piece, Object> map = new EnumMap<>(Piece.class);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                map.put(gameState.getPiece(i, j), value);
            }
        }
        if (!map.containsKey(Piece.EMPTY)) {
            gameState.setResult(3);
        }
    }

//    public synchronized Side getSideToTurn() {
//        return gameState.getSideToTurn();
//    }
//
//    public Piece getPiece(Side side) {
//        return gameState.getPlayerPiece(side);
//    }
//
//    public String getOpponentUsername(Side side) {
//        return players[side.getOther().getIndex()].getUsername();
//    }
}