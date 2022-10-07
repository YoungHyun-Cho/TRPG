import actions.ActionResult;
import actions.ActionType;
import items.Item;
import items.potions.HpPotion;
import items.potions.MpPotion;
import items.weapons.*;
import items.weapons.Throwable;
import subjects.Enemy;
import subjects.Player;

import java.util.Scanner;

public class TRPG {
    private Player player;
    private Enemy[] enemies;
    private Item[] items;

    public TRPG(Player player, Enemy[] enemies, Item[] items) {
        this.player = player;
        this.enemies = enemies;
        this.items = items;
    }

    public boolean gameStart() {

        Scanner scanner = new Scanner(System.in);
        int turn = 0;
        int passTurn = 0;

        Game:
        while (true) {

            Enemy enemy = enemies[(int) (Math.random() * enemies.length)];

            System.out.printf("[안내] 🚨 야생의 %s이(가) 나타났습니다. 🚨\n", enemy.getName());
            System.out.println("*".repeat(70));

            while (enemy.getHp() > 0) {

                String chargeLevelVisualization = "";
                if (player.getWeapon() instanceof Chargeable) {
                    int chargeLevel = ((Chargeable) player.getWeapon()).getCharge();
                    switch (chargeLevel) {
                        case 1: chargeLevelVisualization = "🟥️⬜️⬜️"; break;
                        case 2: chargeLevelVisualization = "🟥️🟥️⬜️"; break;
                        case 3: chargeLevelVisualization = "🟥️🟥️🟥️"; break;
                        default: chargeLevelVisualization = "⬜⬜⬜";
                    }
                }

                String skillState = "";
                if (player.getWeapon() instanceof Chargeable) skillState += String.format("%-13s: %s\n", "🔥 차지 레벨", chargeLevelVisualization);
                if (player.getWeapon() instanceof Stunnable) skillState += String.format("%-13s: %s\n", "⚡️ 카운터 방패 치기", ((Stunnable) player.getWeapon()).isGuarded() ? "사용 가능 ⭕️" : "사용 불가 ❌");
                if (player.getWeapon() instanceof Guardable) skillState += String.format("%-13s: %s\n", "🛡 가드 상태", ((Guardable) player.getWeapon()).isSurvivalMode() ? "ON ✅" : "OFF ⛔️");

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
                
                String playerHpVisualization = "🟥".repeat(playerHp) + "⬜️".repeat(20 - playerHp);
                String playerMpVisualiation = "🟦".repeat(playerMp) + "⬜".repeat(20 - playerMp);
                String playerExpVisualization = "🟨".repeat(playerExp) + "⬜".repeat(20 - playerExp);
                String enemyHpVisualization = "🟥".repeat(enemyHp) + "⬜".repeat(20 - enemyHp);

                System.out.printf("%-13s: %3d/%3d %s\n", "🧟 야생의 " + enemy.getName(), enemy.getHp(), enemy.getMaxHp(), enemyHpVisualization);
                System.out.printf("%-13s: %7d %s\n", "📏 적과의 거리", enemy.getPosition() - player.getPosition(), distanceVisualization);
                System.out.println();
                System.out.printf("%s.%d: %-7s \n", "🏅 Lv", player.getLevel(), player.getName());
                System.out.printf("%-15s: %3d/%3d %s\n", "🩸 HP ", player.getHp(), player.getMaxHp(), playerHpVisualization);
                System.out.printf("%-16s: %3d/%3d %s\n", "🧚‍ MP ", player.getMp(), player.getMaxMp(), playerMpVisualiation);
                System.out.printf("%-15s: %3d/%3d %s\n", "🤺 EXP ", player.getExp(), player.getMaxExp(), playerExpVisualization);
                System.out.println();
                System.out.printf("%-13s: %s\n%s", "🔫 나의 무기", player.getWeapon().getName(), skillState);
                System.out.printf("%-13s: HP포션_%d MP포션_%d\n", "🧪 보유 아이템", player.getHpPotion().getQuantity(), player.getMpPotion().getQuantity());
                System.out.println("*".repeat(70));
                System.out.println();

                // 플레이어 턴
                if (turn++ % 2 == 0) {

                    String action = "";

                    while (true) {


                        System.out.println("무엇을 하시겠습니까?");
                        System.out.print("(1)_앞으로 이동 (2)_뒤로 이동 ");
                        switch (player.getWeapon().getName()) {
                            case "딱밤": System.out.print("(3)_딱밤 때리기 "); break;
                            case "야구 방망이": System.out.print("(3)_휘둘러치기 (4)_차지 (5)_가드 "); break;
                            case "뿅망치 & 냄비뚜껑": System.out.print("(3)_후리기 (4)_카운터 방패 치기 (5)_가드 "); break;
                            case "새총": System.out.print("(3)_돌멩이 발사 (4)_차지 (5)_백스텝 "); break;
                        }

                        System.out.println("(9)_HP포션 사용 (0)_MP포션 사용");

                        action = scanner.nextLine();
                        int distance = enemy.getPosition() - player.getPosition();

                        if (action.equals("1")) {
                            if (distance <= 0) {
                                System.out.println("[🚨] 더 이상 전진할 수 없습니다. 다른 액션을 선택해주세요.");
                                continue;
                            }
                            else {
                                System.out.println("적을 향해 1만큼 전진합니다. ");
                                player.action(ActionType.MOVE_FORWARD);
                                break;
                            }
                        }

                        else if (action.equals("2")) {
                            System.out.println("적으로부터 1만큼 물러납니다. ");
                            player.action(ActionType.MOVE_BACKWARD);
                            break;
                        }

                        else if (action.equals("3")) {
                            player.setMp(player.getMp() - 10);
                            ActionResult actionResult = player.action(ActionType.NORMAL_ATTACK, distance);
                            if (actionResult.getName().equals("OUT_OF_RANGE")) {
                                System.out.println("[🚨] 적이 사정거리 바깥에 있어 공격할 수 없습니다. 액션을 다시 선택해주세요.");
                                continue;
                            }
                            else {
                                System.out.printf("[🚨] 야생의 %s이 데미지 %d을(를) 입었습니다.\n", enemy.getName(), actionResult.getResult());
                                enemy.setHp(enemy.getHp() - actionResult.getResult());
                            }
                            break;
                        }

                        else if (action.equals("4")) {
                            if (player.getMp() < 10) {
                                System.out.println("[🚨] MP가 부족하여 시전할 수 없습니다. 다른 액션을 선택해주세요.");
                                continue;
                            }
                            else {
                                player.setMp(player.getMp() - 10);
                                if (player.getWeapon() instanceof DefaultWeapon) {
                                    System.out.println("잘못 입력하셨습니다. 정확한 번호를 입력해주세요.");
                                    continue;
                                }
                                else if (player.getWeapon() instanceof Stunnable) {
                                    if (((Stunnable) player.getWeapon()).isGuarded()) {
                                        ActionResult actionResult = player.action(ActionType.SKILL_ATTACK, distance);
                                        enemy.setHp(enemy.getHp() - actionResult.getResult());
                                        enemy.setStunned(true);
                                        passTurn = 3;
                                        System.out.printf("[🚨] 야생의 %s이 데미지 %d을(를) 입고 기절했습니다.\n", enemy.getName(), actionResult.getResult());
                                    }
                                    else {
                                        System.out.println("[🚨] 아직 적의 공격을 가드하지 않아 카운터 방패 치기를 사용할 수 없습니다. 다시 입력해주새요.");
                                        continue;
                                    }
                                }
                                else {
                                    ActionResult actionResult = player.action(ActionType.SKILL_ATTACK, distance);
                                    if (actionResult.getResult() == -1) continue;
                                    else System.out.printf("[🚨] 현재 차지 횟수 : %d\n", actionResult.getResult());
                                }
                                break;
                            }
                        }

                        else if (action.equals("5")) {
                            if (player.getMp() < 20) {
                                System.out.println("[🚨] MP가 부족하여 시전할 수 없습니다. 다른 액션을 선택해주세요.");
                                continue;
                            }
                            else {
                                player.setMp(player.getMp() - 20);
                                if (player.getWeapon() instanceof DefaultWeapon) {
                                    System.out.println("잘못 입력하셨습니다. 정확한 번호를 입력해주세요.");
                                    continue;
                                }
                                else {
                                    ActionResult actionResult = player.action(ActionType.SKILL_SURVIVAL);

                                    if (player.getWeapon() instanceof Throwable) {
                                        player.setPosition(0);
                                        enemy.setPosition(actionResult.getResult());
                                    }
                                }
                            }
                            break;
                        }

                        else if (action.equals("9")) {
                            int hpPotionQuality = player.getHpPotion().getQuality();
                            int hpPotionQuantity = player.getHpPotion().getQuantity();
                            if (hpPotionQuantity <= 0) {
                                System.out.println("HP 포션이 없습니다. 다른 액션을 선택해주세요. ");
                                continue;
                            }
                            System.out.printf("HP포션을 사용하여 HP를 %d만큼 회복하였습니다.\n", hpPotionQuality);
                            player.setHp(player.getHp() + hpPotionQuality);
                            player.getHpPotion().setQuantity(hpPotionQuantity - 1);
                            break;
                        }

                        else if (action.equals("0")) {
                            int mpPotionQuality = player.getMpPotion().getQuality();
                            int mpPotionQuantity = player.getMpPotion().getQuantity();
                            if (mpPotionQuantity <= 0) {
                                System.out.println("MP 포션이 없습니다. 다른 액션을 선택해주세요. ");
                                continue;
                            }
                            System.out.printf("MP포션을 사용하여 MP를 %d만큼 회복하였습니다.\n", mpPotionQuality);
                            player.setMp(player.getMp() + mpPotionQuality);
                            player.getMpPotion().setQuantity(mpPotionQuantity - 1);
                            break;
                        }

                        else {
                            System.out.println("잘못 입력하셨습니다. 정확한 번호를 입력해주세요. ");
                        }
                    }

                    if (enemy.getHp() < 0) {

                        System.out.printf("[⭐️] 야생의 %s를 물리쳤습니다.\n", enemy.getName());

                        System.out.printf("[⭐️] 경험치를 %d 획득하였습니다.\n", enemy.getExp());
                        if (player.levelUp(enemy.getExp())) System.out.println("[⭐️] 레벨이 올랐습니다.");

                        int dropItemIndex = (int) (Math.random() * enemy.getDropItemIndex().length);
                        Item dropItem = items[dropItemIndex];

                        System.out.printf("[⭐️] %s를 획득하였습니다. \n", dropItem.getName());

                        if (dropItem instanceof HpPotion) player.getHpPotion().setQuantity(player.getHpPotion().getQuantity() + 1);

                        else if (dropItem instanceof MpPotion) player.getMpPotion().setQuantity(player.getMpPotion().getQuantity() + 1);

                        else if (dropItem instanceof Weapon) {
                            System.out.println("지금 바로 장착하시겠습니까?");
                            System.out.println("(1)_장착하기 (2)_버리기");

                            String changeWeapon = "";

                            do {
                                changeWeapon = scanner.nextLine();
                                if (changeWeapon.equals("1")) {
                                    player.setWeapon((Weapon) dropItem);
                                    System.out.printf("무기를 %s(으)로 교체하였습니다.\n", ((Weapon) dropItem).getName());
                                }
                                else if(changeWeapon.equals("2")) System.out.println("무기를 버렸습니다. ");
                                else System.out.println("잘못 입력하셨습니다. 정확한 번호를 입력해주세요. ");
                            } while (!changeWeapon.equals("1") && !changeWeapon.equals("2"));
                        }



                        System.out.println("탐험을 계속하시겠습니까?");
                        System.out.println("(1)_계속 탐험하기 (2)_탐험 끝내기");
                        String terminate = "";
                        do {
                            terminate = scanner.nextLine();
                            if (terminate.equals("1")) {
                                enemy.setHp(enemy.getMaxHp());
                                enemy.setPosition((int) (Math.random() * 5 + 5));
                                enemy.setStunned(false);
                                player.setPosition(0);
                                continue Game;
                            }
                            else if(terminate.equals("2")) break Game;
                            else System.out.println("잘못 입력하셨습니다. 정확한 번호를 입력해주세요. ");
                        } while (!terminate.equals("1") && !terminate.equals("2"));
                    }
                }

                // 몹의 턴
                else {

                    if (enemy.isStunned() && passTurn > 0) {
                        System.out.printf("[🚨] 야생의 %s이(가) 기절하여 아무것도 하지 못합니다.\n", enemy.getName());
                        System.out.println("*".repeat(70));
                        passTurn--;
                        continue;
                    }

                    try { Thread.sleep(2000); } catch (Exception e) {}

                    ActionResult actionResult = enemy.action(enemy.getPosition() - player.getPosition());

                    // 적이 움직이는 경우
                    if (actionResult.getName().equals("앞으로 전진")) {
                        enemy.setPosition(enemy.getPosition() - 1);
                        System.out.printf("[🚨] 야생의 %s이 플레이어를 향해 1만큼 전진했습니다.\n", enemy.getName());
                    }

                    // 적이 공격하는 경우
                    else {
                        if (player.getWeapon() instanceof Guardable) {
                            // 플레이어가 가드를 올린 경우
                            if (((Guardable)(player.getWeapon())).isSurvivalMode()) {
                                System.out.printf("[🚨] 야생의 %s이 시전한 %s를 가드하여 데미지 %d를 방어하였습니다. \n", enemy.getName(), actionResult.getName(), actionResult.getResult());
                                ((Guardable)(player.getWeapon())).setSurvivalMode(false);
                                if (player.getWeapon() instanceof Stunnable) {
                                    ((Stunnable) player.getWeapon()).setGuarded(true);
                                    System.out.println("[🚨] 카운터 방패 치기를 사용할 수 있습니다.");
                                }
                            }
                            else {
                                player.setHp(player.getHp() - actionResult.getResult());
                                System.out.printf("[🚨] 야생의 %s이 %s를 시전하여 데미지 %d를 입었습니다. \n", enemy.getName(), actionResult.getName(), actionResult.getResult());
                            }
                        }

                        // 플레이어가 가드를 올리지 않은 경우
                        else {
                            player.setHp(player.getHp() - actionResult.getResult());
                            System.out.printf("[🚨] 야생의 %s이 %s를 시전하여 데미지 %d를 입었습니다. \n", enemy.getName(), actionResult.getName(), actionResult.getResult());
                        }


                        // 플레이어 사망시 메서드 종료
                        if (player.getHp() < 0) return false;
                    }
                }
                System.out.println("*".repeat(70));
            }
        }


        return true;
    }


}
