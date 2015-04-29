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
            return scoreFromGeneralCase(player1Score) + "-" + scoreFromGeneralCase(player2Score);
        }
    }

    private String scoreWhenEquality() {
        String score;
        switch (player1Score) {
            case 0:
                score = LOVE_ALL;
                break;
            case 1:
                score = FIFTEEN_ALL;
                break;
            case 2:
                score = THIRTY_ALL;
                break;
            default:
                score = DEUCE;
                break;

        }
        return score;
    }

    private String scoreForPointEnd() {
        String score;
        int minusResult = player1Score - player2Score;
        if (minusResult == 1) score = ADVANTAGE + "player1";
        else if (minusResult == -1) score = ADVANTAGE + "player2";
        else if (minusResult >= 2) score = WIN_FOR + "player1";
        else score = WIN_FOR + "player2";
        return score;
    }

    private String scoreFromGeneralCase(int tempScore) {
        String score = "";
        switch (tempScore) {
            case 0:
                score += LOVE;
                break;
            case 1:
                score += FIFTEEN;
                break;
            case 2:
                score += THIRTY;
                break;
            case 3:
                score += FORTY;
                break;
        }
        return score;
    }
}
