package ap.mini_project.client.graphic;

import ap.mini_project.client.graphic.panels.AbstractPanel;
import ap.mini_project.client.graphic.panels.GamePanel;
import ap.mini_project.client.graphic.panels.MainMenuPanel;
import ap.mini_project.client.listener.EventListener;
import ap.mini_project.shared.events.GetBoard;
import ap.mini_project.shared.model.Board;
import ap.mini_project.shared.util.Loop;

import java.util.EnumMap;
import java.util.Map;

public class GraphicalAgent {
    private final EventListener listener;
    private final MyFrame frame;
    private final Map<PanelType, AbstractPanel> panels;
    private Loop loop;


    public GraphicalAgent(EventListener listener) {
        this.listener = listener;
        this.frame = new MyFrame();
        this.panels = new EnumMap<>(PanelType.class);

    }

    public void initialize() {
        frame.setVisible(true);
    }

    public void gotoMainMenu() {
        MainMenuPanel panel = new MainMenuPanel(listener);
        frame.setContentPane(panel);
        panels.put(PanelType.MAIN_MENU, panel);
        if (loop != null)
            loop.stop();
    }

    public void gotoGamePanel(Board board) {
        if (frame.getContentPane() != panels.get(PanelType.GAME_PANEL)) {
            GamePanel gamePanel = new GamePanel(listener, board);
            frame.setContentPane(gamePanel);
            panels.put(PanelType.GAME_PANEL, gamePanel);
            loop = new Loop(2, this::updateBoard);
            loop.start();
        } else {
            GamePanel gamePanel = (GamePanel) panels.get(PanelType.GAME_PANEL);
            gamePanel.setBoard(board);
        }
    }

    private void updateBoard() {
        listener.listen(new GetBoard());
    }
}
