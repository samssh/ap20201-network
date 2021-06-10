package ap.mini_project.client.graphic.panels;

import ap.mini_project.client.graphic.Constant;
import ap.mini_project.client.listener.EventListener;
import ap.mini_project.shared.events.ExitEvent;
import ap.mini_project.shared.events.StartGame;

import javax.swing.*;

public class MainMenuPanel extends AbstractPanel {
    private final JButton start;
    private final JButton exit;
    private final EventListener eventListener;

    public MainMenuPanel(EventListener eventListener) {
        this.eventListener = eventListener;
        start = new JButton("start");
        exit = new JButton("exit");
        this.initialize();
    }

    public void initialize() {
        initializeStart();
        initializeExit();
    }

    public void initializeStart() {
        start.setBounds(Constant.BUTTON_X, Constant.START_Y, Constant.BUTTON_WIDTH, Constant.BUTTON_HEIGHT);
        start.addActionListener(e -> this.startAction());
        add(start);
    }

    public void initializeExit() {
        exit.setBounds(Constant.BUTTON_X, Constant.EXIT_Y, Constant.BUTTON_WIDTH, Constant.BUTTON_HEIGHT);
        exit.addActionListener(e -> this.exitAction());
        add(exit);
    }


    private void startAction() {
        eventListener.listen(new StartGame());
    }

    private void exitAction() {
        eventListener.listen(new ExitEvent());
    }

}
