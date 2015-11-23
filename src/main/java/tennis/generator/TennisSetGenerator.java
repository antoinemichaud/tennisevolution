package tennis.generator;

import tennis.game.classic.TennisGame;
import tennis.history.HistoryKeeper;
import tennis.set.TennisSet;

import java.util.Random;
import java.util.function.Predicate;

public class TennisSetGenerator {
    private Random random;
    private TennisSet tennisSet;
    private TennisGame tennisGame;

    public TennisSetGenerator(Random random) {
        this.random = random;
    }

    public HistoryKeeper generate(Predicate<TennisGame> endCondition) {
        tennisGame = new TennisGame();
        HistoryKeeper historyKeeper = new HistoryKeeper(tennisGame);

        do {
            if (Math.round(random.nextDouble()) == 0) {
                tennisGame.incrementPlayer1Score();
            } else {
                tennisGame.incrementPlayer2Score();
            }
        } while (!endCondition.test(tennisGame));
        return historyKeeper;
    }

    public HistoryKeeper generateSet(Predicate<TennisSet> endCondition) {
        tennisSet = new TennisSet();
        HistoryKeeper historyKeeper = new HistoryKeeper(tennisSet);

        do {
            if (Math.round(random.nextDouble()) == 0) {
                tennisSet.player1WonPoint();
            } else {
                tennisSet.player2WonPoint();
            }
        } while (!endCondition.test(tennisSet));
        return historyKeeper;
    }

    public String tennisSetScore() {
        return tennisSet.score();
    }
}
