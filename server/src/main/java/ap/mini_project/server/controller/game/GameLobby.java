package ap.mini_project.server.controller.game;

import ap.mini_project.server.controller.ClientHandler;
import ap.mini_project.server.controller.game.xo.XOGame;
import ap.mini_project.server.model.Side;

public class GameLobby {
    private ClientHandler waiting;

    public GameLobby() {
    }

    public synchronized void startGameRequest(ClientHandler clientHandler) {
        if (waiting == null) {
            waiting = clientHandler;
            clientHandler.setSide(Side.PLAYER_ONE);
        } else {
            if (waiting != clientHandler) {
                Game game = new XOGame(7, 7, 4); // new
                clientHandler.setSide(Side.PLAYER_TWO);
                waiting.setGame(game);
                clientHandler.setGame(game);
                waiting = null;
            }
        }
    }
}
