package tennis.history;

import java.util.HashMap;
import java.util.Map;

public class Aggregator {

    private final String player1Name;
    private final String player2Name;
    private HashMap<String, Integer> playersScores;

    public Aggregator(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        playersScores = new HashMap<>();
        playersScores.put(player1Name, 0);
        playersScores.put(player2Name, 0);
    }

    public Map<String, Integer> aggregateToGameScore(HistoryKeeper historyKeeper) {
        for (WhichPlayer whichPlayer : historyKeeper.list()) {
            switch (whichPlayer) {
                case PLAYER_ONE:
                    playersScores.put(player1Name, playersScores.get(player1Name) + 1);
                    break;
                case PLAYER_TWO:
                    playersScores.put(player2Name, playersScores.get(player2Name) + 1);
            }
        }
        return playersScores;
    }

}
