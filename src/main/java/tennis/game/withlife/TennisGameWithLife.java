package tennis.game.withlife;

import tennis.game.base.PlayerScore;
import tennis.game.base.TennisGame;
import tennis.game.base.TennisScore;
import tennis.game.classic.TennisScoreClassic;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static tennis.history.WhichPlayer.PLAYER_ONE;
import static tennis.history.WhichPlayer.PLAYER_TWO;

public class TennisGameWithLife extends Observable implements TennisGame {
    private int player1Score;
    private int player2Score;

    public TennisGameWithLife() {

    }

    public TennisGameWithLife(List<Observer> observers) {
        observers.forEach(this::addObserver);
    }

    public TennisScore getScore() {
        return new TennisScoreWithLife(this.player1Score, this.player2Score);
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
        TennisScore tennisScore = new TennisScoreWithLife(this.player1Score, this.player2Score);
        return tennisScore.firstPlayerScore() == PlayerScore.GAME || tennisScore.secondPlayerScore() == PlayerScore.GAME;
    }
}
