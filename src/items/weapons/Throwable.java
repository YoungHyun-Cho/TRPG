package items.weapons;

import actions.ActionResult;
import subjects.Enemy;
import subjects.Player;

public interface Throwable extends Weapon {
    ActionResult backStep(Player player, Enemy enemy);
}
