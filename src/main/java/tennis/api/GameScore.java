package tennis.api;

import java.util.concurrent.atomic.AtomicInteger;

public class GameScore {
    private String playerName;
    private AtomicInteger playerScore;

    public GameScore(String playerName) {
        this.playerName = playerName;
        this.playerScore = new AtomicInteger(0);
    }

    public GameScore(String playerName, AtomicInteger score) {
        this.playerName = playerName;
        this.playerScore = score;
    }

    public void incrementScore() {
        this.playerScore.getAndIncrement();
    }

    public String getPlayerName() {
        return playerName;
    }

    public Integer getPlayerScore() {
        return playerScore.get();
    }
}
