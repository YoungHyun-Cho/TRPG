package items.weapons;

import actions.ActionResult;

public interface Guardable extends Weapon {
    ActionResult guard();
    boolean isSurvivalMode();
    void setSurvivalMode(boolean surviveMode);
}
