package items.weapons;

import actions.ActionResult;

public interface Chargeable {
    ActionResult charge();
    int getCharge();
}
