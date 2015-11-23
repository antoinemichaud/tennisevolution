package tennis.game.noavantage;


import static tennis.game.noavantage.PlayerScore.GAME;
import static tennis.game.noavantage.PlayerScore.playerScoreFromInt;

public class TennisScore {
    private int player1Score;
    private int player2Score;

    public TennisScore(int player1Score, int player2Score) {
        this.player1Score = player1Score;
        this.player2Score = player2Score;
    }

    public PlayerScore firstPlayerScore() {
        return getPlayerScore(player1Score, player2Score);
    }

    public PlayerScore secondPlayerScore() {
        return getPlayerScore(player2Score, player1Score);
    }

    private static PlayerScore getPlayerScore(int thisPlayerScore, int opponentScore) {
        if (opponentScore >= 4 || thisPlayerScore >= 4) {
            return GAME;
        }
        return playerScoreFromInt(thisPlayerScore);
    }

}
