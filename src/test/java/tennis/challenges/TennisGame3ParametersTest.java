package tennis.challenges;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import tennis.container.TennisGameKataContainer;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class TennisGame3ParametersTest {

    private int player1Score;
    private int player2Score;
    private String mainScoreDisplayedExpected;
    private String alternativeScoreDisplayedExpected;
    private String frenchScoreDisplayedExpected;

    public TennisGame3ParametersTest(int player1Score,
                                     int player2Score,
                                     String mainScoreDisplayedExpected,
                                     String alternativeScoreDisplayedExpected,
                                     String frenchScoreDisplayedExpected) {
        this.player1Score = player1Score;
        this.player2Score = player2Score;
        this.mainScoreDisplayedExpected = mainScoreDisplayedExpected;
        this.alternativeScoreDisplayedExpected = alternativeScoreDisplayedExpected;
        this.frenchScoreDisplayedExpected = frenchScoreDisplayedExpected;
    }

    @Parameters
    public static Collection<Object[]> getAllScores() {
        return Arrays.asList(new Object[][]{
                {0, 0, "Love-All", "0-0", "Zéro partout"},
                {1, 1, "Fifteen-All", "15-15", "Quinze A"},
                {2, 2, "Thirty-All", "30-30", "Trente A"},
                {3, 3, "Deuce", "40-40", "Egalité"},
                {4, 4, "Deuce", "40-40", "Egalité"},

                {1, 0, "Fifteen-Love", "15-0", "Quinze-Zéro"},
                {0, 1, "Love-Fifteen", "0-15", "Zéro-Quinze"},
                {2, 0, "Thirty-Love", "30-0", "Trente-Zéro"},
                {0, 2, "Love-Thirty", "0-30", "Zéro-Trente"},
                {3, 0, "Forty-Love", "40-0", "Quarante-Zéro"},
                {0, 3, "Love-Forty", "0-40", "Zéro-Quarante"},
                {4, 0, "Win for player1", "Game player1!", "Jeu pour player1"},
                {0, 4, "Win for player2", "Game player2!", "Jeu pour player2"},

                {2, 1, "Thirty-Fifteen", "30-15", "Trente-Quinze"},
                {1, 2, "Fifteen-Thirty", "15-30", "Quinze-Trente"},
                {3, 1, "Forty-Fifteen", "40-15", "Quarante-Quinze"},
                {1, 3, "Fifteen-Forty", "15-40", "Quinze-Quarante"},
                {4, 1, "Win for player1", "Game player1!", "Jeu pour player1"},
                {1, 4, "Win for player2", "Game player2!", "Jeu pour player2"},

                {3, 2, "Forty-Thirty", "40-30", "Quarante-Trente"},
                {2, 3, "Thirty-Forty", "30-40", "Trente-Quarante"},
                {4, 2, "Win for player1", "Game player1!", "Jeu pour player1"},
                {2, 4, "Win for player2", "Game player2!", "Jeu pour player2"},

                {4, 3, "Advantage player1", "A-40", "Avantage player1"},
                {3, 4, "Advantage player2", "40-A", "Avantage player2"},
                {5, 4, "Advantage player1", "A-40", "Avantage player1"},
                {4, 5, "Advantage player2", "40-A", "Avantage player2"},
                {15, 14, "Advantage player1", "A-40", "Avantage player1"},
                {14, 15, "Advantage player2", "40-A", "Avantage player2"},

                {6, 4, "Win for player1", "Game player1!", "Jeu pour player1"},
                {4, 6, "Win for player2", "Game player2!", "Jeu pour player2"},
                {16, 14, "Win for player1", "Game player1!", "Jeu pour player1"},
                {14, 16, "Win for player2", "Game player2!", "Jeu pour player2"},
        });
    }

    @Test
    public void should_have_right_regular_display() {
        TennisGameKataContainer tennisGameKataContainer = new TennisGameKataContainer();
        String scoreDisplayed = tennisGameKataContainer.displayScore("player1", this.player1Score, "player2", this.player2Score);
        assertThat(scoreDisplayed).isEqualTo(this.mainScoreDisplayedExpected);
    }

    @Test
    public void should_have_right_alternative_display() throws Exception {
        TennisGameKataContainer tennisGameKataContainer = new TennisGameKataContainer();
        String alternativeScoreDisplayed = tennisGameKataContainer.displayAlternativeScore("player1", this.player1Score, "player2", this.player2Score);
        assertThat(alternativeScoreDisplayed).isEqualTo(this.alternativeScoreDisplayedExpected);
    }

    @Test
    public void should_have_right_french_display() throws Exception {
        TennisGameKataContainer tennisGameKataContainer = new TennisGameKataContainer();
        String alternativeScoreDisplayed = tennisGameKataContainer.displayFrenchScore("player1", this.player1Score, "player2", this.player2Score);
        assertThat(alternativeScoreDisplayed).isEqualTo(this.frenchScoreDisplayedExpected);
    }
}