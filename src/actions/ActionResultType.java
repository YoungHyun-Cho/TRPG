package actions;

public enum ActionResultType {
    PLAYER_SUCCESS,

    PLAYER_FAILURE,
    PLAYER_FAILURE_CANNOT_MOVE_MORE,
    PLAYER_FAILURE_NOT_ENOUGH_MP,
    PLAYER_FAILURE_WRONG_NUMBER,
    PLAYER_FAILURE_NOT_GUARDED_YET,
    PLAYER_FAILURE_OUT_OF_RANGE,
    PLAYER_FAILURE_WRONG_ACTION_TYPE,
    PLAYER_FAILURE_CANNOT_CHARGE_MORE,
    PLAYER_FAILURE_NOT_ENOUGH_POTION,

    ENEMY_SUCCESS_MOVE_FORWARD,
    ENEMY_SUCCESS_ATTACK,
    ENEMY_FAILURE_IS_STUNNED,
    ENEMY_FAILURE_PLAYER_GUARDED,
}