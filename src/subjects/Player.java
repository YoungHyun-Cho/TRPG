package subjects;

import actions.ActionResult;
import actions.ActionResultType;
import items.potions.HpPotion;
import items.potions.MpPotion;
import items.weapons.Guardable;
import items.weapons.Stunnable;
import items.weapons.Weapon;

public class Player implements Movable, Attackable, Harmable, Recoverable {

    private final String name;
    private int hp;
    private int mp;
    private int maxHp;
    private int maxMp;
    private int exp;
    private int maxExp;
    private int level;
    private int position;
    private Weapon weapon;
    private final HpPotion hpPotion;
    private final MpPotion mpPotion;

    public Player(String name, int hp, int mp, int maxHp, int maxMp,
                  int exp, int maxExp, int level, int position,
                  HpPotion hpPotion, MpPotion mpPotion, Weapon weapon) {
        this.name = name;
        this.hp = hp;
        this.mp = mp;
        this.maxHp = maxHp;
        this.maxMp = maxMp;
        this.exp = exp;
        this.maxExp = maxExp;
        this.level = level;
        this.position = position;
        this.hpPotion = hpPotion;
        this.mpPotion = mpPotion;
        this.weapon = weapon;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getMp() {
        return mp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getMaxMp() {
        return maxMp;
    }

    public int getExp() {
        return exp;
    }

    public int getMaxExp() {
        return maxExp;
    }

    public int getLevel() {
        return level;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public HpPotion getHpPotion() {
        return hpPotion;
    }

    public MpPotion getMpPotion() {
        return mpPotion;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public void takeDamage(int damage) {
        this.hp -= damage;
    }

    public boolean levelUp(int exp) {
        this.exp += exp;
        if (this.exp >= maxExp) {
            int restExp = this.exp - maxExp;
            level += 1;
            this.exp = restExp;
            maxHp = maxHp + level * 10;
            maxMp = maxMp + level * 10;
            hp = maxHp;
            mp = maxMp;
            maxExp = maxExp + level * 2;
            return true;
        }
        return false;
    }

    @Override
    public ActionResult recoverHp(int quantity) {

        if (hpPotion.getQuantity() <= 0) return new ActionResult(
                ActionResultType.PLAYER_FAILURE_NOT_ENOUGH_POTION,
                "[????] HP ????????? ????????????. ?????? ????????? ??????????????????."
        );

        if (this.hp + quantity > maxHp) this.hp = maxHp;
        else this.hp += quantity;
        hpPotion.setQuantity(hpPotion.getQuantity() - 1);
        return new ActionResult(
                ActionResultType.PLAYER_SUCCESS,
                String.format("[????] HP????????? ???????????? HP??? %d?????? ?????????????????????.", hpPotion.getQuality())
        );
    }

    @Override
    public ActionResult recoverMp(int quantity) {

        if (mpPotion.getQuantity() <= 0) return new ActionResult(
                ActionResultType.PLAYER_FAILURE_NOT_ENOUGH_POTION,
                "[????] MP ????????? ????????????. ?????? ????????? ??????????????????."
        );

        if (this.mp + quantity > maxMp) this.mp = maxMp;
        else this.mp += quantity;
        mpPotion.setQuantity(mpPotion.getQuantity() - 1);
        return new ActionResult(
                ActionResultType.PLAYER_SUCCESS,
                String.format("[????] MP????????? ???????????? MP??? %d?????? ?????????????????????.", mpPotion.getQuality())
        );
    }

    public ActionResult action(ActionType actionType) {
        switch (actionType) {
            case RECOVER_HP: return recoverHp(hpPotion.getQuality());
            case RECOVER_MP: return recoverMp(mpPotion.getQuality());
            default: return new ActionResult(
                    ActionResultType.PLAYER_FAILURE_WRONG_ACTION_TYPE,
                    "ERR : ????????? ?????? ??????"
            );
        }
    }

    public ActionResult action(ActionType actionType, Enemy enemy, int mpConsumption) {

        int distance = enemy.getPosition() - position;

        switch (actionType) {
            case MOVE_FORWARD: return move(MoveDirection.FORWARD, distance);
            case MOVE_BACKWARD: return move(MoveDirection.BACKWARD, distance);
            case NORMAL_ATTACK: return normalAttack(enemy, distance);
            case SKILL_ATTACK: return skillAttack(enemy, distance, mpConsumption);
            case SKILL_SURVIVAL: return skillSurvival(enemy, mpConsumption);
            default: return new ActionResult(
                    ActionResultType.PLAYER_FAILURE_WRONG_ACTION_TYPE,
                    "ERR : ????????? ?????? ??????"
            );
        }
    }

    public ActionResult move(MoveDirection moveDirection, int distance) {

        if (distance <= 0) return new ActionResult(
                ActionResultType.PLAYER_FAILURE_CANNOT_MOVE_MORE,
                "[????] ??? ?????? ????????? ??? ????????????. ?????? ????????? ??????????????????."
        );
        else if (distance > 10) return new ActionResult(
                ActionResultType.PLAYER_FAILURE_CANNOT_MOVE_MORE,
                "[????] ??? ?????? ????????? ??? ????????????. ?????? ????????? ??????????????????."
        );
        else {
            switch (moveDirection) {
                case FORWARD:
                    position += 1;
                    return new ActionResult(
                            ActionResultType.PLAYER_SUCCESS,
                            "[????] ?????? ?????? 1?????? ???????????????."
                    );
                case BACKWARD:
                    position -= 1;
                    return new ActionResult(
                            ActionResultType.PLAYER_SUCCESS,
                            "[????] ??????????????? 1?????? ???????????????. "
                    );
                default:
                    return new ActionResult(
                            ActionResultType.PLAYER_FAILURE,
                            "ERR : ????????? ?????? ??????"
                    );
            }
        }
    }

    @Override
    public ActionResult normalAttack(Enemy enemy, int distance) {
        return weapon.normalAttack(enemy, distance);
    }

    @Override
    public ActionResult skillAttack(Enemy enemy, int distance, int mpConsumption) {

        if (mp < mpConsumption) return new ActionResult(
                ActionResultType.PLAYER_FAILURE_NOT_ENOUGH_MP,
                "[????] MP??? ???????????? ????????? ??? ????????????. ?????? ????????? ??????????????????."
        );
        else {
            mp -= mpConsumption;
            return weapon.skillAttack(enemy, distance);
        }
    }

    @Override
    public ActionResult skillSurvival(Enemy enemy, int mpConsumption) {

        if (mp < mpConsumption) return new ActionResult(
                ActionResultType.PLAYER_FAILURE_NOT_ENOUGH_MP,
                "[????] MP??? ???????????? ????????? ??? ????????????. ?????? ????????? ??????????????????."
        );
        else {
            mp -= mpConsumption;
            return weapon.skillSurvival(this, enemy);
        }
    }

    public ActionResult guardEnemyAttack(Guardable guardableWeapon, String enemyName, String enemyAttackName, int damage) {
        guardableWeapon.setSurvivalMode(false);
        if (guardableWeapon instanceof Stunnable) ((Stunnable) guardableWeapon).setGuarded(true);
        return new ActionResult(
                ActionResultType.ENEMY_FAILURE_PLAYER_GUARDED,
                String.format("[????] ????????? %s??? ????????? %s??? ???????????? ????????? %d???(???) ?????????????????????.", enemyName, enemyAttackName, damage)
        );
    }
}