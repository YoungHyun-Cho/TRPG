package items.weapons;

import items.Item;
import actions.ActionResult;

public interface Weapon extends Item {
    ActionResult normalAttack(int distance);
    ActionResult skillAttack(int distance);
    ActionResult skillSurvival();
    String getName();

    int getAttackRange();


}
