package items.weapons;

import actions.ActionResult;
import actions.ActionResultType;
import subjects.Enemy;
import subjects.Player;

public class DefaultWeapon implements Weapon {
    private final String name;
    private final int damage;
    private final int attackRange;

    public DefaultWeapon(String name, int damage, int attackRange) {
        this.name = name;
        this.damage = damage;
        this.attackRange = attackRange;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getAttackRange() { return attackRange; }

    public ActionResult normalAttack(Enemy enemy, int distance) {
        if (attackRange < Math.abs(distance)) return new ActionResult(
                ActionResultType.PLAYER_FAILURE_OUT_OF_RANGE,
                "[🚫] 적이 사정거리 바깥에 있어 공격할 수 없습니다. 액션을 다시 선택해주세요."
        );
        else {
            int damage = this.damage + (int) (Math.random() * 5);
            enemy.takeDamage(damage);
            return new ActionResult(
                    ActionResultType.PLAYER_SUCCESS,
                    String.format("[🚨] 딱밤 때리기를 시전하여 야생의 %s이 데미지 %d을(를) 입었습니다.\n", enemy.getName(), damage)
            );
        }
    }

    @Override
    public ActionResult skillAttack(Enemy enemy, int distance) {
        return new ActionResult(
                ActionResultType.PLAYER_FAILURE_WRONG_NUMBER,
                "[🚫] 잘못 입력하셨습니다. 정확한 번호를 입력해주세요."
        );
    }

    @Override
    public ActionResult skillSurvival(Player player, Enemy enemy) {
        return new ActionResult(
                ActionResultType.PLAYER_FAILURE_WRONG_NUMBER,
                "[🚫] 잘못 입력하셨습니다. 정확한 번호를 입력해주세요."
        );
    }
}