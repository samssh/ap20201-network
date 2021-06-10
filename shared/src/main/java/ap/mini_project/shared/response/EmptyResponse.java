package ap.mini_project.shared.response;

public class EmptyResponse extends Response{
    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.empty();
    }
}
