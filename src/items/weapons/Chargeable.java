package items.weapons;

import actions.ActionResult;

public interface Chargeable extends Weapon {
    ActionResult charge();
    int getCharge();
}
