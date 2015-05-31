package tennis.game;

public class TennisGameTestUtils {
    public static void initScores(TennisGameDisplayer game, String player1Name, int player1Score, String player2Name, int player2Score) {
        int highestScore = Math.max(player1Score, player2Score);
        for (int i = 0; i < highestScore; i++) {
            if (i < player1Score)
                game.wonPoint(player1Name);
            if (i < player2Score)
                game.wonPoint(player2Name);
        }
    }

    public static void initScores(TennisGame game, int player1Score, int player2Score) {
        int highestScore = Math.max(player1Score, player2Score);
        for (int i = 0; i < highestScore; i++) {
            if (i < player1Score)
                game.incrementPlayer1Score();
            if (i < player2Score)
                game.incrementPlayer2Score();
        }
    }
}
