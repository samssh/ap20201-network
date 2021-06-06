package ap.mini_project.client.graphic.panels;

import ap.mini_project.client.graphic.Constant;
import ap.mini_project.client.listener.EventListener;
import ap.mini_project.shared.events.ClickOnBoard;
import ap.mini_project.shared.model.Board;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends AbstractPanel {
    private final EventListener listener;
    private final BoardPanel boardPanel;
    private final int cellH;
    private final int cellW;
    private final JLabel label;

    public GamePanel(EventListener listener, Board board) {
        this.listener = listener;
        this.cellH = (Constant.HEIGHT - 200) / board.getH();
        this.cellW = (Constant.WIDTH - 200) / board.getW();
        this.boardPanel = new BoardPanel(board, cellW, cellH);
        this.add(boardPanel);
        boardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                click(e.getX() / cellW, e.getY() / cellH);
            }
        });

        label = new JLabel(board.getMessage());
        label.setBounds(300, 40, 100, 100);
        this.add(label);
    }

    public void setBoard(Board board) {
        boardPanel.setBoard(board);
        label.setText(board.getMessage());
    }

    private void click(int x, int y) {
        listener.listen(new ClickOnBoard(x, y));
    }
}
