package tennis;

import static tennis.PlayerScore.playerScoreFromInt;

public class RegularScore extends TennisScore {
    private int player1Score;
    private int player2Score;

    public RegularScore(int player1Score, int player2Score) {
        this.player1Score = player1Score;
        this.player2Score = player2Score;
    }

    public PlayerScore firstPlayerScore() {
        return playerScoreFromInt(player1Score);
    }

    public PlayerScore secondPlayerScore() {
        return playerScoreFromInt(player2Score);
    }

}
