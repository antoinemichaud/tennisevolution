package tennis.game.withlife;

import tennis.display.EnglishGameDisplayer;
import tennis.display.GenericDisplayer;
import tennis.display.TennisGameDisplayer;
import tennis.game.base.TennisGame;
import tennis.game.base.TennisGameUtils;

public class TennisGameKataContainer {

    public String displayScore(String player1, int player1score, String player2, int player2score) {
        TennisGameWithLife tennisGame = new TennisGameWithLife();
        TennisGameDisplayer tennisGameDisplayer = new EnglishGameDisplayer(player1, player2, tennisGame);
        TennisGameUtils.initScores(tennisGame, player1, player1score, player2, player2score);
        return getScore(tennisGameDisplayer);
    }

    public String displayScore(String language, String player1, int player1score, String player2, int player2score) {
        TennisGame tennisGame = new TennisGameWithLife();
        TennisGameDisplayer tennisGameDisplayer = new GenericDisplayer(language, player1, player2, tennisGame);
        TennisGameUtils.initScores(tennisGame, player1, player1score, player2, player2score);
        return getScore(tennisGameDisplayer);
    }

    private String getScore(TennisGameDisplayer game) {
        return game.getScore();
    }
}
