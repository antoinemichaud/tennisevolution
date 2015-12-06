package tennis;

import java.util.List;

public class TenniSetDisplayer {
    private List<Integer> scores;

    public TenniSetDisplayer(List<Integer> scores) {
        this.scores = scores;
    }

    public String display() {
        TennisSet tennisSet = new TennisSet();
        for (Integer playerWhoWon : scores) {
            if (playerWhoWon == 1) {
                tennisSet.player1Win();
            } else {
                tennisSet.player2Win();
            }
        }
        TennisSetScore score = tennisSet.score();
        if (tennisSet.state() == TennisSetState.PLAYER_1_WON) {
            return "Set for player1!";
        }
        if (tennisSet.state() == TennisSetState.PLAYER_2_WON) {
            return "Set for player2!";
        }
        return score.player1WonGames + "-" + score.player2WonGames + " " +
                new TennisEnglishDisplayer(tennisSet.getTennisGame()).displayScore(score.player1GameScore, score.player2GameScore);
    }
}
