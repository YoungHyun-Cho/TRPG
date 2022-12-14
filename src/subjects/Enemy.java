package subjects;

import actions.ActionResult;
import actions.ActionResultType;
import items.weapons.Guardable;

public class Enemy implements Movable, Harmable {

    private final String name;
    private final int damage;
    private final int exp;
    private final int[] dropItemIndex;
    private final int maxHp;
    private int hp;
    private int position;

    private int passTurn = 0;

    public Enemy(String name, int hp, int maxHp, int damage, int exp, int position, int[] dropItemIndex) {
        this.name = name;
        this.hp = hp;
        this.maxHp = maxHp;
        this.damage = damage;
        this.exp = exp;
        this.position = position;
        this.dropItemIndex = dropItemIndex;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getExp() {
        return exp;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setPassTurn(int passTurn) {
        this.passTurn = passTurn;
    }

    public int[] getDropItemIndex() {
        return dropItemIndex;
    }

    @Override
    public void takeDamage(int damage) {
        this.hp -= damage;
    }

    public ActionResult attack(Player player, String enemyAttackName, int damage) {
        if (player.getWeapon() instanceof Guardable && ((Guardable) player.getWeapon()).isSurvivalMode())
            return player.guardEnemyAttack(((Guardable) player.getWeapon()), name, enemyAttackName, damage);
        else {
            player.takeDamage(damage);
            return new ActionResult(
                    ActionResultType.ENEMY_SUCCESS_ATTACK,
                    String.format("[ð¨] ì¼ìì %sì´ %së¥¼ ìì íì¬ ë°ë¯¸ì§ %dì(ë¥¼) ìììµëë¤.", name, enemyAttackName, damage)
            );
        }
    }

    public ActionResult pinch(Player player) {

        int damage = this.damage + (int) (Math.random() * 3);
        return attack(player, "ê¼¬ì§ê¸°ð¤", damage);
    }

    public ActionResult crash(Player player) {

        int damage = this.damage + (int) (Math.random() * 5);
        return attack(player, "ëª¸íµ ë°ì¹ê¸°ð¥", damage);

    }

    public ActionResult bite(Player player) {

        int damage = this.damage + (int) (Math.random() * 10);
        return attack(player, "ê¹¨ë¬¼ê¸°ð¦·", damage);

    }

    public ActionResult scream(Player player) {

        int damage = this.damage + (int) (Math.random() * 3);
        return attack(player, "ìë¦¬ ì§ë¥´ê¸°ð±", damage);

    }

    public ActionResult throwStone(Player player) {

        int damage = this.damage + (int) (Math.random() * 5);
        return attack(player, "ë ëì§ê¸°ðª¨", damage);

    }

    @Override
    public ActionResult move(MoveDirection moveDirection, int distance) {

        position -= 1;
        return new ActionResult(
                ActionResultType.ENEMY_SUCCESS_MOVE_FORWARD,
                String.format("[ð¨] ì¼ìì %sì´ íë ì´ì´ë¥¼ í¥í´ 1ë§í¼ ì ì§íìµëë¤.", name)
        );
    }

    public ActionResult action(Player player) {

        if (passTurn > 0) {
            passTurn -= 1;
            return new ActionResult(
                    ActionResultType.ENEMY_FAILURE_IS_STUNNED,
                    String.format("[ð¨] ì¼ìì %sì´(ê°) ê¸°ì íì¬ ìë¬´ê²ë íì§ ëª»í©ëë¤.", name)
            );
        }

        int distance = position - player.getPosition();

        if (Math.abs(distance) <= 1) {
            int randomNum = (int) (Math.random() * 9);
            switch (randomNum) {
                case 0:
                case 1: return bite(player);
                case 2:
                case 3:
                case 4: return crash(player);
                default: return pinch(player);
            }
        }

        else if (Math.abs(distance) <= 5) {
            int randomNum = (int) (Math.random() * 7);
            switch (randomNum) {
                case 0: return throwStone(player);
                case 1:
                case 2: return scream(player);
                default: return  move(MoveDirection.FORWARD, distance);
            }
        }

        else return move(MoveDirection.FORWARD, distance);
    }
}
