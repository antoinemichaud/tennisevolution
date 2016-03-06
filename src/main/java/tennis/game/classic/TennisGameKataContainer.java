package tennis.game.classic;

public class TennisGameKataContainer {

    public String displayScore(String player1, int player1score, String player2, int player2score) {
        return displayScore("english", player1, player1score, player2, player2score);
    }

    public String displayFrenchScore(String player1, int player1Score, String player2, int player2Score) {
        return displayScore("french", player1, player1Score, player2, player2Score);
    }

    public String displayScore(String language, String player1, int player1score, String player2, int player2score) {
        TennisGame tennisGame = new TennisGame();
        TennisGameDisplayer tennisGameDisplayer = new GenericDisplayer(language, player1, player2, tennisGame);
        TennisGameUtils.initScores(tennisGame, player1, player1score, player2, player2score);
        return getScore(tennisGameDisplayer);
    }

    private String getScore(TennisGameDisplayer game) {
        return game.getScore();
    }

}
