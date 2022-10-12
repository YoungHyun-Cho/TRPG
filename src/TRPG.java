import actions.ActionResult;
import actions.ActionResultType;
import subjects.ActionType;
import items.Item;
import items.potions.HpPotion;
import items.potions.MpPotion;
import items.weapons.*;
import subjects.Enemy;
import subjects.Player;
import java.util.Scanner;

public class TRPG {
    private final Player player;
    private final Enemy[] enemies;
    private final Item[] items;
    private final GameConfig.MpConsumption mpConsumption;
    Scanner scanner = new Scanner(System.in);

    public TRPG(Player player, Enemy[] enemies, Item[] items, GameConfig.MpConsumption mpComsumption) {
        this.player = player;
        this.enemies = enemies;
        this.items = items;
        this.mpConsumption = mpComsumption;
    }

    public boolean gameStart() {

        int turn = 0;

        Game:
        while (true) {

            Enemy enemy = enemies[(int) (Math.random() * enemies.length)];

            System.out.printf("🚨 야생의 %s🧟이(가) 나타났습니다. 🚨\n", enemy.getName());
            System.out.println("*".repeat(70));

            while (enemy.getHp() > 0) {

                printBattleStatus(enemy);

                // 플레이어 턴
                if (turn++ % 2 == 0) {

                    String action = "";

                    while (true) {
                        printPlayerActionList();
                        action = scanner.nextLine();
                        ActionResult actionResult;

                        switch (action) {
                            case "1": actionResult = player.action(ActionType.MOVE_FORWARD, enemy, mpConsumption.getMove()); break;
                            case "2": actionResult = player.action(ActionType.MOVE_BACKWARD, enemy, mpConsumption.getMove()); break;
                            case "3": actionResult = player.action(ActionType.NORMAL_ATTACK, enemy, mpConsumption.getNormalAttack()); break;
                            case "4": actionResult = player.action(ActionType.SKILL_ATTACK, enemy, mpConsumption.getSkillAttack()); break;
                            case "5": actionResult = player.action(ActionType.SKILL_SURVIVAL, enemy, mpConsumption.getSkillSurvival()); break;
                            case "9": actionResult = player.action(ActionType.RECOVER_HP);break;
                            case "0": actionResult = player.action(ActionType.RECOVER_MP); break;
                            case "+": break Game;
                            default: actionResult = new ActionResult(
                                    ActionResultType.PLAYER_FAILURE_WRONG_NUMBER,
                                    "[🚫] 잘못 입력하셨습니다. 정확한 번호를 입력해주세요.\n"
                            );
                        }

                        System.out.println(actionResult.getMessage());
                        if (actionResult.getActionResultType() == ActionResultType.PLAYER_SUCCESS) break;
                    }

                    try { Thread.sleep(2000); } catch (Exception e) {}

                    if (enemy.getHp() < 0) {
                        printBattleResult(enemy);
                        try { Thread.sleep(2000); } catch (Exception e) {}
                        continue Game;
                    }
                    System.out.println("*".repeat(70));
                }

                // 몹의 턴
                else {
                    ActionResult actionResult = enemy.action(player);
                    System.out.println(actionResult.getMessage());
                    if (actionResult.getActionResultType() == ActionResultType.ENEMY_FAILURE_IS_STUNNED) continue;
                    try { Thread.sleep(2000); } catch (Exception e) {}
                    if (player.getHp() < 0) return false;
                    System.out.println("*".repeat(70));
                }
            }
        }
        return true;
    }

