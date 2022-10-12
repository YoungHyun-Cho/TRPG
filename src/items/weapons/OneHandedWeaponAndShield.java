package items.weapons;

import actions.ActionResult;
import actions.ActionResultType;
import subjects.Enemy;
import subjects.Player;

public class OneHandedWeaponAndShield implements Guardable, Stunnable {
    private final String name;
    private final int damage;
    private final int attackRange;
    public boolean survivalMode;
    public boolean guarded;

    public OneHandedWeaponAndShield(String name, int damage, int attackRange, boolean survivalMode, boolean guarded) {
        this.name = name;
        this.damage = damage;
        this.attackRange = attackRange;
        this.survivalMode = survivalMode;
        this.guarded = guarded;
    }

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
    public ActionResult normalAttack(Enemy enemy, int distance) {
        if (attackRange < Math.abs(distance)) return new ActionResult(
                ActionResultType.PLAYER_FAILURE_OUT_OF_RANGE,
                "[🚫] 적이 사정거리 바깥에 있어 공격할 수 없습니다. 액션을 다시 선택해주세요."
        );
        else {
            int damage = this.damage + (int) (Math.random() * 10);
            enemy.takeDamage(damage);
            return new ActionResult(
                    ActionResultType.PLAYER_SUCCESS,
                    String.format("[🚨] 후리기를 시전하여 야생의 %s이 데미지 %d을(를) 입었습니다.", enemy.getName(), damage)
            );
        }
    }

    @Override
    public ActionResult skillAttack(Enemy enemy, int distance) {
        if (attackRange < Math.abs(distance)) return new ActionResult(
                ActionResultType.PLAYER_FAILURE_OUT_OF_RANGE,
                "[🚫] 적이 사정거리 바깥에 있어 공격할 수 없습니다. 액션을 다시 선택해주세요."
        );
        else return shieldCounterAttack(enemy);
    }

    @Override
    public ActionResult skillSurvival(Player player, Enemy enemy) {
        return guard();
    }

    public ActionResult shieldCounterAttack(Enemy enemy) {
        if (guarded) {
            guarded = false;
            int damage = this.damage + (int) (Math.random() * 30) + 60;
            enemy.takeDamage(damage);
            enemy.setPassTurn(3);
            return new ActionResult(
                    ActionResultType.PLAYER_SUCCESS,
                    String.format("[🚨] 카운터 방패 치기를 시전하여 야생의 %s이 데미지 %d을(를) 입고 기절했습니다.", enemy.getName(), damage)
            );
        }
        else return new ActionResult(
                ActionResultType.PLAYER_FAILURE_NOT_GUARDED_YET,
                "[🚫] 아직 적의 공격을 가드하지 않아 카운터 방패 치기를 사용할 수 없습니다. 다시 입력해주세요."
        );
    }

    @Override
    public ActionResult guard() {
        setSurvivalMode(true);
        return new ActionResult(
                ActionResultType.PLAYER_SUCCESS,
                "[🚨] 냄비뚜껑으로 다음 턴의 공격을 1회 가드합니다."
        );
    }
}
