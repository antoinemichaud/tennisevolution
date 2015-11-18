package tennis.set;

import java.util.List;

public class TennisSetKataContainer {

    TennisSet tennisSet = new TennisSet();

    public String displayScore(List<Integer> player1Scores, List<Integer> player2Scores) {
        for (int i = 0; i < player1Scores.size(); i++) {
            Integer player1Score = player1Scores.get(i);
            Integer player2Score = player2Scores.get(i);
            for (int j = 0; j < Math.max(player1Score, player2Score); j++) {
                if (j < player1Score)
                    tennisSet.player1WonPoint();
                if (j < player2Score)
                    tennisSet.player2WonPoint();
            }
        }
        return tennisSet.score();
    }

    public String displayScore(List<Integer> scores) {
        TennisSet tennisSet = new TennisSet();

        for (Integer score : scores) {
            if(score==1){
                tennisSet.player1WonPoint();
            }else if(score==2){
                tennisSet.player2WonPoint();
            }
        }
        return tennisSet.score();
    }
}