    public void printBattleStatus(Enemy enemy) {

        String chargeLevelVisualization = "";
        if (player.getWeapon() instanceof Chargeable) {
            int chargeLevel = ((Chargeable) player.getWeapon()).getCharge();
            switch (chargeLevel) {
                case 1:
                    chargeLevelVisualization = "🟥️⬜️⬜️";
                    break;
                case 2:
                    chargeLevelVisualization = "🟥️🟥️⬜️";
                    break;
                case 3:
                    chargeLevelVisualization = "🟥️🟥️🟥️";
                    break;
                default:
                    chargeLevelVisualization = "⬜⬜⬜";
            }
        }

        String skillState = "";
        if (player.getWeapon() instanceof Chargeable)
            skillState += String.format("%-13s: %s\n", "🔥 차지 레벨", chargeLevelVisualization);
        if (player.getWeapon() instanceof Stunnable)
            skillState += String.format("%-13s: %s\n", "⚡️ 카운터 방패 치기", ((Stunnable) player.getWeapon()).isGuarded() ? "사용 가능 ⭕️" : "사용 불가 ❌");
        if (player.getWeapon() instanceof Guardable)
            skillState += String.format("%-13s: %s\n", "🛡 가드 상태", ((Guardable) player.getWeapon()).isSurvivalMode() ? "ON ✅" : "OFF ⛔️");

        int attackRange = player.getWeapon().getAttackRange();
        int playerEnemydistance = enemy.getPosition() - player.getPosition();
        int distanceWithoutRange = playerEnemydistance - player.getWeapon().getAttackRange();
        if (distanceWithoutRange < 0) distanceWithoutRange = 0;
        if (attackRange > playerEnemydistance) attackRange = playerEnemydistance;

        String distanceVisualization = String.format("🧑🏻‍🦰%s%s🧟", "🔴".repeat(attackRange), "⚪️".repeat(distanceWithoutRange));

        int playerHp = 20 * player.getHp() / player.getMaxHp();
        int playerMp = 20 * player.getMp() / player.getMaxMp();
        int playerExp = 20 * player.getExp() / player.getMaxExp();
        int enemyHp = 20 * enemy.getHp() / enemy.getMaxHp();

        String playerHpVisualization = "🟥".repeat(playerHp < 0 ? 0 : playerHp) + "⬜️".repeat(20 - playerHp < 0 ? 0 : 20 - playerHp);
        String playerMpVisualization = "🟦".repeat(playerMp < 0 ? 0 : playerMp) + "⬜".repeat(20 - playerMp < 0 ? 0 : 20 - playerMp);
        String playerExpVisualization = "🟨".repeat(playerExp < 0 ? 0 : playerExp) + "⬜".repeat(20 - playerExp < 0 ? 0 : 20 - playerExp);
        String enemyHpVisualization = "🟥".repeat(enemyHp < 0 ? 0 : enemyHp) + "⬜".repeat(20 - enemyHp < 0 ? 0 : 20 - enemyHp);

        System.out.printf("%-13s: %3d/%3d %s\n", "🧟 야생의 " + enemy.getName(), enemy.getHp(), enemy.getMaxHp(), enemyHpVisualization);
        System.out.printf("%-13s: %7d %s\n", "📏 적과의 거리", enemy.getPosition() - player.getPosition(), distanceVisualization);
        System.out.println();
        System.out.printf("%s.%d: %-7s \n", "🏅 Lv", player.getLevel(), player.getName());
        System.out.printf("%-15s: %3d/%3d %s\n", "🩸 HP ", player.getHp(), player.getMaxHp(), playerHpVisualization);
        System.out.printf("%-16s: %3d/%3d %s\n", "🧚‍ MP ", player.getMp(), player.getMaxMp(), playerMpVisualization);
        System.out.printf("%-15s: %3d/%3d %s\n", "🤺 EXP ", player.getExp(), player.getMaxExp(), playerExpVisualization);
        System.out.println();
        System.out.printf("%-13s: %s\n%s", "🔫 나의 무기", player.getWeapon().getName(), skillState);
        System.out.printf("%-13s: HP포션_%d MP포션_%d\n", "🧪 보유 아이템", player.getHpPotion().getQuantity(), player.getMpPotion().getQuantity());
        System.out.println("*".repeat(70));
        System.out.println();
    }

    public void printPlayerActionList() {

        System.out.println("[⭐️] 무엇을 하시겠습니까?");
        System.out.println("(1)_앞으로 이동 (2)_뒤로 이동 ");
        switch (player.getWeapon().getName()) {
            case "딱밤": System.out.println("(3)_딱밤 때리기 "); break;
            case "야구 방망이": System.out.println("(3)_휘둘러치기 (4)_차지[MP-10] (5)_가드[MP-20] "); break;
            case "뿅망치 & 냄비뚜껑": System.out.println("(3)_후리기 (4)_카운터 방패 치기[MP-10] (5)_가드[MP-20] "); break;
            case "새총": System.out.println("(3)_돌멩이 발사 (4)_차지 [MP-10] (5)_백스텝 [MP-20] "); break;
        }
        System.out.println("(9)_HP포션 사용 [HP+100] (0)_MP포션 사용 [MP+100] (+)_게임 종료");
    }

    public void printBattleResult(Enemy enemy) {

        int dropItemIndex = (int) (Math.random() * enemy.getDropItemIndex().length);
        Item dropItem = items[dropItemIndex];

        System.out.printf("[⭐️] 야생의 %s를 물리쳤습니다.\n", enemy.getName());
        System.out.printf("[⭐️] 경험치를 %d 획득하였습니다.\n", enemy.getExp());
        if (player.levelUp(enemy.getExp())) System.out.println("[⭐️] 레벨이 올랐습니다.");
        System.out.printf("[⭐️] %s를 획득하였습니다.\n", dropItem.getName());

        if (dropItem instanceof HpPotion)
            player.getHpPotion().setQuantity(player.getHpPotion().getQuantity() + 1);

        else if (dropItem instanceof MpPotion)
            player.getMpPotion().setQuantity(player.getMpPotion().getQuantity() + 1);

        else if (dropItem instanceof Weapon) {
            System.out.println("지금 바로 장착하시겠습니까?");
            System.out.println("(1)_장착하기 (2)_버리기");

            String changeWeapon = "";

            do {
                changeWeapon = scanner.nextLine();
                switch (changeWeapon) {
                    case "1":
                        player.setWeapon((Weapon) dropItem);
                        System.out.printf("무기를 %s(으)로 교체하였습니다.\n", ((Weapon) dropItem).getName());
                        break;
                    case "2":
                        System.out.println("무기를 버렸습니다.");
                        break;
                    default:
                        System.out.println("[🚫] 잘못 입력하셨습니다. 정확한 번호를 입력해주세요. ");
                }
            } while (!changeWeapon.equals("1") && !changeWeapon.equals("2"));
        }

        System.out.println("[⭐️] 탐험을 계속합니다.\n");

        player.setPosition(0);
        enemy.setHp(enemy.getMaxHp());
        enemy.setPassTurn(0);
        enemy.setPosition((int) (Math.random() * 5 + 5));
    }
}
