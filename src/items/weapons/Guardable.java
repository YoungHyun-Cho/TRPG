package items.weapons;

import actions.ActionResult;

public interface Guardable {
    ActionResult guard();

    boolean isSurvivalMode();
    void setSurvivalMode(boolean surviveMode);
}
