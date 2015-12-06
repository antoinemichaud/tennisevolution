package tennis.game.withlife;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static tennis.history.WhichPlayer.PLAYER_ONE;
import static tennis.history.WhichPlayer.PLAYER_TWO;

public class TennisGame extends Observable {
    private int player1Score;
    private int player2Score;

    public TennisGame() {

    }

    public TennisGame(List<Observer> observers) {
        observers.forEach(this::addObserver);
    }

    public TennisScore getScore() {
        return new TennisScore(this.player1Score, this.player2Score);
    }

    public void incrementPlayer1Score() {
        this.player1Score++;
        setChanged();
        notifyObservers(PLAYER_ONE);
    }

    public void incrementPlayer2Score() {
        this.player2Score++;
        setChanged();
        notifyObservers(PLAYER_TWO);
    }

    public boolean isEndOfGame() {
        TennisScore tennisScore = new TennisScore(this.player1Score, this.player2Score);
        return tennisScore.firstPlayerScore() == PlayerScore.GAME || tennisScore.secondPlayerScore() == PlayerScore.GAME;
    }
}
