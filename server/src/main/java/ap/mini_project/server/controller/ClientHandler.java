package ap.mini_project.server.controller;

import ap.mini_project.server.controller.game.Game;
import ap.mini_project.server.controller.game.GameLobby;
import ap.mini_project.server.model.Side;
import ap.mini_project.shared.events.EventVisitor;
import ap.mini_project.shared.events.StartGame;
import ap.mini_project.shared.model.Board;
import ap.mini_project.shared.response.BoardResponse;
import ap.mini_project.shared.response.Response;
import ap.mini_project.shared.response.ShowMessage;

import java.io.IOException;

public class ClientHandler extends Thread implements EventVisitor {
    private final ResponseSender sender;
    private final GameLobby gameLobby;
    private Side side;
    private Game game;

    public ClientHandler(ResponseSender sender, GameLobby gameLobby) throws IOException {
        this.sender = sender;
        this.gameLobby = gameLobby;
    }

    public void run() {
        while (true) {
            sender.sendResponse(sender.getEvent().visit(this));
        }
    }

    @Override
    public Response startGame(StartGame startGame) {
        gameLobby.startGameRequest(this);
        return getBoard();
    }

    @Override
    public Response clickOnBoard(int x, int y) {
        game.click(x, y, side);
        return getBoard();
    }

    @Override
    public Response getBoard() {
        Board board;
        if (game == null) {
            board = new Board(7, 7);
            board.setMessage("waiting for another player");
        } else {
            if (game.getResult() == -1)
                board = game.getBoard(side);
            else {
                Game game = this.game;
                this.game = null;
                if (game.getResult() == 3)
                    return new ShowMessage("draw");
                else if (game.getResult() == side.getIndex())
                    return new ShowMessage("win");
                else
                    return new ShowMessage("lose");
            }
        }
        return new BoardResponse(board);
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
