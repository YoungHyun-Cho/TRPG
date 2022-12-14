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
                "[ð«] ì ì´ ì¬ì ê±°ë¦¬ ë°ê¹¥ì ìì´ ê³µê²©í  ì ììµëë¤. ì¡ìì ë¤ì ì íí´ì£¼ì¸ì."
        );
        else {
            int damage = this.damage + (int) (Math.random() * 10);
            enemy.takeDamage(damage);
            return new ActionResult(
                    ActionResultType.PLAYER_SUCCESS,
                    String.format("[ð¨] íë¦¬ê¸°ë¥¼ ìì íì¬ ì¼ìì %sì´ ë°ë¯¸ì§ %dì(ë¥¼) ìììµëë¤.", enemy.getName(), damage)
            );
        }
    }

    @Override
    public ActionResult skillAttack(Enemy enemy, int distance) {
        if (attackRange < Math.abs(distance)) return new ActionResult(
                ActionResultType.PLAYER_FAILURE_OUT_OF_RANGE,
                "[ð«] ì ì´ ì¬ì ê±°ë¦¬ ë°ê¹¥ì ìì´ ê³µê²©í  ì ììµëë¤. ì¡ìì ë¤ì ì íí´ì£¼ì¸ì."
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
                    String.format("[ð¨] ì¹´ì´í° ë°©í¨ ì¹ê¸°ë¥¼ ìì íì¬ ì¼ìì %sì´ ë°ë¯¸ì§ %dì(ë¥¼) ìê³  ê¸°ì íìµëë¤.", enemy.getName(), damage)
            );
        }
        else return new ActionResult(
                ActionResultType.PLAYER_FAILURE_NOT_GUARDED_YET,
                "[ð«] ìì§ ì ì ê³µê²©ì ê°ëíì§ ìì ì¹´ì´í° ë°©í¨ ì¹ê¸°ë¥¼ ì¬ì©í  ì ììµëë¤. ë¤ì ìë ¥í´ì£¼ì¸ì."
        );
    }

    @Override
    public ActionResult guard() {
        setSurvivalMode(true);
        return new ActionResult(
                ActionResultType.PLAYER_SUCCESS,
                "[ð¨] ëë¹ëê»ì¼ë¡ ë¤ì í´ì ê³µê²©ì 1í ê°ëí©ëë¤."
        );
    }
}
