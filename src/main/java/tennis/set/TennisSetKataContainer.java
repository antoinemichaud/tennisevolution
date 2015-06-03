package tennis.set;

import java.util.List;

public class TennisSetKataContainer {

    TennisSet tennisSet = new TennisSet();

    public String displayScore(List<Integer> player1Scores, List<Integer> player2Scores) {
        for (int i = 0; i < player1Scores.size(); i++) {
            Integer player1Score = player1Scores.get(i);
            Integer player2Score = player2Scores.get(i);
            for (int j = 0; j < Math.max(player1Score, player2Score); j++) {
                if (i <= player1Score)
                    tennisSet.player1WonPoint();
                if (i <= player2Score)
                    tennisSet.player2WonPoint();
            }
        }
        return tennisSet.score();
    }
}
