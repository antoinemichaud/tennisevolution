package tennis.container;

import tennis.game.EnglishGameDisplayer;
import tennis.game.FiguresGameDisplayer;
import tennis.game.FrenchGameDisplayer;
import tennis.game.TennisGameDisplayer;

public class TennisGameKataContainer {

    public String displayScore(String player1, int player1score, String player2, int player2score) {
        TennisGameDisplayer tennisGameDisplayer = new EnglishGameDisplayer(player1, player2);
        TennisGameUtils.initScores(tennisGameDisplayer, player1, player1score, player2, player2score);
        return getScore(tennisGameDisplayer);
    }

    public String displayAlternativeScore(String player1, int player1Score, String player2, int player2Score) {
        FiguresGameDisplayer tennisGame = new FiguresGameDisplayer(player1, player2);
        TennisGameUtils.initScores(tennisGame, player1, player1Score, player2, player2Score);
        return getScore(tennisGame);
    }

    public String displayFrenchScore(String player1, int player1Score, String player2, int player2Score) {
        TennisGameDisplayer tennisGameDisplayer = new FrenchGameDisplayer(player1, player2);
        TennisGameUtils.initScores(tennisGameDisplayer, player1, player1Score, player2, player2Score);
        return getScore(tennisGameDisplayer);
    }

    private String getScore(TennisGameDisplayer game) {
        return game.getScore();
    }
}
