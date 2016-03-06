package tennis.game.noavantage;

import tennis.game.base.TennisGame;
import tennis.game.base.TennisScore;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static tennis.history.WhichPlayer.PLAYER_ONE;
import static tennis.history.WhichPlayer.PLAYER_TWO;

public class TennisGameNoAdvantage extends Observable implements TennisGame {
    private int player1Score;
    private int player2Score;

    public TennisGameNoAdvantage() {

    }

    public TennisGameNoAdvantage(List<Observer> observers) {
        observers.forEach(this::addObserver);
    }

    public TennisScore getScore() {
        return new TennisScoreNoAdvantage(this.player1Score, this.player2Score);
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
        return player1Score >= 4 || player2Score >= 4;
    }
}
