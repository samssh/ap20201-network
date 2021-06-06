package ap.mini_project.server.controller.game;

import ap.mini_project.server.model.Side;
import ap.mini_project.shared.model.Board;

public interface Game {
    void click(int x, int y, Side side);

    Board getBoard(Side side);

    int getResult();
}
