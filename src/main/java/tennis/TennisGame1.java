package tennis;

public class TennisGame1 implements TennisGame {

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

    private int player1Score;
    private int player2Score;

    public TennisGame1(String player1Name, String player2Name) {
        player1Score = 0;
        player2Score = 0;
    }

    public void wonPoint(String playerName) {
        if (playerName.equals("player1"))
            player1Score += 1;
        else
            player2Score += 1;
    }

    public String getScore() {
        if (player1Score == player2Score) {
            return scoreWhenEquality();
        } else if (player1Score >= 4 || player2Score >= 4) {
            return scoreForPointEnd();
        } else {
            return singlePlayerScoreAsString(player1Score) + "-" + singlePlayerScoreAsString(player2Score);
        }
    }

    private String scoreWhenEquality() {
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

    private String scoreForPointEnd() {
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
