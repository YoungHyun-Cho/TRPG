package items.weapons;

import actions.ActionResult;
import subjects.Enemy;

public interface Stunnable extends Weapon {
    ActionResult shieldCounterAttack(Enemy enemy);
    void setGuarded(boolean guarded);
    boolean isGuarded();
}
