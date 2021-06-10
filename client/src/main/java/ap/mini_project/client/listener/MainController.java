package ap.mini_project.client.listener;

import ap.mini_project.client.graphic.GraphicalAgent;
import ap.mini_project.shared.events.Event;
import ap.mini_project.shared.model.Board;
import ap.mini_project.shared.response.Response;
import ap.mini_project.shared.response.ResponseVisitor;
import ap.mini_project.shared.util.Loop;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class MainController implements ResponseVisitor {
    private final EventSender eventSender;
    private final List<Event> events;
    private final Loop loop;
    private final GraphicalAgent graphicalAgent;

    public MainController(EventSender eventSender) {
        this.eventSender = eventSender;
        this.events = new LinkedList<>();
        this.loop = new Loop(10, this::sendEvents);
        this.graphicalAgent = new GraphicalAgent(this::addEvent);
    }

    public void start() {
        loop.start();
        graphicalAgent.initialize();
    }


    private void addEvent(Event event) {
        synchronized (events) {
            events.add(event);
        }
    }

    private void sendEvents() {
        List<Event> temp;
        synchronized (events) {
            temp = new LinkedList<>(events);
            events.clear();
        }
        for (Event event : temp) {
            Response response = eventSender.send(event);
            response.visit(this);
        }
    }

    @Override
    public void visitBoard(Board board) {
        graphicalAgent.gotoGamePanel(board);
    }

    @Override
    public void showMessage(String s) {
        graphicalAgent.gotoMainMenu();
        JOptionPane.showMessageDialog(graphicalAgent.getFrame(), s);
    }

    @Override
    public void exit() {
        eventSender.close();
        System.exit(0);
    }
}
