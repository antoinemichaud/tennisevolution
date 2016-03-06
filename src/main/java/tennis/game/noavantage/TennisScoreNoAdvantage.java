package tennis.game.noavantage;

import tennis.game.base.PlayerScore;
import tennis.game.base.TennisScore;

import static tennis.game.base.PlayerScore.GAME;
import static tennis.game.base.PlayerScore.playerScoreFromInt;

public class TennisScoreNoAdvantage implements TennisScore {
    private int player1Score;
    private int player2Score;

    public TennisScoreNoAdvantage(int player1Score, int player2Score) {
        this.player1Score = player1Score;
        this.player2Score = player2Score;
    }

    private static PlayerScore getPlayerScore(int thisPlayerScore, int opponentScore) {
        if (thisPlayerScore >= 4) {
            return GAME;
        }
        return playerScoreFromInt(thisPlayerScore);
    }

    public PlayerScore firstPlayerScore() {
        return getPlayerScore(player1Score, player2Score);
    }

    public PlayerScore secondPlayerScore() {
        return getPlayerScore(player2Score, player1Score);
    }

}
