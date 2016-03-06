package tennis.generator;

import tennis.game.classic.TennisGameClassic;
import tennis.history.HistoryKeeper;
import tennis.history.WhichPlayer;
import tennis.set.TennisSet;

import java.util.Collections;
import java.util.Random;
import java.util.function.Predicate;

public class TennisSetGenerator {
    private Random random;
    private TennisSet tennisSet;
    private TennisGameClassic tennisGame;

    public TennisSetGenerator(Random random) {
        this.random = random;
    }

    public HistoryKeeper generate(Predicate<TennisGameClassic> endCondition) {
        tennisGame = new TennisGameClassic();
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

    public HistoryKeeper generateNoAdvantage(Predicate<TennisGameClassic> endCondition) {
        tennisGame = new TennisGameClassic();
        HistoryKeeper historyKeeper = new HistoryKeeper(tennisGame);

        do {
            if (Math.round(random.nextDouble()) == 0) {
                tennisGame.incrementPlayer1Score();
            } else {
                tennisGame.incrementPlayer2Score();
            }
            if(someoneWin(historyKeeper)){
                break;
            }

        } while (!endCondition.test(tennisGame));
        return historyKeeper;
    }

    private boolean someoneWin(HistoryKeeper historyKeeper) {
        return (Collections.frequency(historyKeeper.list(), WhichPlayer.PLAYER_ONE) == 4 ||
                Collections.frequency(historyKeeper.list(), WhichPlayer.PLAYER_TWO) == 4);
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
