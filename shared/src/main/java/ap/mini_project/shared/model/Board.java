package ap.mini_project.shared.model;

import java.awt.*;

public class Board {
    private int h, w;
    private Cell[][] cells;
    private String message;

    public Board(int h, int w) {
        this.h = h;
        this.w = w;
        cells = new Cell[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                cells[i][j] = new Cell();
                if ((i + j) % 2 == 0) {
                    cells[i][j].setColor(Color.BLACK);
                } else {
                    cells[i][j].setColor(Color.WHITE);
                }
            }
        }
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
