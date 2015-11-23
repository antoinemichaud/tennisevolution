package tennis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TennisTest {

    private int player1Score;
    private int player2Score;
    private String expectedScore;

    public TennisTest(int player1Score, int player2Score, String expectedScore) {
        this.player1Score = player1Score;
        this.player2Score = player2Score;
        this.expectedScore = expectedScore;
    }

    @Parameters
    public static Collection<Object[]> getAllScores() {
        return Arrays.asList(new Object[][] {
                {0, 0, "Love-All", "0-0"},
                {1, 1, "Fifteen-All", "15-15"},
                {2, 2, "Thirty-All", "30-30"},
                {3, 3, "Deuce", "40-40"},
                {4, 4, "Deuce", "40-40"},

                {1, 0, "Fifteen-Love", "15-0"},
                {0, 1, "Love-Fifteen", "0-15"},
                {2, 0, "Thirty-Love", "30-0"},
                {0, 2, "Love-Thirty", "0-30"},
                {3, 0, "Forty-Love", "40-0"},
                {0, 3, "Love-Forty", "0-40"},
                {4, 0, "Win for player1", "Game player1!"},
                {0, 4, "Win for player2", "Game player2!"},

                {2, 1, "Thirty-Fifteen", "30-15"},
                {1, 2, "Fifteen-Thirty", "15-30"},
                {3, 1, "Forty-Fifteen", "40-15"},
                {1, 3, "Fifteen-Forty", "15-40"},
                {4, 1, "Win for player1", "Game player1!"},
                {1, 4, "Win for player2", "Game player2!"},

                {3, 2, "Forty-Thirty", "40-30"},
                {2, 3, "Thirty-Forty", "30-40"},
                {4, 2, "Win for player1", "Game player1!"},
                {2, 4, "Win for player2", "Game player2!"},

                {4, 3, "Advantage player1", "A-40"},
                {3, 4, "Advantage player2", "40-A"},
                {5, 4, "Advantage player1", "A-40"},
                {4, 5, "Advantage player2", "40-A"},
                {15, 14, "Advantage player1", "A-40"},
                {14, 15, "Advantage player2", "40-A"},

                {6, 4, "Win for player1", "Game player1!"},
                {4, 6, "Win for player2", "Game player2!"},
                {16, 14, "Win for player1", "Game player1!"},
                {14, 16, "Win for player2", "Game player2!"}
        });
    }

    public void checkAllScores(TennisService game) {
        assertEquals(this.expectedScore, game.displayScore(this.player1Score, this.player2Score));
    }

    @Test
    public void checkAllScoresTennisGame1() {
        TennisService game = new TennisService();
        checkAllScores(game);
    }
}