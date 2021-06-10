package ap.mini_project.shared.response;

public class ExitResponse extends Response{
    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.exit();
    }
}
