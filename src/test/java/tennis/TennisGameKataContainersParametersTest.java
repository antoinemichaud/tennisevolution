package tennis;

import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class TennisGameKataContainersParametersTest {

    private int player1Score;
    private int player2Score;
    private String mainScoreDisplayedExpected;
    private String alternativeScoreDisplayedExpected;

    public TennisGameKataContainersParametersTest(int player1Score,
                                                  int player2Score,
                                                  String mainScoreDisplayedExpected, String alternativeScoreDisplayedExpected) {
        this.player1Score = player1Score;
        this.player2Score = player2Score;
        this.mainScoreDisplayedExpected = mainScoreDisplayedExpected;
        this.alternativeScoreDisplayedExpected = alternativeScoreDisplayedExpected;
    }

    @Parameters
    public static Collection<Object[]> getAllScores() {
        return Arrays.asList(new Object[][]{
                {0, 0, "Love-All", "0-0"},
                {1, 1, "Fifteen-All", "15-15"},
                {2, 2, "Thirty-All", "30-30"},
                {3, 3, "Deuce", "40-40"},
                {4, 4, "Deuce", "40-40"},

                {1, 0, "Fifteen-Love", "15-0"},
                {0, 1, "Love-Fifteen", "0-15"},
                {2, 0, "Thirty-Love", "30-0"},
                {0, 2, "Love-Thirty", "0-30"},
                {3, 0, "Forty-Love","40-0"},
                {0, 3, "Love-Forty", "0-40"},
                {4, 0, "Win for player1", "Game player 1!"},
                {0, 4, "Win for player2", "Game player 2!"},

                {2, 1, "Thirty-Fifteen", "30-15"},
                {1, 2, "Fifteen-Thirty", "15-30"},
                {3, 1, "Forty-Fifteen", "40-15"},
                {1, 3, "Fifteen-Forty", "15-40"},
                {4, 1, "Win for player1", "Game player 1!"},
                {1, 4, "Win for player2", "Game player 2!"},

                {3, 2, "Forty-Thirty", "40-30"},
                {2, 3, "Thirty-Forty", "30-40"},
                {4, 2, "Win for player1", "Game player 1!"},
                {2, 4, "Win for player2", "Game player 2!"},

                {4, 3, "Advantage player1", "A-40"},
                {3, 4, "Advantage player2", "40-A"},
                {5, 4, "Advantage player1", "A-40"},
                {4, 5, "Advantage player2", "40-A"},
                {15, 14, "Advantage player1", "A-40"},
                {14, 15, "Advantage player2", "40-A"},

                {6, 4, "Win for player1", "Game player 1!"},
                {4, 6, "Win for player2", "Game player 2!"},
                {16, 14, "Win for player1", "Game player 1!"},
                {14, 16, "Win for player2", "Game player 2!"},
        });
    }

    public void checkAllScores(TennisGame game) {
        int highestScore = Math.max(this.player1Score, this.player2Score);
        for (int i = 0; i < highestScore; i++) {
            if (i < this.player1Score)
                game.wonPoint("player1");
            if (i < this.player2Score)
                game.wonPoint("player2");
        }
        assertEquals(this.mainScoreDisplayedExpected, game.getScore());
    }

    @Test
    public void should_have_right_regular_display() {
        TennisGameKataContainer tennisGameKataContainer = new TennisGameKataContainer();
        String scoreDisplayed = tennisGameKataContainer.displayScore("player1", this.player1Score, "player2", this.player2Score);
        assertThat(scoreDisplayed).isEqualTo(this.mainScoreDisplayedExpected);
    }

    @Test
    @Ignore
    public void should_have_right_alternative_display() throws Exception {
        TennisGameKataContainer tennisGameKataContainer = new TennisGameKataContainer();
        String alternativeScoreDisplayed = tennisGameKataContainer.displayAlternativeScore("player1", this.player1Score, "player2", this.player2Score);
        assertThat(alternativeScoreDisplayed).isEqualTo(this.alternativeScoreDisplayedExpected);
    }
}