package tennis;

public class TennisGame {
    private int player1Score;
    private int player2Score;

    public TennisScore getScore() {
        return new TennisScore(this.player1Score, this.player2Score);
    }

    public void incrementPlayer1Score() {
        this.player1Score++;
    }

    public void incrementPlayer2Score() {
        this.player2Score++;
    }

    public boolean isEndOfGame() {
        return player1Score >= 4 || player2Score >= 4;
    }
}
