package tennis.game.withlife;

import tennis.game.base.PlayerScore;
import tennis.game.base.TennisScore;

import static tennis.game.base.PlayerScore.playerScoreFromInt;

public class TennisScoreWithLife implements TennisScore {
    private int player1Score;
    private int player2Score;

    public TennisScoreWithLife(int player1Score, int player2Score) {
        this.player1Score = Math.max(0, player1Score - 1);
        this.player2Score = Math.max(0, player2Score - 1);
    }

    private static PlayerScore getPlayerScore(int thisPlayerScore, int opponentScore) {
        if (opponentScore >= 4 || thisPlayerScore >= 4) {
            int playerLead = thisPlayerScore - opponentScore;
            if (playerLead >= 1) {
                return PlayerScore.GAME;
            }
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
