package subjects;
import actions.ActionResult;

public interface Attackable {
    ActionResult normalAttack(Enemy enemy, int distance);
    ActionResult skillAttack(Enemy enemy, int distance, int mpConsumption);
    ActionResult skillSurvival(Enemy enemy, int mpConsumption);
}
