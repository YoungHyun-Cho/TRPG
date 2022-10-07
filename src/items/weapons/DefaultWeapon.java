package items.weapons;

import actions.ActionResult;

public class DefaultWeapon implements Weapon {
    private String name = "딱밤";
    private int damage = 100; // 임시
    private int attackRange = 1;

    public String getName() {
        return name;
    }

    @Override
    public int getAttackRange() {
        return attackRange;
    }

    @Override
    public ActionResult skillAttack(int distance) {
        return new ActionResult(null, 0);
    }

    @Override
    public ActionResult skillSurvival() {
        return new ActionResult(null, 0);
    }

    public ActionResult normalAttack(int distance) {
        System.out.println("딱밤 때리기를 시전합니다.");
        if (attackRange < Math.abs(distance)) return new ActionResult("OUT_OF_RANGE", -3);
        else return new ActionResult("딱밤 때리기", damage + (int) (Math.random() * 5));
    }
}