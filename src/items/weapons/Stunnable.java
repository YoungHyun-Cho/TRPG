package items.weapons;

import actions.ActionResult;

public interface Stunnable {
    ActionResult shieldCounterAttack();
    void setGuarded(boolean guarded);
    boolean isGuarded();
}
