package items.weapons;

import actions.ActionResult;
import actions.ActionResultType;
import subjects.Enemy;
import subjects.Player;

public class RangedWeapon implements Chargeable, Throwable {

    private final String name;
    private final int damage;
    private final int attackRange;
    private int charge;

    public RangedWeapon(String name, int damage, int attackRange, int charge) {
        this.name = name;
        this.damage = damage;
        this.attackRange = attackRange;
        this.charge = charge;
    }

    @Override
    public String getName() { return name; }

    @Override
    public int getCharge() { return charge; }

    @Override
    public int getAttackRange() { return attackRange; }

    @Override
    public ActionResult normalAttack(Enemy enemy, int distance) {

        if (attackRange < Math.abs(distance)) return new ActionResult(
                ActionResultType.PLAYER_FAILURE_OUT_OF_RANGE,
                "[🚫] 적이 사정거리 바깥에 있어 공격할 수 없습니다. 액션을 다시 선택해주세요."
        );
        else {
            int temp = charge + 1;
            charge = 0;
            int damage = this.damage + (int) ((Math.random() * 5) + 20) * temp;
            enemy.takeDamage(damage);
            return new ActionResult(
                    ActionResultType.PLAYER_SUCCESS,
                    String.format("[🚨] %d차지 돌멩이 발사를 시전하여 야생의 %s이 데미지 %d을(를) 입었습니다.", temp - 1, enemy.getName(), damage)
            );
        }
    }

    @Override
    public ActionResult skillAttack(Enemy enemy, int distance) {
        return charge();
    }

    @Override
    public ActionResult skillSurvival(Player player, Enemy enemy) {
        return backStep(player, enemy);
    }

    @Override
    public ActionResult charge() {

        if (charge < 3) return new ActionResult(
                ActionResultType.PLAYER_SUCCESS,
                String.format("[🚨] 차지를 시전합니다. 현재 차지 레벨 : %d", ++charge)
        );

        else return new ActionResult(
                ActionResultType.PLAYER_FAILURE_CANNOT_CHARGE_MORE,
                "[🚨] 더 이상 차지할 수 없습니다."
        );
    }

    @Override
    public ActionResult backStep(Player player, Enemy enemy) {

        player.setPosition(0);
        enemy.setPosition(10);

        return new ActionResult(
                ActionResultType.PLAYER_SUCCESS,
                "[🚨] 백스텝을 시전하여 적과의 거리를 10만큼 벌렸습니다."
        );
    }
}
