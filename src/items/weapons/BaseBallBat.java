package items.weapons;

import actions.ActionResult;

public class BaseBallBat implements TwoHandedWeapon {
    private String name = "야구 방망이";

    private int damage = 30;

    private int attackRange = 3;

    private int charge = 0;

    private boolean survivalMode = false;

    @Override
    public boolean isSurvivalMode() {
        return survivalMode;
    }

    @Override
    public void setSurvivalMode(boolean survivalMode) {
        this.survivalMode = survivalMode;
    }

    @Override
    public String getName() {
        return name;
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
    public ActionResult normalAttack(int distance) {
        if (attackRange < Math.abs(distance)) return new ActionResult("OUT_OF_RANGE", -3);
        else {
            System.out.printf("[🚨] %d차지 휘둘러치기를 시전합니다.\n", charge);
            int temp = charge + 1;
            charge = 0;
            return new ActionResult("휘둘러치기", damage + (int) ((Math.random() * 5) + 30) * temp);
        }
    }

    @Override
    public ActionResult skillAttack(int distance) {
        return charge();
    }

    @Override
    public ActionResult skillSurvival() {
        return guard();
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
    public ActionResult guard() {
        System.out.println("[🚨] 야구 방망이로 다음 턴의 공격을 1회 가드합니다.\n");
        setSurvivalMode(true);
        return new ActionResult("가드", 0);
    }
}
