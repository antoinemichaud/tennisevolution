package tennis.api;

public class GameQuestion {
    private GameScore player1GameScore;
    private GameScore player2GameScore;

    public GameQuestion(String player1Name, String player2Name) {
        this.player1GameScore = new GameScore(player1Name);
        this.player2GameScore = new GameScore(player2Name);
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
