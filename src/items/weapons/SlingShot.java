package items.weapons;

import actions.ActionResult;
import subjects.Enemy;
import subjects.Player;

public class SlingShot implements RangedWeapon {

    private String name = "새총";
    private int damage = 20;
    private int attackRange = 10;

    private int charge = 0;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ActionResult normalAttack(int distance) {
        if (attackRange < Math.abs(distance)) return new ActionResult("OUT_OF_RANGE", -3);
        else {
            System.out.printf("[🚨] %d차지 돌멩이 발사를 시전합니다.\n", charge);
            int temp = charge + 1;
            charge = 0;
            return new ActionResult("돌멩이 발사", damage + (int) ((Math.random() * 5) + 30) * temp);
        }
    }

    @Override
    public ActionResult skillAttack(int distance) {
        return charge();
    }

    @Override
    public ActionResult skillSurvival() {
        return backStep();
    }
    @Override
    public int getCharge() {
        return charge;
    }

    @Override
    public int getAttackRange() {
        return attackRange;
    }

    @Override
    public ActionResult charge() {
        if (charge < 3) {
            System.out.printf("[🚨] 차지를 시전합니다.\n", ++charge);
            return new ActionResult("차지", charge);
        }
        else {
            System.out.println("[🚨] 더 이상 차지할 수 없습니다.\n");
            return new ActionResult("차지", -1);
        }
    }

    @Override
    public ActionResult backStep() {
        System.out.println("[🚨] 백스텝을 시전하여 적과의 거리를 10만큼 벌렸습니다.");
        return new ActionResult("백스텝", 10);
    }
}
