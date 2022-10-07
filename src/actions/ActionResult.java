package actions;

public class ActionResult {
    String name;
    int result;

    public ActionResult(String name, int result) {
        this.name = name;
        this.result = result;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
