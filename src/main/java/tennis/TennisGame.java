package tennis;

public class TennisGame {
    public TennisScore getScoreAsBusiness(int player1Score, int player2Score) {
        return new RegularScore(player1Score, player2Score);
    }
}
