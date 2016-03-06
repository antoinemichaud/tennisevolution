package tennis.game.withlife;

import tennis.display.*;
import tennis.game.base.TennisGame;
import tennis.game.base.TennisGameUtils;

public class TennisGameKataContainer {

    public String displayScore(String player1, int player1score, String player2, int player2score) {
        TennisGameWithLife tennisGame = new TennisGameWithLife();
        TennisGameDisplayer tennisGameDisplayer = new EnglishGameDisplayer(player1, player2, tennisGame);
        TennisGameUtils.initScores(tennisGame, player1, player1score, player2, player2score);
        return getScore(tennisGameDisplayer);
    }

    public String displayAlternativeScore(String player1, int player1Score, String player2, int player2Score) {
        TennisGame tennisGame = new TennisGameWithLife();
        FiguresGameDisplayer gameDisplayer = new FiguresGameDisplayer(player1, player2, tennisGame);
        TennisGameUtils.initScores(tennisGame, player1, player1Score, player2, player2Score);
        return getScore(gameDisplayer);
    }

    public String displayFrenchScore(String player1, int player1Score, String player2, int player2Score) {
        TennisGameWithLife tennisGame = new TennisGameWithLife();
        TennisGameDisplayer tennisGameDisplayer = new FrenchGameDisplayer(player1, player2, tennisGame);
        TennisGameUtils.initScores(tennisGame, player1, player1Score, player2, player2Score);
        return getScore(tennisGameDisplayer);
    }

    public String displayGermanScore(String player1Name, int player1Score, String player2Name, int player2Score) {
        TennisGameWithLife tennisGame = new TennisGameWithLife();
        TennisGameDisplayer tennisGameDisplayer = new GermanGameDisplayer(player1Name, player2Name, tennisGame);
        TennisGameUtils.initScores(tennisGame, player1Name, player1Score, player2Name, player2Score);
        return getScore(tennisGameDisplayer);
    }

    private String getScore(TennisGameDisplayer game) {
        return game.getScore();
    }
}
