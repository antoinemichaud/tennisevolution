package tennis.set.services;

import tennis.display.EnglishGameDisplayer;
import tennis.display.TennisGameDisplayer;
import tennis.game.base.PlayerScore;
import tennis.game.base.TennisGame;
import tennis.game.classic.TennisGameClassic;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.concurrent.atomic.AtomicInteger;

public class TennisSet {

    public static final String PLAYER_1 = "player1";
    public static final String PLAYER_2 = "player2";

    private TennisGame tennisGame = new TennisGameClassic();
    private AtomicInteger player1wonGames;
    private AtomicInteger player2wonGames;
    private List<Observer> observers = new ArrayList<>();
    private TennisGameDisplayer tennisGameDisplayer;

    public TennisSet() {
        player1wonGames = new AtomicInteger(0);
        player2wonGames = new AtomicInteger(0);
        tennisGameDisplayer = new EnglishGameDisplayer(PLAYER_1, PLAYER_2, tennisGame);
    }

    public void player1WonPoint() {
        tennisGame.incrementPlayer1Score();
        if (tennisGame.getScore().firstPlayerScore() == PlayerScore.GAME) {
            startNewGame();
            player1wonGames.incrementAndGet();
        }
    }

    public void player2WonPoint() {
        tennisGame.incrementPlayer2Score();
        if (tennisGame.getScore().secondPlayerScore() == PlayerScore.GAME) {
            startNewGame();
            player2wonGames.incrementAndGet();
        }
    }

    private void startNewGame() {
        tennisGame = new TennisGameClassic(observers);
        tennisGameDisplayer = new EnglishGameDisplayer(PLAYER_1, PLAYER_2, tennisGame);
    }

    public String score() {
        String playersScore = player1wonGames + "-" + player2wonGames + " ";
        if (player1wonGames.get() >= 6) {
            return playersScore + "Set for player1!";
        }
        if (player2wonGames.get() >= 6) {
            return playersScore + "Set for player2!";
        }
        return playersScore + tennisGameDisplayer.getScore();
    }
}
