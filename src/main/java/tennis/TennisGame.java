package tennis;

public class TennisGame {
    private int player1Score;

    public TennisScore getScoreAsBusiness(int player1Score, int player2Score) {
        return new RegularScore(player1Score, player2Score);
    }

    public void incrementPlayer1Score() {
        this.player1Score++;
    }

    public int getPlayer1Score() {
        return player1Score;
    }
}
