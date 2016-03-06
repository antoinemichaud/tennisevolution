package tennis.set.services;

import java.util.List;

public class TennisSetKataContainer {

    public String displayScore(List<Integer> scores) {
        Integer servicePlayer = scores.remove(0);
        TennisSet tennisSet = new TennisSet();
        for (Integer score : scores) {
            if (score == 1) {
                tennisSet.player1WonPoint();
            } else if (score == 2) {
                tennisSet.player2WonPoint();
            }
        }
        return tennisSet.score();
    }
}
