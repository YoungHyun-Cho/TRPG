package subjects;

import actions.ActionResult;
import actions.ActionResultType;
import items.weapons.Guardable;

public class Enemy implements Movable, Harmable {

    private String name;
    private int hp;
    private int maxHp;
    private int damage;
    private int exp;
    private int position;
    private int[] dropItemIndex;

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
                    String.format("[🚨] 야생의 %s이 %s를 시전하여 데미지 %d을(를) 입었습니다.", name, enemyAttackName, damage)
            );
        }
    }

    // 꼬집기
    public ActionResult pinch(Player player) {

        int damage = this.damage + (int) (Math.random() * 3);
        String enemyAttackName = "꼬집기🤏";
        return attack(player, enemyAttackName, damage);
    }

    // 몸통 박치기
    public ActionResult crash(Player player) {

        int damage = this.damage + (int) (Math.random() * 5);
        String enemyAttackName = "몸통 박치기💥";
        return attack(player, enemyAttackName, damage);

    }

    // 깨물기
    public ActionResult bite(Player player) {

        int damage = this.damage + (int) (Math.random() * 10);
        String enemyAttackName = "깨물기🦷";
        return attack(player, enemyAttackName, damage);

    }

    // 소리 지르기
    public ActionResult scream(Player player) {

        int damage = this.damage + (int) (Math.random() * 3);
        String enemyAttackName = "소리 지르기😱";
        return attack(player, enemyAttackName, damage);

    }

    // 돌 던지기
    public ActionResult throwStone(Player player) {

        int damage = this.damage + (int) (Math.random() * 5);
        String enemyAttackName = "돌 던지기🪨";
        return attack(player, enemyAttackName, damage);

    }

    @Override
    public ActionResult move(MoveDirection moveDirection, int distance) {
        position -= 1;
        return new ActionResult(
                ActionResultType.ENEMY_SUCCESS_MOVE_FORWARD,
                String.format("[🚨] 야생의 %s이 플레이어를 향해 1만큼 전진했습니다.\n", name)
        );
    }

    public ActionResult action(Player player) {

        if (passTurn > 0) {
            passTurn -= 1;
            return new ActionResult(
                    ActionResultType.ENEMY_FAILURE_IS_STUNNED,
                    String.format("[🚨] 야생의 %s이(가) 기절하여 아무것도 하지 못합니다.\n", name)
            );
        }

        int distance = position - player.getPosition();
        ActionResult actionResult;

        // 1 이하의 거리에서는 근거리 공격만 가능함.
        if (Math.abs(distance) <= 1) {
            int randomNum = (int) (Math.random() * 9);
            switch (randomNum) {
                case 0:
                case 1: actionResult = bite(player); break;
                case 2:
                case 3:
                case 4: actionResult = crash(player); break;
                default: actionResult = pinch(player);
            }
        }

        // 2 ~ 5의 거리에서는 원거리 공격 및 접근이 가능함.
        else if (Math.abs(distance) <= 5) {
            int randomNum = (int) (Math.random() * 7);
            switch (randomNum) {
                case 0: actionResult = throwStone(player); break;
                case 1:
                case 2: actionResult = scream(player); break;
                default: actionResult = move(MoveDirection.FORWARD, distance);
            }
        }

        // 6 이상의 거리에서는 접근만 가능함.
        else actionResult = move(MoveDirection.FORWARD, distance);

        return actionResult;
    }
}
