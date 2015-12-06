package tennis;

public class TennisService2 {

    private String player1Name;
    private String player2Name;
    private int player1Score;
    private int player2Score;

    public void wonPoint(String playerName) {
        if (playerName.equals("player1"))
            player1Score += 1;
        else
            player2Score += 1;
    }

    private String globalScoreAsStringForEquality() {
        switch (player1Score) {
            case 0:
                return "0-0";
            case 1:
                return "15-15";
            case 2:
                return "30-30";
            default:
                return "40-40";
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
        if (scoreDiff == 1) return "A-40";
        else if (scoreDiff == -1) return "40-A";
        else if (scoreDiff >= 2) return "Game player1!";
        else return "Game player2!";
    }

    private String singlePlayerScoreAsString(int playerScore) {
        switch (playerScore) {
            case 0:
                return "0";
            case 1:
                return "15";
            case 2:
                return "30";
            default:
                return "40";
        }
    }

}
