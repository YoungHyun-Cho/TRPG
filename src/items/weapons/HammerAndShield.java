package items.weapons;

import actions.ActionResult;

public class HammerAndShield implements OneHandedWeaponAndShield {
    private String name = "뿅망치 & 냄비뚜껑";

    private int damage = 20;

    private int attackRange = 2;

    public boolean survivalMode = false;

    public boolean guarded = false;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isSurvivalMode() {
        return survivalMode;
    }

    @Override
    public void setSurvivalMode(boolean surviveMode) {
        this.survivalMode = surviveMode;
    }

    @Override
    public void setGuarded(boolean guarded) {
        this.guarded = guarded;
    }

    @Override
    public boolean isGuarded() {
        return guarded;
    }

    @Override
    public int getAttackRange() {
        return attackRange;
    }

    @Override
    public ActionResult normalAttack(int distance) {
        if (attackRange < Math.abs(distance)) return new ActionResult("OUT_OF_RANGE", -3);
        else {
            System.out.println("[🚨] 후리기를 시전합니다.");
            return new ActionResult("후리기", damage + (int) (Math.random() * 10));
        }
    }

    @Override
    public ActionResult skillAttack(int distance) {
        if (attackRange < Math.abs(distance)) return new ActionResult("OUT_OF_RANGE", -3);
        else return shieldCounterAttack();
    }

    @Override
    public ActionResult skillSurvival() {
        return guard();
    }

    public ActionResult shieldCounterAttack() {
        if (guarded) {
            guarded = false;
            System.out.println("[🚨] 카운터 방패 치기를 시전합니다.");
            return new ActionResult("카운터 방패 치기", damage + (int) (Math.random() * 30) + 60);
        }
        else return new ActionResult("CANNOT_COUNTER", -4);
    }

    @Override
    public ActionResult guard() {
        System.out.println("[🚨] 냄비뚜껑으로 다음 턴의 공격을 1회 가드합니다.\n");
        setSurvivalMode(true);
        return new ActionResult("가드", 0);
    }
}
