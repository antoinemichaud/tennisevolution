package tennis.game.classic;

import tennis.game.base.TennisGame;
import tennis.game.base.TennisScore;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static tennis.history.WhichPlayer.PLAYER_ONE;
import static tennis.history.WhichPlayer.PLAYER_TWO;

public class TennisGameClassic extends Observable implements TennisGame {
    private int player1Score;
    private int player2Score;

    public TennisGameClassic() {

    }

    public TennisGameClassic(List<Observer> observers) {
        observers.forEach(this::addObserver);
    }

    @Override
    public TennisScore getScore() {
        return new TennisScoreClassic(this.player1Score, this.player2Score);
    }

    @Override
    public void incrementPlayer1Score() {
        this.player1Score++;
        setChanged();
        notifyObservers(PLAYER_ONE);
    }

    @Override
    public void incrementPlayer2Score() {
        this.player2Score++;
        setChanged();
        notifyObservers(PLAYER_TWO);
    }

    @Override
    public boolean isEndOfGame() {
        return player1Score >= 4 || player2Score >= 4;
    }
}
