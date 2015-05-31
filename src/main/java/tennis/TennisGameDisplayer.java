package tennis;

public abstract class TennisGameDisplayer {

    protected final String player1Name;
    protected final String player2Name;

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
            tennisGame.incrementPlayer2Score();
    }

    public String getScore() {
        if (tennisGame.getPlayer1Score() == tennisGame.getPlayer2Score()) {
            return globalScoreAsStringForEquality();
        } else if (tennisGame.getPlayer1Score() >= 4 || tennisGame.getPlayer2Score() >= 4) {
            return globalScoreAsStringForPointEnd();
        } else {
            RegularScore score = (RegularScore) tennisGame.getScore();
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

    protected abstract String loveAll();

    protected abstract String fifteenAll();

    protected abstract String thirtyAll();

    protected abstract String deuce();

    protected String globalScoreAsStringForPointEnd() {
        int scoreDiff = tennisGame.getPlayer1Score() - tennisGame.getPlayer2Score();
        if (scoreDiff == 1) return advantagePlayer1();
        else if (scoreDiff == -1) return advantagePlayer2();
        else if (scoreDiff >= 2) return gameForPlayer1();
        else return gameForPlayer2();
    }

    protected abstract String advantagePlayer1();

    protected abstract String advantagePlayer2();

    protected abstract String gameForPlayer1();

    protected abstract String gameForPlayer2();

    protected abstract String love();

    protected abstract String fifteen();

    protected abstract String thirty();

    protected abstract String forty();

}
