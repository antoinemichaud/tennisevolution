package tennis;

public class TennisEnglishDisplayer {

    public static final String LOVE_ALL = "Love-All";
    public static final String FIFTEEN_ALL = "Fifteen-All";
    public static final String THIRTY_ALL = "Thirty-All";
    public static final String DEUCE = "Deuce";
    public static final String ADVANTAGE = "Advantage ";
    public static final String WIN_FOR = "Win for ";
    public static final String LOVE = "Love";
    public static final String FIFTEEN = "Fifteen";
    public static final String THIRTY = "Thirty";
    public static final String FORTY = "Forty";

    private String player1Name;
    private String player2Name;
    private int player1Score;
    private int player2Score;

    private String globalScoreAsStringForEquality() {
        switch (player1Score) {
            case 0:
                return LOVE_ALL;
            case 1:
                return FIFTEEN_ALL;
            case 2:
                return THIRTY_ALL;
            default:
                return DEUCE;
        }
    }

    public String displayScore(String player1Name, int player1Score, String player2Name, int player2Score) {
        this.player1Score = player1Score;
        this.player2Score = player2Score;
        if (player1Score == player2Score) {
            return globalScoreAsStringForEquality();
        } else if (player1Score >= 4 || player2Score >= 4) {
            return globalScoreAsStringForPointEnd();
        } else {
            return singlePlayerScoreAsString(player1Score) + "-" + singlePlayerScoreAsString(player2Score);
        }
    }

    private String globalScoreAsStringForPointEnd() {
        int scoreDiff = player1Score - player2Score;
        if (scoreDiff == 1) return ADVANTAGE + "player1";
        else if (scoreDiff == -1) return ADVANTAGE + "player2";
        else if (scoreDiff >= 2) return WIN_FOR + "player1";
        else return WIN_FOR + "player2";
    }

    private String singlePlayerScoreAsString(int playerScore) {
        switch (playerScore) {
            case 0:
                return LOVE;
            case 1:
                return FIFTEEN;
            case 2:
                return THIRTY;
            default:
                return FORTY;
        }
    }

}
