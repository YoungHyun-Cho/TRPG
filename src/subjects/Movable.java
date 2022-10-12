package subjects;

import actions.ActionResult;

public interface Movable {
    ActionResult move(MoveDirection moveDirection, int distance);
}
