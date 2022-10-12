import items.Item;
import items.potions.HpPotion;
import items.potions.MpPotion;
import items.weapons.BaseBallBat;
import items.weapons.DefaultWeapon;
import items.weapons.HammerAndShield;
import items.weapons.SlingShot;
import subjects.Enemy;
import subjects.Player;

public class GameConfig {

    String playerName;

    static class MpConsumption {

        private final int move;
        private final int normalAttack;
        private final int skillAttack ;
        private final int skillSurvival ;

        public MpConsumption(int move, int normalAttack, int skillAttack, int skillSurvival) {
            this.move = move;
            this.normalAttack = normalAttack;
            this.skillAttack = skillAttack;
            this.skillSurvival = skillSurvival;
        }

        public int getMove() {
            return move;
        }

        public int getNormalAttack() {
            return normalAttack;
        }

        public int getSkillAttack() {
            return skillAttack;
        }

        public int getSkillSurvival() {
            return skillSurvival;
        }
    }

    public GameConfig(String playerName) {
        this.playerName = playerName;
    }

    public Player player() {
        return new Player(
                playerName, 100, 100, 100, 100, 0, 10, 1, 0,
                new HpPotion(100, 3),
                new MpPotion(100, 3),
                new DefaultWeapon("딱밤", 40, 1)
        );
    }

    public Enemy[] enemies() {
        return new Enemy[] {
                new Enemy("조영현", 100, 100, 10, 3, (int) (Math.random() * 5 + 5), new int[] { 0, 2, 3, 4}),
                new Enemy("나태웅", 150, 150, 15, 5, (int) (Math.random() * 5 + 5), new int[] { 1, 2, 3, 4 }),
                new Enemy("김요한", 150, 150, 15, 5, (int) (Math.random() * 5 + 5), new int[] { 1, 2, 3, 4 }),
                new Enemy("구민상", 200, 200, 20, 10, (int) (Math.random() * 5 + 5), new int[] { 0, 1, 2, 3, 4 })
        };
    }

    public Item[] items() {
        return new Item[] {
                new HpPotion(100, 1),
                new MpPotion(100, 1),
                new BaseBallBat("야구 방망이", 50, 3, 0, false),
                new HammerAndShield("뿅망치 & 냄비뚜껑", 20, 2, false, false),
                new SlingShot("새총", 20, 10, 0)
        };
    }

    public MpConsumption mpConsumption() {
        return new MpConsumption(0, 0, 10, 20);
    }
}
