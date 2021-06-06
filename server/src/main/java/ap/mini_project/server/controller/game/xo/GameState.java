package ap.mini_project.server.controller.game.xo;


import ap.mini_project.server.model.Side;

public class GameState {
    private final Piece[][] pieces;
    private Side sideToTurn;
    private final Piece[] playerPiece;
    private int result = -1;

    GameState(int width, int height, Side side, Piece pieceP1, Piece pieceP2) {
        pieces = new Piece[width][height];
        playerPiece = new Piece[2];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pieces[i][j] = Piece.EMPTY;
            }
        }
        sideToTurn = side;
        playerPiece[0] = pieceP1;
        playerPiece[1] = pieceP2;
    }

    synchronized void putPiece(int i, int j, Side side) {
        pieces[i][j] = playerPiece[side.getIndex()];
    }

    Piece getPiece(int i, int j) {
        if (-1 < i && i < pieces.length && -1 < j && j < pieces[0].length)
            return pieces[i][j];
        return Piece.EMPTY;
    }

    public Side getSideToTurn() {
        return sideToTurn;
    }

    public void setSideToTurn(Side sideToTurn) {
        this.sideToTurn = sideToTurn;
    }

    public boolean isEnd() {
        return result != -1;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getResult(){
        return result;
    }

}