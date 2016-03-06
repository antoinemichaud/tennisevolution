package tennis.game.classic;

import tennis.game.base.PlayerScore;
import tennis.game.base.TennisScore;

import static tennis.game.base.PlayerScore.playerScoreFromInt;

public class TennisScoreClassic implements TennisScore {
    private int player1Score;
    private int player2Score;

    public TennisScoreClassic(int player1Score, int player2Score) {
        this.player1Score = player1Score;
        this.player2Score = player2Score;
    }

    private static PlayerScore getPlayerScore(int thisPlayerScore, int opponentScore) {
        if (opponentScore >= 4 || thisPlayerScore >= 4) {
            int playerLead = thisPlayerScore - opponentScore;
            if (playerLead == 1) {
                return PlayerScore.ADVANTAGE;
            } else if (playerLead >= 2) {
                return PlayerScore.GAME;
            }
        }
        return playerScoreFromInt(thisPlayerScore);
    }

    @Override
    public PlayerScore firstPlayerScore() {
        return getPlayerScore(player1Score, player2Score);
    }

    @Override
    public PlayerScore secondPlayerScore() {
        return getPlayerScore(player2Score, player1Score);
    }

}
