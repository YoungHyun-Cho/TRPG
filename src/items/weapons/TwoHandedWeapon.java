package items.weapons;

import actions.ActionResult;
import actions.ActionResultType;
import subjects.Enemy;
import subjects.Player;

public class TwoHandedWeapon implements Guardable, Chargeable {
    private final String name;
    private final int damage;
    private final int attackRange;
    private int charge;
    private boolean survivalMode;

    public TwoHandedWeapon(String name, int damage, int attackRange, int charge, boolean survivalMode) {
        this.name = name;
        this.damage = damage;
        this.attackRange = attackRange;
        this.charge = charge;
        this.survivalMode = survivalMode;
    }

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
                "[ð«] ì ì´ ì¬ì ê±°ë¦¬ ë°ê¹¥ì ìì´ ê³µê²©í  ì ììµëë¤. ì¡ìì ë¤ì ì íí´ì£¼ì¸ì."
        );
        else {
            int temp = charge + 1;
            charge = 0;
            int damage = this.damage + (int) ((Math.random() * 5) + 30) * temp;
            enemy.takeDamage(damage);
            return new ActionResult(
                    ActionResultType.PLAYER_SUCCESS,
                    String.format("[ð¨] %dì°¨ì§ íëë¬ì¹ê¸°ë¥¼ ìì íì¬ ì¼ìì %sì´ ë°ë¯¸ì§ %dì(ë¥¼) ìììµëë¤.", temp - 1, enemy.getName(), damage)
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
                String.format("[ð¨] ì°¨ì§ë¥¼ ìì í©ëë¤. íì¬ ì°¨ì§ ë ë²¨ : %d", ++charge)
        );
        else return new ActionResult(
                ActionResultType.PLAYER_FAILURE_CANNOT_CHARGE_MORE,
                "[ð¨] ë ì´ì ì°¨ì§í  ì ììµëë¤."
        );
    }

    @Override
    public ActionResult guard() {
        setSurvivalMode(true);
        return new ActionResult(
                ActionResultType.PLAYER_SUCCESS,
                "[ð¨] ì¼êµ¬ ë°©ë§ì´ë¡ ë¤ì í´ì ê³µê²©ì 1í ê°ëí©ëë¤."
        );
    }
}
