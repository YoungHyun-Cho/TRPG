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
                "[ð«] ì ì´ ì¬ì ê±°ë¦¬ ë°ê¹¥ì ìì´ ê³µê²©í  ì ììµëë¤. ì¡ìì ë¤ì ì íí´ì£¼ì¸ì."
        );
        else {
            int damage = this.damage + (int) (Math.random() * 5);
            enemy.takeDamage(damage);
            return new ActionResult(
                    ActionResultType.PLAYER_SUCCESS,
                    String.format("[ð¨] ë±ë°¤ ëë¦¬ê¸°ë¥¼ ìì íì¬ ì¼ìì %sì´ ë°ë¯¸ì§ %dì(ë¥¼) ìììµëë¤.\n", enemy.getName(), damage)
            );
        }
    }

    @Override
    public ActionResult skillAttack(Enemy enemy, int distance) {
        return new ActionResult(
                ActionResultType.PLAYER_FAILURE_WRONG_NUMBER,
                "[ð«] ìëª» ìë ¥íì¨ìµëë¤. ì íí ë²í¸ë¥¼ ìë ¥í´ì£¼ì¸ì."
        );
    }

    @Override
    public ActionResult skillSurvival(Player player, Enemy enemy) {
        return new ActionResult(
                ActionResultType.PLAYER_FAILURE_WRONG_NUMBER,
                "[ð«] ìëª» ìë ¥íì¨ìµëë¤. ì íí ë²í¸ë¥¼ ìë ¥í´ì£¼ì¸ì."
        );
    }
}