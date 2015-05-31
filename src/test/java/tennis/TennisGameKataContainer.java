package tennis;

public class TennisGameKataContainer {

    public String displayScore(String player1, int player1score, String player2, int player2score) {
        TennisGame tennisGame = new EnglishTennisGame(player1, player2);
        TennisGameTestUtils.initScores(tennisGame, player1, player1score, player2, player2score);
        return getScore(tennisGame);
    }

    public String displayAlternativeScore(String player1, int player1Score, String player2, int player2Score) {
        TennisGameAlt tennisGame = new TennisGameAlt(player1, player2);
        TennisGameTestUtils.initScores(tennisGame, player1, player1Score, player2, player2Score);
        return getScore(tennisGame);
    }

    public String displayFrenchScore(String player1, int player1Score, String player2, int player2Score) {
        TennisGame tennisGame = new TennisGameFrench(player1, player2);
        TennisGameTestUtils.initScores(tennisGame, player1, player1Score, player2, player2Score);
        return getScore(tennisGame);
    }

    private String getScore(TennisGame game) {
        return game.getScore();
    }
}
