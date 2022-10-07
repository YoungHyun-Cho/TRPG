package subjects;

import actions.ActionResult;
import actions.ActionType;
import items.potions.HpPotion;
import items.potions.MpPotion;
import items.weapons.Weapon;

public class Player implements Movable {

    private String name;
    private int hp;
    private int mp;

    private int maxHp;
    private int maxMp;
    private int exp;
    private int maxExp;
    private int level;
    private int position;
    private HpPotion hpPotion;
    private MpPotion mpPotion;
    private Weapon weapon;

    public Player(String name, int hp, int mp, int maxHp, int maxMp, int exp, int maxExp, int level, int position, HpPotion hpPotion, MpPotion mpPotion, Weapon weapon) {
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

    public void setHp(int hp) {
        if (hp > maxHp) this.hp = maxHp;
        else this.hp = hp;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getMaxMp() {
        return maxMp;
    }

    public void setMaxMp(int maxMp) {
        this.maxMp = maxMp;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getMaxExp() {
        return maxExp;
    }

    public void setMaxExp(int maxExp) {
        this.maxExp = maxExp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public void setHpPotion(HpPotion hpPotion) {
        this.hpPotion = hpPotion;
    }

    public MpPotion getMpPotion() {
        return mpPotion;
    }

    public void setMpPotion(MpPotion mpPotion) {
        this.mpPotion = mpPotion;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
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

    public ActionResult move(MoveDirection moveDirection) {
        switch (moveDirection) {
            case FORWARD: position += 1; break;
            case BACKWARD: position -= 1; break;
        }
        return new ActionResult("앞으로 전진", 0);
    }

    public ActionResult action(ActionType actionType, int distance) {

        ActionResult actionResult;

        switch (actionType) {
            case NORMAL_ATTACK: actionResult = weapon.normalAttack(distance); break;
            case SKILL_ATTACK: actionResult = weapon.skillAttack(distance); break;
            default: actionResult = new ActionResult("SOMETHING_WENT_WRONG", -2);
        }

//        else actionResult = new ActionResult("OUT_OF_RANGE", -3);
//        노말어택과 스킬 어택에서 사정거리 검사 직접해야 함. -> 차지는 사정거리에 상관없이 가능하지만, 방패치기는 사정거리에 영향 받음. -> 따로 해줘야함.
        return actionResult;
    }

    public ActionResult action(ActionType actionType) {

        ActionResult actionResult;

        switch (actionType) {
            case MOVE_FORWARD: actionResult = move(MoveDirection.FORWARD); break;
            case MOVE_BACKWARD: actionResult = move(MoveDirection.BACKWARD); break;
            case SKILL_SURVIVAL: actionResult = weapon.skillSurvival(); break;
            default: actionResult = new ActionResult("SOMETHING_WENT_WRONG", -2);
        }

        return actionResult;
    }
}