package tennis;

public class TennisGame {

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
    private final String player1Name;
    private final String player2Name;

    private int player1Score;
    private int player2Score;

    public TennisGame(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        player1Score = 0;
        player2Score = 0;
    }

    public void wonPoint(String playerName) {
        if (playerName.equals(player1Name))
            player1Score += 1;
        else
            player2Score += 1;
    }

    public String getScore() {
        if (player1Score == player2Score) {
            return globalScoreAsStringForEquality();
        } else if (player1Score >= 4 || player2Score >= 4) {
            return globalScoreAsStringForPointEnd();
        } else {
            return singlePlayerScoreAsString(player1Score) + "-" + singlePlayerScoreAsString(player2Score);
        }
    }

    private String globalScoreAsStringForEquality() {
        switch (player1Score) {
            case 0:
                return loveAll();
            case 1:
                return fifteenAll();
            case 2:
                return thirtyAll();
            default:
                return deuce();
        }
    }

    private String loveAll() {
        return LOVE_ALL;
    }

    private String fifteenAll() {
        return FIFTEEN_ALL;
    }

    private String thirtyAll() {
        return THIRTY_ALL;
    }

    private String deuce() {
        return DEUCE;
    }

    private String globalScoreAsStringForPointEnd() {
        int scoreDiff = player1Score - player2Score;
        if (scoreDiff == 1) return advantagePlayer1();
        else if (scoreDiff == -1) return advantagePlayer2();
        else if (scoreDiff >= 2) return gameForPlayer1();
        else return gameForPlayer2();
    }

    private String advantagePlayer1() {
        return ADVANTAGE + player1Name;
    }

    private String advantagePlayer2() {
        return ADVANTAGE + player2Name;
    }

    private String gameForPlayer1() {
        return WIN_FOR + player1Name;
    }

    private String gameForPlayer2() {
        return WIN_FOR + player2Name;
    }

    private String singlePlayerScoreAsString(int playerScore) {
        switch (playerScore) {
            case 0:
                return love();
            case 1:
                return fifteen();
            case 2:
                return thirty();
            default:
                return forty();
        }
    }

    private String love() {
        return LOVE;
    }

    private String fifteen() {
        return FIFTEEN;
    }

    private String thirty() {
        return THIRTY;
    }

    private String forty() {
        return FORTY;
    }
}
