package tennis.api;

import java.util.concurrent.atomic.AtomicInteger;

public class GameQuestion {
    private GameScore player1GameScore;
    private GameScore player2GameScore;

    public GameQuestion(String player1Name, String player2Name) {
        this.player1GameScore = new GameScore(player1Name);
        this.player2GameScore = new GameScore(player2Name);
    }

    public GameQuestion(String player1Name, int player1Score, String player2Name, int player2Score) {
        this.player1GameScore = new GameScore(player1Name, new AtomicInteger(player1Score));
        this.player2GameScore = new GameScore(player2Name, new AtomicInteger(player2Score));
    }

    public void incrementPlayer1() {
        player1GameScore.incrementScore();
    }

    public void incrementPlayer2() {
        player2GameScore.incrementScore();
    }

    public GameScore getPlayer1GameScore() {
        return player1GameScore;
    }

    public GameScore getPlayer2GameScore() {
        return player2GameScore;
    }
}
