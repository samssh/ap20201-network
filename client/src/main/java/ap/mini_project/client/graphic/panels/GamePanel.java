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
    private int cellH;
    private int cellW;
    private final JLabel label;

    public GamePanel(EventListener listener, Board board) {
        this.listener = listener;
        this.label = new JLabel(board.getMessage(), SwingConstants.CENTER);
        label.setBounds(150, 40, 500, 100);
        this.add(label);
        this.boardPanel = new BoardPanel();
        this.setBoard(board);
        this.add(boardPanel);
        boardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                click(e.getX() / cellW, e.getY() / cellH);
            }
        });
    }

    public void setBoard(Board board) {
        this.cellH = (Constant.HEIGHT - 200) / board.getH();
        this.cellW = (Constant.WIDTH - 200) / board.getW();
        label.setText(board.getMessage());
        boardPanel.setBoard(board, cellW, cellH);
    }

    private void click(int x, int y) {
        listener.listen(new ClickOnBoard(x, y));
    }
}
