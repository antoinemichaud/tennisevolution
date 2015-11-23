package tennis.game.services;

public enum PlayerScore {
    LOVE,
    FIFTEEN,
    THIRTY,
    FORTY,
    ADVANTAGE,
    GAME;

    public static PlayerScore playerScoreFromInt(int score) {
        if (score == 0)
            return LOVE;
        if (score == 1)
            return FIFTEEN;
        if (score == 2)
            return THIRTY;
        return FORTY;
    }
}
