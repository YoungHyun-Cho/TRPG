import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("############################################################");
        System.out.println("TRPG : 야생의 코드스테이츠 탐험하기");
        System.out.print("이름을 입력해주세요 : ");
        String playerName = scanner.nextLine();
        System.out.println("잠시 후 게임을 시작합니다.");
        try { Thread.sleep(1000); } catch (Exception e) {}
        System.out.println("############################################################");

        GameConfig gameConfig = new GameConfig(playerName);

        TRPG trpgGame = new TRPG(gameConfig.player(), gameConfig.enemies(), gameConfig.items(), gameConfig.mpConsumption());

        if (trpgGame.gameStart()) System.out.printf("🏆 %s 생존\n", playerName);
        else System.out.printf("🪦 %s 사망\n", playerName);

        System.out.println("게임을 종료합니다. ");
    }
}
