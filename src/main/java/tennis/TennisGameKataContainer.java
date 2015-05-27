package tennis;

public class TennisGameKataContainer {

    public String displayScore(String player1, int player1score, String player2, int player2score) {
        TennisGame tennisGame = new TennisGame(player1, player2);
        initScores(tennisGame, player1, player1score, player2, player2score);
        return getScore(tennisGame);
    }

    public String displayAlternativeScore(String player1, int player1Score, String player2, int player2Score) {
        TennisGameAlt tennisGame = new TennisGameAlt(player1, player2);
        initScores(tennisGame, player1, player1Score, player2, player2Score);
        return getScore(tennisGame);
    }

    public String displayFrenchScore(String player1, int player1Score, String player2, int player2Score) {
        TennisGameAlt tennisGame = new TennisGameAlt(player1, player2);
        initScores(tennisGame, player1, player1Score, player2, player2Score);
        return getScore(tennisGame);
    }

    private void initScores(TennisGame game, String player1Name, int player1Score, String player2Name, int player2Score) {
        int highestScore = Math.max(player1Score, player2Score);
        for (int i = 0; i < highestScore; i++) {
            if (i < player1Score)
                game.wonPoint(player1Name);
            if (i < player2Score)
                game.wonPoint(player2Name);
        }
    }

    private String getScore(TennisGame game) {
        return game.getScore();
    }
}
