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
    private final Loop loop;
    private volatile PanelType currentPanel;


    public GraphicalAgent(EventListener listener) {
        this.listener = listener;
        this.frame = new MyFrame();
        this.panels = new EnumMap<>(PanelType.class);
        loop = new Loop(2, this::updateBoard);
    }

    public void initialize() {
        frame.setVisible(true);
        this.gotoMainMenu();
        loop.start();
    }

    public void gotoMainMenu() {
        this.currentPanel = PanelType.MAIN_MENU;
        MainMenuPanel panel = new MainMenuPanel(listener);
        frame.setContentPane(panel);
        panels.put(PanelType.MAIN_MENU, panel);
    }

    public void gotoGamePanel(Board board) {
        if (frame.getContentPane() != panels.get(PanelType.GAME_PANEL)) {
            this.currentPanel = PanelType.GAME_PANEL;
            GamePanel gamePanel = new GamePanel(listener, board);
            frame.setContentPane(gamePanel);
            panels.put(PanelType.GAME_PANEL, gamePanel);

        } else {
            GamePanel gamePanel = (GamePanel) panels.get(PanelType.GAME_PANEL);
            gamePanel.setBoard(board);
        }
    }

    private void updateBoard() {
        if (this.currentPanel == PanelType.GAME_PANEL)
            listener.listen(new GetBoard());
    }

    public MyFrame getFrame() {
        return frame;
    }
}
