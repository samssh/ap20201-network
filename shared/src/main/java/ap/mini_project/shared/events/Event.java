package ap.mini_project.shared.events;

import ap.mini_project.shared.response.Response;

public abstract class Event {
    public abstract Response visit(EventVisitor eventVisitor);
}
