package tennis;

public class TennisGame {
    private int player1Score;
    private int player2Score;

    public TennisScore getScore() {
        return new RegularScore(this.player1Score, this.player2Score);
    }

    public void incrementPlayer1Score() {
        this.player1Score++;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public void incrementPlayer2Score() {
        this.player2Score++;
    }

    public int getPlayer2Score() {
        return player2Score;
    }
}
