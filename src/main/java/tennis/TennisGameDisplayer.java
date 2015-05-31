package tennis;

public class TennisGameDisplayer {

    protected final String player1Name;
    protected final String player2Name;
    private int player2Score;

    TennisGame tennisGame;

    public TennisGameDisplayer(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        tennisGame = new TennisGame();
    }

    public void wonPoint(String playerName) {
        if (playerName.equals(player1Name))
            tennisGame.incrementPlayer1Score();
        else
            setPlayer2Score(getPlayer2Score() + 1);
    }

    public String getScore() {
        if (tennisGame.getPlayer1Score() == getPlayer2Score()) {
            return globalScoreAsStringForEquality();
        } else if (tennisGame.getPlayer1Score() >= 4 || getPlayer2Score() >= 4) {
            return globalScoreAsStringForPointEnd();
        } else {
            RegularScore score = (RegularScore) tennisGame.getScoreAsBusiness(tennisGame.getPlayer1Score(), this.getPlayer2Score());
            return getScoreAsString(score.firstPlayerScore()) + "-" + getScoreAsString(score.secondPlayerScore());
        }
    }

    private String getScoreAsString(PlayerScore player1Score) {
        String player1ScoreAsString = null;
        switch (player1Score) {
            case LOVE:
                player1ScoreAsString = love();
                break;
            case FIFTEEN:
                player1ScoreAsString = fifteen();
                break;
            case THIRTY:
                player1ScoreAsString = thirty();
                break;
            case FORTY:
                player1ScoreAsString = forty();
        }
        return player1ScoreAsString;
    }

    protected String globalScoreAsStringForEquality() {
        switch (tennisGame.getPlayer1Score()) {
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

    protected String loveAll() {
        return null;
    }

    protected String fifteenAll() {
        return null;
    }

    protected String thirtyAll() {
        return null;
    }

    protected String deuce() {
        return null;
    }

    protected String globalScoreAsStringForPointEnd() {
        int scoreDiff = tennisGame.getPlayer1Score() - getPlayer2Score();
        if (scoreDiff == 1) return advantagePlayer1();
        else if (scoreDiff == -1) return advantagePlayer2();
        else if (scoreDiff >= 2) return gameForPlayer1();
        else return gameForPlayer2();
    }

    protected String advantagePlayer1() {
        return null;
    }

    protected String advantagePlayer2() {
        return null;
    }

    protected String gameForPlayer1() {
        return null;
    }

    protected String gameForPlayer2() {
        return null;
    }

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

    protected String love() {
        return null;
    }

    protected String fifteen() {
        return null;
    }

    protected String thirty() {
        return null;
    }

    protected String forty() {
        return null;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public void setPlayer2Score(int player2Score) {
        this.player2Score = player2Score;
    }
}
