package tennis.game;

import java.util.Observable;

import static tennis.history.WhichPlayer.PLAYER_ONE;
import static tennis.history.WhichPlayer.PLAYER_TWO;

public class TennisGame extends Observable {
    private int player1Score;
    private int player2Score;

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
        return player1Score >= 4 || player2Score >= 4;
    }
}
