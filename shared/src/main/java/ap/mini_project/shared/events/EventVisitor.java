package ap.mini_project.shared.events;

import ap.mini_project.shared.response.Response;

public interface EventVisitor {
    Response startGame(StartGame startGame);

    Response clickOnBoard(int x, int y);

    Response getBoard();

    // ...
}
