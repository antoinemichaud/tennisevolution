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
    private TennisScore player1Score;
    private TennisScore player2Score;
    private TennisGame tennisGame;

    private String globalScoreAsStringForEquality() {
        switch (player1Score) {
            case ZERO:
                return LOVE_ALL;
            case FIFTEEN:
                return FIFTEEN_ALL;
            case THIRTY:
                return THIRTY_ALL;
            default:
                return DEUCE;
        }
    }

    public String displayScore(String player1Name, int player1ScoreNum, String player2Name, int player2ScoreNum) {
        tennisGame = new TennisGame(player1ScoreNum, player2ScoreNum);
        TennisScores tennisScores = tennisGame.score();
        this.player1Score = tennisScores.player1;
        this.player2Score = tennisScores.player2;
        if (player1Score == player2Score) {
            return globalScoreAsStringForEquality();
        } else if (tennisGame.endOfGame()) {
            return globalScoreAsStringForPointEnd();
        } else {
            return singlePlayerScoreAsString(player1Score) + "-" + singlePlayerScoreAsString(player2Score);
        }
    }

    private String globalScoreAsStringForPointEnd() {
        if (player1Score == TennisScore.ADVANTAGE) return ADVANTAGE + "player1";
        else if (player2Score == TennisScore.ADVANTAGE) return ADVANTAGE + "player2";
        else if (player1Score == TennisScore.GAME) return WIN_FOR + "player1";
        else return WIN_FOR + "player2";
    }

    private String singlePlayerScoreAsString(TennisScore playerScore) {
        switch (playerScore) {
            case ZERO:
                return LOVE;
            case FIFTEEN:
                return FIFTEEN;
            case THIRTY:
                return THIRTY;
            default:
                return FORTY;
        }
    }

}
