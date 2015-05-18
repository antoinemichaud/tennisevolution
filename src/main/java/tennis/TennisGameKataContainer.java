package tennis;

public class TennisGameKataContainer {


    public String displayScore(String player1, int player1score, String player2, int player2score) {
        TennisGame tennisGame = new TennisGame(player1, player2);
        return checkAllScores(tennisGame, player1, player1score, player2, player2score);
    }

    public String checkAllScores(TennisGame game, String player1Name, int player1Score, String player2Name, int player2Score) {
        int highestScore = Math.max(player1Score, player2Score);
        for (int i = 0; i < highestScore; i++) {
            if (i < player1Score)
                game.wonPoint(player1Name);
            if (i < player2Score)
                game.wonPoint(player2Name);
        }
        return game.getScore();
    }
}
