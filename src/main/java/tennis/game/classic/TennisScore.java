package tennis.game.classic;

import static tennis.game.classic.PlayerScore.playerScoreFromInt;

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
