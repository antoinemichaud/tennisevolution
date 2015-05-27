package tennis;

public class TennisGameAlt extends TennisGame {

    public static final String LOVE_ALL = "0-0";
    public static final String FIFTEEN_ALL = "15-15";
    public static final String THIRTY_ALL = "30-30";
    public static final String DEUCE = "40-40";
    public static final String WIN_FOR = "Game ";
    public static final String LOVE = "0";
    public static final String FIFTEEN = "15";
    public static final String THIRTY = "30";
    public static final String FORTY = "40";
    private final String player1Name;
    private final String player2Name;

    private int player1Score;
    private int player2Score;

    public TennisGameAlt(String player1Name, String player2Name) {
        super("", "");
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
                return LOVE_ALL;
            case 1:
                return FIFTEEN_ALL;
            case 2:
                return THIRTY_ALL;
            default:
                return DEUCE;
        }
    }

    private String globalScoreAsStringForPointEnd() {
        int scoreDiff = player1Score - player2Score;
        if (scoreDiff == 1) return "A-40";
        else if (scoreDiff == -1) return "40-A";
        else if (scoreDiff >= 2) return WIN_FOR + player1Name + "!";
        else return WIN_FOR + player2Name + "!";
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
