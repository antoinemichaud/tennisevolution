package tennis.game.withlife;

import static tennis.game.withlife.PlayerScore.playerScoreFromInt;

public class TennisScore {
    private int player1Score;
    private int player2Score;

    public TennisScore(int player1Score, int player2Score) {
        this.player1Score = Math.max(0, player1Score - 1);
        this.player2Score = Math.max(0, player2Score - 1);
    }

    public PlayerScore firstPlayerScore() {
        return getPlayerScore(player1Score, player2Score);
    }

    public PlayerScore secondPlayerScore() {
        return getPlayerScore(player2Score, player1Score);
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

}
