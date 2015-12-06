package tennis;

public class TennisScores {

    public TennisScore player1;
    public TennisScore player2;

    public TennisScores(TennisScore player1, TennisScore player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public static TennisScores scores(TennisScore player1, TennisScore player2) {
        return new TennisScores(player1, player2);
    }

}
