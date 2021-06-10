package ap.mini_project.shared.response;

import ap.mini_project.shared.model.Board;

public interface ResponseVisitor {
    void visitBoard(Board board);

    void showMessage(String message);

    default void empty() {

    }
}
