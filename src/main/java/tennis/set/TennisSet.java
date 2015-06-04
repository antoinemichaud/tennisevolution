package tennis.set;

import tennis.game.EnglishGameDisplayer;
import tennis.game.TennisGameDisplayer;

import java.util.concurrent.atomic.AtomicInteger;

public class TennisSet {

    public static final String PLAYER_1 = "player1";
    public static final String PLAYER_2 = "player2";

    TennisGameDisplayer tennisGameDisplayer = new EnglishGameDisplayer(PLAYER_1, PLAYER_2);
    private AtomicInteger player1wonGames;
    private AtomicInteger player2wonGames;

    public TennisSet() {
        player1wonGames = new AtomicInteger(0);
        player2wonGames = new AtomicInteger(0);
    }

    public void player1WonPoint() {
        playerWonPoint(PLAYER_1, player1wonGames);
    }

    public void player2WonPoint() {
        playerWonPoint(PLAYER_2, player2wonGames);
    }

    private void playerWonPoint(String playerName, AtomicInteger pointsToIncrease) {
        tennisGameDisplayer.wonPoint(playerName);
        if (tennisGameDisplayer.getScore().equals("Win for " + playerName)) {
            tennisGameDisplayer = new EnglishGameDisplayer(PLAYER_1, PLAYER_2);
            pointsToIncrease.incrementAndGet();
        }
    }

    public String score() {
        return player1wonGames + "-" + player2wonGames + " " + tennisGameDisplayer.getScore();
    }
}
