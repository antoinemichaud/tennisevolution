package tennis;

public class TennisFrenchDisplayer {

    public static final String LOVE_ALL = "Zéro partout";
    public static final String FIFTEEN_ALL = "Quinze partout";
    public static final String THIRTY_ALL = "Trente partout";
    public static final String DEUCE = "Egalité";
    public static final String ADVANTAGE = "Avantage ";
    public static final String WIN_FOR = "Jeu ";
    public static final String LOVE = "Zéro";
    public static final String FIFTEEN = "Quinze";
    public static final String THIRTY = "Trente";
    public static final String FORTY = "Quarante";

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
