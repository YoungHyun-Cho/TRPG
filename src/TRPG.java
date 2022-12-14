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

            System.out.printf("π¨ μΌμμ %sπ§μ΄(κ°) λνλ¬μ΅λλ€. π¨\n", enemy.getName());

            while (enemy.getHp() > 0) {

                // νλ μ΄μ΄ ν΄
                if (turn++ % 2 == 0) {
                    System.out.println("*".repeat(70));
                    printBattleStatus(enemy);

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
                                    "[π«] μλͺ» μλ ₯νμ¨μ΅λλ€. μ νν λ²νΈλ₯Ό μλ ₯ν΄μ£ΌμΈμ."
                            );
                        }

                        System.out.println(actionResult.getMessage());
                        if (actionResult.getActionResultType() == ActionResultType.PLAYER_SUCCESS) break;
                    }

//                    try { Thread.sleep(2000); } catch (Exception e) {}

                    if (enemy.getHp() < 0) {
                        printBattleResult(enemy);
                        try { Thread.sleep(2000); } catch (Exception e) {}
                        continue Game;
                    }
//                    System.out.println("*".repeat(70));
                }

                // λͺΉμ ν΄
                else {
                    ActionResult actionResult = enemy.action(player);
                    System.out.println(actionResult.getMessage());
//                    System.out.println("*".repeat(70));
                    if (actionResult.getActionResultType() == ActionResultType.ENEMY_FAILURE_IS_STUNNED) continue;
                    if (player.getHp() < 0) return false;
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
                    chargeLevelVisualization = "π₯οΈβ¬οΈβ¬οΈ";
                    break;
                case 2:
                    chargeLevelVisualization = "π₯οΈπ₯οΈβ¬οΈ";
                    break;
                case 3:
                    chargeLevelVisualization = "π₯οΈπ₯οΈπ₯οΈ";
                    break;
                default:
                    chargeLevelVisualization = "β¬β¬β¬";
            }
        }

        String skillState = "";
        if (player.getWeapon() instanceof Chargeable)
            skillState += String.format("%-13s: %s\n", "π₯ μ°¨μ§ λ λ²¨", chargeLevelVisualization);
        if (player.getWeapon() instanceof Stunnable)
            skillState += String.format("%-13s: %s\n", "β‘οΈ μΉ΄μ΄ν° λ°©ν¨ μΉκΈ°", ((Stunnable) player.getWeapon()).isGuarded() ? "μ¬μ© κ°λ₯ β­οΈ" : "μ¬μ© λΆκ° β");
        if (player.getWeapon() instanceof Guardable)
            skillState += String.format("%-13s: %s\n", "π‘ κ°λ μν", ((Guardable) player.getWeapon()).isSurvivalMode() ? "ON β" : "OFF βοΈ");

        int attackRange = player.getWeapon().getAttackRange();
        int playerEnemyDistance = enemy.getPosition() - player.getPosition();
        int distanceWithoutRange = playerEnemyDistance - player.getWeapon().getAttackRange();
        if (distanceWithoutRange < 0) distanceWithoutRange = 0;
        if (attackRange > playerEnemyDistance) attackRange = playerEnemyDistance;

        String distanceVisualization = String.format("π§π»βπ¦°%s%sπ§", "π΄".repeat(attackRange), "βͺοΈ".repeat(distanceWithoutRange));

        int playerHp = 20 * player.getHp() / player.getMaxHp();
        int playerMp = 20 * player.getMp() / player.getMaxMp();
        int playerExp = 20 * player.getExp() / player.getMaxExp();
        int enemyHp = 20 * enemy.getHp() / enemy.getMaxHp();

        String playerHpVisualization = "π₯".repeat(playerHp < 0 ? 0 : playerHp) + "β¬οΈ".repeat(20 - playerHp < 0 ? 0 : 20 - playerHp);
        String playerMpVisualization = "π¦".repeat(playerMp < 0 ? 0 : playerMp) + "β¬".repeat(20 - playerMp < 0 ? 0 : 20 - playerMp);
        String playerExpVisualization = "π¨".repeat(playerExp < 0 ? 0 : playerExp) + "β¬".repeat(20 - playerExp < 0 ? 0 : 20 - playerExp);
        String enemyHpVisualization = "π₯".repeat(enemyHp < 0 ? 0 : enemyHp) + "β¬".repeat(20 - enemyHp < 0 ? 0 : 20 - enemyHp);

        System.out.printf("%-13s: %3d/%3d %s\n", "π§ μΌμμ " + enemy.getName(), enemy.getHp(), enemy.getMaxHp(), enemyHpVisualization);
        System.out.printf("%-13s: %7d %s\n", "π μ κ³Όμ κ±°λ¦¬", enemy.getPosition() - player.getPosition(), distanceVisualization);
        System.out.println();
        System.out.printf("%s.%d: %-7s \n", "π Lv", player.getLevel(), player.getName());
        System.out.printf("%-15s: %3d/%3d %s\n", "π©Έ HP ", player.getHp(), player.getMaxHp(), playerHpVisualization);
        System.out.printf("%-16s: %3d/%3d %s\n", "π§β MP ", player.getMp(), player.getMaxMp(), playerMpVisualization);
        System.out.printf("%-15s: %3d/%3d %s\n", "π€Ί EXP ", player.getExp(), player.getMaxExp(), playerExpVisualization);
        System.out.println();
        System.out.printf("%-13s: %s\n%s", "π« λμ λ¬΄κΈ°", player.getWeapon().getName(), skillState);
        System.out.printf("%-13s: HPν¬μ_%d MPν¬μ_%d\n", "π§ͺ λ³΄μ  μμ΄ν", player.getHpPotion().getQuantity(), player.getMpPotion().getQuantity());
        System.out.println("*".repeat(70));
        System.out.println();
    }

    public void printPlayerActionList() {

        System.out.println("[β­οΈ] λ¬΄μμ νμκ² μ΅λκΉ?");
        System.out.println("    (1)_μμΌλ‘ μ΄λ (2)_λ€λ‘ μ΄λ ");
        switch (player.getWeapon().getName()) {
            case "λ±λ°€": System.out.println("    (3)_λ±λ°€ λλ¦¬κΈ° "); break;
            case "μΌκ΅¬ λ°©λ§μ΄": System.out.println("    (3)_νλλ¬μΉκΈ° (4)_μ°¨μ§[MP-10] (5)_κ°λ[MP-20] "); break;
            case "λΏλ§μΉ & λλΉλκ»": System.out.println("    (3)_νλ¦¬κΈ° (4)_μΉ΄μ΄ν° λ°©ν¨ μΉκΈ°[MP-10] (5)_κ°λ[MP-20] "); break;
            case "μμ΄": System.out.println("    (3)_λλ©©μ΄ λ°μ¬ (4)_μ°¨μ§ [MP-10] (5)_λ°±μ€ν [MP-20] "); break;
        }
        System.out.println("    (9)_HPν¬μ μ¬μ© [HP+100] (0)_MPν¬μ μ¬μ© [MP+100] (+)_κ²μ μ’λ£");
    }

    public void printBattleResult(Enemy enemy) {

        int dropItemIndex = (int) (Math.random() * enemy.getDropItemIndex().length);
        Item dropItem = items[dropItemIndex];

        System.out.printf("[β­οΈ] μΌμμ %sλ₯Ό λ¬Όλ¦¬μ³€μ΅λλ€.\n", enemy.getName());
        System.out.printf("[β­οΈ] κ²½νμΉλ₯Ό %d νλνμμ΅λλ€.\n", enemy.getExp());
        if (player.levelUp(enemy.getExp())) System.out.println("[β­οΈ] λ λ²¨μ΄ μ¬λμ΅λλ€.");
        System.out.printf("[β­οΈ] %sλ₯Ό νλνμμ΅λλ€.\n", dropItem.getName());

        if (dropItem instanceof HpPotion)
            player.getHpPotion().setQuantity(player.getHpPotion().getQuantity() + 1);

        else if (dropItem instanceof MpPotion)
            player.getMpPotion().setQuantity(player.getMpPotion().getQuantity() + 1);

        else if (dropItem instanceof Weapon) {
            System.out.println("[β­οΈ] μ§κΈ λ°λ‘ μ₯μ°©νμκ² μ΅λκΉ?");
            System.out.println("    (1)_μ₯μ°©νκΈ° (2)_λ²λ¦¬κΈ°");

            String changeWeapon = "";

            do {
                changeWeapon = scanner.nextLine();
                switch (changeWeapon) {
                    case "1":
                        player.setWeapon((Weapon) dropItem);
                        System.out.printf("[β­οΈ] λ¬΄κΈ°λ₯Ό %s(μΌ)λ‘ κ΅μ²΄νμμ΅λλ€.\n", ((Weapon) dropItem).getName());
                        break;
                    case "2":
                        System.out.println("[β­οΈ] λ¬΄κΈ°λ₯Ό λ²λ Έμ΅λλ€.");
                        break;
                    default:
                        System.out.println("[π«] μλͺ» μλ ₯νμ¨μ΅λλ€. μ νν λ²νΈλ₯Ό μλ ₯ν΄μ£ΌμΈμ. ");
                }
            } while (!changeWeapon.equals("1") && !changeWeapon.equals("2"));
        }

        System.out.println("[β­οΈ] ννμ κ³μν©λλ€.\n");

        player.setPosition(0);
        enemy.setHp(enemy.getMaxHp());
        enemy.setPassTurn(0);
        enemy.setPosition((int) (Math.random() * 5 + 5));
    }
}
