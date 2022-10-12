package items.weapons;

import actions.ActionResult;
import actions.ActionResultType;
import subjects.Enemy;
import subjects.Player;

public class BaseBallBat implements TwoHandedWeapon {
    private String name = "야구 방망이";
    private int damage = 50;
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
    public ActionResult normalAttack(Enemy enemy, int distance) {
        if (attackRange < Math.abs(distance)) return new ActionResult(
                ActionResultType.PLAYER_FAILURE_OUT_OF_RANGE,
                "[🚫] 적이 사정거리 바깥에 있어 공격할 수 없습니다. 액션을 다시 선택해주세요."
        );
        else {
            int temp = charge + 1;
            charge = 0;
            damage = this.damage + (int) ((Math.random() * 5) + 30) * temp;
            enemy.takeDamage(damage);
            System.out.printf("[🚨] 야생의 %s이 데미지 %d을(를) 입었습니다.\n", enemy.getName(), damage);
            return new ActionResult(
                    ActionResultType.PLAYER_SUCCESS,
                    String.format("[🚨] %d차지 휘둘러치기를 시전합니다.\n", temp - 1)
            );
        }
    }

    @Override
    public ActionResult skillAttack(Enemy enemy, int distance) { return charge(); }

    @Override
    public ActionResult skillSurvival(Player player, Enemy enemy) {
        return guard();
    }

    @Override
    public ActionResult charge() {
        if (charge < 3) return new ActionResult(
                ActionResultType.PLAYER_SUCCESS,
                String.format("[🚨] 차지를 시전합니다. 현재 차지 레벨 : %d\n", ++charge)
        );
        else return new ActionResult(
                ActionResultType.PLAYER_FAILURE_CANNOT_CHARGE_MORE,
                "[🚨] 더 이상 차지할 수 없습니다."
        );
    }

    @Override
    public ActionResult guard() {
        setSurvivalMode(true);
        return new ActionResult(
                ActionResultType.PLAYER_SUCCESS,
                "[🚨] 야구 방망이로 다음 턴의 공격을 1회 가드합니다."
        );
    }
}
