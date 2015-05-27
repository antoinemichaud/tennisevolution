package tennis;

public abstract class TennisGame {

    protected final String player1Name;
    protected final String player2Name;
    protected int player1Score;
    protected int player2Score;

    public TennisGame(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        player1Score = 0;
        this.player2Name = player2Name;
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

    protected String globalScoreAsStringForEquality() {
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

    protected abstract String loveAll();

    protected abstract String fifteenAll();

    protected abstract String thirtyAll();

    protected abstract String deuce();

    protected String globalScoreAsStringForPointEnd() {
        int scoreDiff = player1Score - player2Score;
        if (scoreDiff == 1) return advantagePlayer1();
        else if (scoreDiff == -1) return advantagePlayer2();
        else if (scoreDiff >= 2) return gameForPlayer1();
        else return gameForPlayer2();
    }

    protected abstract String advantagePlayer1();

    protected abstract String advantagePlayer2();

    protected abstract String gameForPlayer1();

    protected abstract String gameForPlayer2();

    protected String singlePlayerScoreAsString(int playerScore) {
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

    protected abstract String love();

    protected abstract String fifteen();

    protected abstract String thirty();

    protected abstract String forty();
}
