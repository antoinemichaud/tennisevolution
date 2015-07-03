package tennis.set;

import tennis.game.EnglishGameDisplayer;
import tennis.game.PlayerScore;
import tennis.game.TennisGame;
import tennis.game.TennisGameDisplayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.atomic.AtomicInteger;

public class TennisSet extends Observable {

    public static final String PLAYER_1 = "player1";
    public static final String PLAYER_2 = "player2";

    private TennisGame tennisGame = new TennisGame();
    private TennisGameDisplayer tennisGameDisplayer = new EnglishGameDisplayer(PLAYER_1, PLAYER_2, tennisGame);
    private AtomicInteger player1wonGames;
    private AtomicInteger player2wonGames;
    private List<Observer> observers = new ArrayList<>();

    public TennisSet() {
        player1wonGames = new AtomicInteger(0);
        player2wonGames = new AtomicInteger(0);
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
        tennisGame = new TennisGame();
        for (Observer observer : observers) {
            tennisGame.addObserver(observer);
        }
        tennisGameDisplayer = new EnglishGameDisplayer(PLAYER_1, PLAYER_2, tennisGame);
    }

    public String score() {
        String playersScore = player1wonGames + "-" + player2wonGames + " ";
        if (player2wonGames.get() >= 6) {
            return playersScore + "Set for player2!";
        }
        if (player1wonGames.get() >= 6) {
            return playersScore + "Set for player1!";
        }
        return playersScore + tennisGameDisplayer.getScore();
    }

    @Override
    public synchronized void addObserver(Observer o) {
        observers.add(o);
        tennisGame.addObserver(o);
    }
}
