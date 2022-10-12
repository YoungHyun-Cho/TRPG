package actions;

public class ActionResult {
    private ActionResultType actionResultType;
    private String message;

    public ActionResult(ActionResultType actionResultType, String message) {
        this.actionResultType = actionResultType;
        this.message = message;
    }

    public ActionResultType getActionResultType() {
        return actionResultType;
    }

    public String getMessage() {
        return message;
    }
}
