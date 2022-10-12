package subjects;

import actions.ActionResult;

public interface Recoverable {
    ActionResult recoverHp(int quantity);
    ActionResult recoverMp(int quantity);
}
