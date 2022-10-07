import items.Item;
import items.potions.HpPotion;
import items.potions.MpPotion;
import items.weapons.DefaultWeapon;
import items.weapons.BaseBallBat;
import items.weapons.HammerAndShield;
import items.weapons.SlingShot;
import subjects.Enemy;
import subjects.Player;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("############################################################");
        System.out.println("TRPG : 야생의 코드스테이츠 탐험하기");
        System.out.println("############################################################");
        System.out.print("이름을 입력해주세요 : ");
        String playerName = scanner.nextLine();
        System.out.println("게임을 시작합니다. ");

        Player player = new Player(
                playerName, 100, 100, 100, 100, 0, 10, 1, 0,
                new HpPotion(100, 3),
                new MpPotion(100, 3),
//                new DefaultWeapon()
                new SlingShot()
        );

        Enemy[] enemies = {
                new Enemy("조영현", 100, 100, 10, 3, (int) (Math.random() * 5 + 5), false, new int[] { 0, 2, 3, 4}),
                new Enemy("나태웅", 150, 150, 15, 5, (int) (Math.random() * 5 + 5), false, new int[] { 1, 2, 3, 4 }),
                new Enemy("김요한", 150, 150, 15, 5, (int) (Math.random() * 5 + 5), false, new int[] { 1, 2, 3, 4 }),
                new Enemy("구민상", 200, 200, 20, 10, (int) (Math.random() * 5 + 5), false, new int[] { 0, 1, 2, 3, 4 })
        };

        Item[] items = {
                new HpPotion(100, 1),
                new MpPotion(100, 1),
                new BaseBallBat(),
                new HammerAndShield(),
                new SlingShot()
        };

        TRPG trpgGame = new TRPG(player, enemies, items);
        if (trpgGame.gameStart()) System.out.println(playerName + " 생존");
        else System.out.println(playerName + " 사망");
        System.out.println("게임 종료");

    }
}
