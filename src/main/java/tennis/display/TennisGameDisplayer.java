package tennis.display;

import tennis.game.base.PlayerScore;
import tennis.game.base.TennisGame;
import tennis.game.base.TennisScore;

public abstract class TennisGameDisplayer {

    protected final String player1Name;
    protected final String player2Name;

    TennisGame tennisGame;

    public TennisGameDisplayer(String player1Name, String player2Name, TennisGame tennisGame) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.tennisGame = tennisGame;
    }

    public void wonPoint(String playerName) {
        if (playerName.equals(player1Name))
            tennisGame.incrementPlayer1Score();
        else
            tennisGame.incrementPlayer2Score();
    }

    public String getScore() {
        TennisScore score = tennisGame.getScore();
        if (score.firstPlayerScore() == score.secondPlayerScore()) {
            return globalScoreAsStringForEquality();
        } else if (tennisGame.isEndOfGame()) {
            return globalScoreAsStringForPointEnd();
        } else {
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
        switch (tennisGame.getScore().firstPlayerScore()) {
            case LOVE:
                return loveAll();
            case FIFTEEN:
                return fifteenAll();
            case THIRTY:
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
        if (tennisGame.getScore().firstPlayerScore() == PlayerScore.ADVANTAGE) return advantagePlayer1();
        else if (tennisGame.getScore().secondPlayerScore() == PlayerScore.ADVANTAGE) return advantagePlayer2();
        else if (tennisGame.getScore().firstPlayerScore() == PlayerScore.GAME) return gameForPlayer1();
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
