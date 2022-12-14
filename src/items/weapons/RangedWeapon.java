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
                "[ð«] ì ì´ ì¬ì ê±°ë¦¬ ë°ê¹¥ì ìì´ ê³µê²©í  ì ììµëë¤. ì¡ìì ë¤ì ì íí´ì£¼ì¸ì."
        );
        else {
            int temp = charge + 1;
            charge = 0;
            int damage = this.damage + (int) ((Math.random() * 5) + 20) * temp;
            enemy.takeDamage(damage);
            return new ActionResult(
                    ActionResultType.PLAYER_SUCCESS,
                    String.format("[ð¨] %dì°¨ì§ ëë©©ì´ ë°ì¬ë¥¼ ìì íì¬ ì¼ìì %sì´ ë°ë¯¸ì§ %dì(ë¥¼) ìììµëë¤.", temp - 1, enemy.getName(), damage)
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
                String.format("[ð¨] ì°¨ì§ë¥¼ ìì í©ëë¤. íì¬ ì°¨ì§ ë ë²¨ : %d", ++charge)
        );

        else return new ActionResult(
                ActionResultType.PLAYER_FAILURE_CANNOT_CHARGE_MORE,
                "[ð¨] ë ì´ì ì°¨ì§í  ì ììµëë¤."
        );
    }

    @Override
    public ActionResult backStep(Player player, Enemy enemy) {

        player.setPosition(0);
        enemy.setPosition(10);

        return new ActionResult(
                ActionResultType.PLAYER_SUCCESS,
                "[ð¨] ë°±ì¤íì ìì íì¬ ì ê³¼ì ê±°ë¦¬ë¥¼ 10ë§í¼ ë²ë ¸ìµëë¤."
        );
    }
}
