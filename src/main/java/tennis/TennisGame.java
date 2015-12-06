package tennis;

import static tennis.TennisScore.*;
import static tennis.TennisScores.scores;

public class TennisGame {

    private int player1Score;
    private int player2Score;

    public TennisGame(int player1Score, int player2Score) {
        this.player1Score = Math.max(0, player1Score - 1);
        this.player2Score = Math.max(0, player2Score - 1);
    }

    private TennisScores globalScoreAsStringForEquality() {
        switch (player1Score) {
            case 0:
                return scores(ZERO, ZERO);
            case 1:
                return scores(FIFTEEN, FIFTEEN);
            case 2:
                return scores(THIRTY, THIRTY);
            default:
                return scores(FORTY, FORTY);
        }
    }

    public TennisScores score() {
        if (player1Score == player2Score) {
            return globalScoreAsStringForEquality();
        } else if (player1Score >= 4 || player2Score >= 4) {
            return globalScoreAsStringForPointEnd();
        } else {
            return scores(singlePlayerScoreAsString(player1Score), singlePlayerScoreAsString(player2Score));
        }
    }

    private TennisScores globalScoreAsStringForPointEnd() {
        int scoreDiff = player1Score - player2Score;
//        if (scoreDiff == 1) return scores(ADVANTAGE, FORTY);
//        else if (scoreDiff == -1) return scores(FORTY, ADVANTAGE);
        if (scoreDiff >= 1) return scores(GAME, ANY);
        else return scores(ANY, GAME);
    }

    private TennisScore singlePlayerScoreAsString(int playerScore) {
        switch (playerScore) {
            case 0:
                return ZERO;
            case 1:
                return FIFTEEN;
            case 2:
                return THIRTY;
            default:
                return FORTY;
        }
    }

    boolean endOfGame() {
        return player1Score >= 4 || player2Score >= 4;
    }
}
