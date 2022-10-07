package subjects;

import actions.ActionResult;

public class Enemy implements Movable {

    private String name;
    private int hp;
    private int maxHp;
    private int damage;
    private int exp;
    private int position;
    private boolean stunned;
    private int[] dropItemIndex;

    public Enemy(String name, int hp, int maxHp, int damage, int exp, int position, boolean stunned, int[] dropItemIndex) {
        this.name = name;
        this.hp = hp;
        this.maxHp = maxHp;
        this.damage = damage;
        this.exp = exp;
        this.position = position;
        this.stunned = stunned;
        this.dropItemIndex = dropItemIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public boolean isStunned() {
        return stunned;
    }

    public void setStunned(boolean stunned) {
        this.stunned = stunned;
    }

    public int[] getDropItemIndex() {
        return dropItemIndex;
    }

    public void setDropItemIndex(int[] dropItemIndex) {
        this.dropItemIndex = dropItemIndex;
    }

    // 꼬집기
    public ActionResult pinch() {
        return new ActionResult("꼬집기🤏", damage + (int) (Math.random() * 3));
    }

    // 몸통 박치기
    public ActionResult crash() {
        return new ActionResult("몸통 박치기💥", damage + (int) (Math.random() * 5));
    }

    // 깨물기
    public ActionResult bite() {
        return new ActionResult("깨물기🦷", damage + (int) (Math.random() * 10));
    }

    // 소리 지르기
    public ActionResult scream() {
        return new ActionResult("소리 지르기😱", damage + (int) (Math.random() * 3));

    }

    // 돌 던지기
    public ActionResult throwStone() {
        return new ActionResult("돌 던지기🪨", damage + (int) (Math.random() * 5));
    }

    @Override
    public ActionResult move(MoveDirection moveDirection) {
        return new ActionResult("앞으로 전진", 0);
    }

    public ActionResult action(int distance) {

        ActionResult actionResult;

        // 1 이하의 거리에서는 근거리 공격만 가능함.
        if (Math.abs(distance) <= 1) {
            int randomNum = (int) (Math.random() * 9);
            switch (randomNum) {
                case 0:
                case 1: actionResult = bite(); break;
                case 2:
                case 3:
                case 4: actionResult = crash(); break;
                default: actionResult = pinch();
            }
        }

        // 2 ~ 5의 거리에서는 원거리 공격 및 접근이 가능함.
        else if (Math.abs(distance) <= 5) {
            int randomNum = (int) (Math.random() * 7);
            switch (randomNum) {
                case 0: actionResult = throwStone(); break;
                case 1:
                case 2: actionResult = scream(); break;
                default: actionResult = move(MoveDirection.FORWARD);
            }
        }

        // 6 이상의 거리에서는 접근만 가능함.
        else actionResult = move(MoveDirection.FORWARD);

        return actionResult;
    }
}
