package ap.mini_project.server.controller;

import ap.mini_project.server.controller.game.Game;
import ap.mini_project.server.controller.game.GameLobby;
import ap.mini_project.server.model.Side;
import ap.mini_project.shared.events.EventVisitor;
import ap.mini_project.shared.events.StartGame;
import ap.mini_project.shared.model.Board;
import ap.mini_project.shared.response.*;

import java.io.IOException;

public class ClientHandler extends Thread implements EventVisitor {
    private final ResponseSender responseSender;
    private final GameLobby gameLobby;
    private Side side;
    private Game game;
    private volatile boolean running;

    public ClientHandler(ResponseSender responseSender, GameLobby gameLobby) throws IOException {
        this.responseSender = responseSender;
        this.gameLobby = gameLobby;
    }

    @Override
    public synchronized void start() {
        running = true;
        super.start();
    }

    public void run() {
        while (running) {
            responseSender.sendResponse(responseSender.getEvent().visit(this));
        }
        responseSender.close();
    }

    @Override
    public Response startGame(StartGame startGame) {
        gameLobby.startGameRequest(this);
        return getBoard();
    }

    @Override
    public Response clickOnBoard(int x, int y) {
        if (game != null)
            game.click(x, y, side);
        return getBoard();
    }

    @Override
    public Response getBoard() {
        Board board;
        if (side == null) { // no game running
            return new EmptyResponse();
        }
        if (game == null) {
            board = new Board(7, 7);
            board.setMessage("waiting for another player");
        } else {
            if (game.getResult() == -1)
                board = game.getBoard(side);
            else {
                Game game = this.game;
                this.game = null;
                int sideIndex = side.getIndex();
                this.side = null;
                if (game.getResult() == 3)
                    return new ShowMessage("draw");
                else if (game.getResult() == sideIndex)
                    return new ShowMessage("win");
                else
                    return new ShowMessage("lose");
            }
        }
        return new BoardResponse(board);
    }

    @Override
    public Response exit() {
        running = false;
        return new ExitResponse();
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
