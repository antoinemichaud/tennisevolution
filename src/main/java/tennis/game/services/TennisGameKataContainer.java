package tennis.game.services;

public class TennisGameKataContainer {

    public String displayScore(String player1, int player1score, String player2, int player2score, int servicePlayer) {
        TennisGame tennisGame = new TennisGame();
        TennisGameDisplayer tennisGameDisplayer = new EnglishGameDisplayer(player1, player2, tennisGame, servicePlayer);
        TennisGameUtils.initScores(tennisGame, player1, player1score, player2, player2score);
        return getScore(tennisGameDisplayer);
    }

    public String displayAlternativeScore(String player1, int player1Score, String player2, int player2Score) {
        TennisGame tennisGame = new TennisGame();
        FiguresGameDisplayer gameDisplayer = new FiguresGameDisplayer(player1, player2, tennisGame, 1);
        TennisGameUtils.initScores(tennisGame, player1, player1Score, player2, player2Score);
        return getScore(gameDisplayer);
    }

    public String displayFrenchScore(String player1, int player1Score, String player2, int player2Score) {
        TennisGame tennisGame = new TennisGame();
        TennisGameDisplayer tennisGameDisplayer = new FrenchGameDisplayer(player1, player2, tennisGame, 1);
        TennisGameUtils.initScores(tennisGame, player1, player1Score, player2, player2Score);
        return getScore(tennisGameDisplayer);
    }

    private String getScore(TennisGameDisplayer game) {
        return game.getScore();
    }
}
