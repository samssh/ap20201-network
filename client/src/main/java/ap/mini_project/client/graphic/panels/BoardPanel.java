package ap.mini_project.client.graphic.panels;


import ap.mini_project.client.graphic.ImageLoader;
import ap.mini_project.shared.model.Board;
import ap.mini_project.shared.model.Cell;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {
    private Board board;
    private int cellH;
    private int cellW;

    public BoardPanel() {
        setLayout(null);
    }

    public synchronized void setBoard(Board board, int cellW, int cellH) {
        this.board = board;
        this.cellW = cellW;
        this.cellH = cellH;
        this.setBounds(100, 100, cellW * board.getW(), cellH * board.getH());
    }

    @Override
    protected synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < board.getW(); i++) {
            for (int j = 0; j < board.getH(); j++) {
                Cell tem = board.getCells()[i][j];
                Color gColor = g.getColor();
                g.setColor(new Color(tem.getR(), tem.getG(), tem.getB()));
                g.fillRect(i * cellW, j * cellH, cellW, cellH);
                if (tem.getPieceName() != null) {
                    g.drawImage(ImageLoader.getInstance().getImage(tem.getPieceName()), i * cellW, j * cellH,
                            cellW, cellH, this);
                }
                g.setColor(gColor);
            }
        }
    }
}
