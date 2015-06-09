package tennis.generator;

import tennis.game.TennisGame;
import tennis.history.HistoryKeeper;

import java.util.Random;
import java.util.function.Predicate;

public class TennisSetGenerator {
    private Random random;

    public TennisSetGenerator(Random random) {
        this.random = random;
    }

    public HistoryKeeper generate(Predicate<TennisGame> endCondition) {
        TennisGame tennisGame = new TennisGame();
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
}
