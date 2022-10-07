public class GameConfig {

}


/*
* 리팩터링
* 1. 무기 인터페이스 -> Chargeable, Guardable, Throwable
* 2. 플레이어 및 에너미 인터페이스 -> subjects.Movable, Attackable
* 3. Config로 게임 설정 옮기기
* */