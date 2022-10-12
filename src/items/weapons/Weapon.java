package items.weapons;

import items.Item;
import actions.ActionResult;
import subjects.Enemy;
import subjects.Player;

public interface Weapon extends Item {
    ActionResult normalAttack(Enemy enemy, int distance);
    ActionResult skillAttack(Enemy enemy, int distance);
    ActionResult skillSurvival(Player player, Enemy enemy);
    String getName();
    int getAttackRange();
}
