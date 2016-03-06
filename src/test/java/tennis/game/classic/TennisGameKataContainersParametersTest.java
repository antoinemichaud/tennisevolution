package tennis.game.classic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class TennisGameKataContainersParametersTest {

    private int player1Score;
    private int player2Score;
    private String mainScoreDisplayedExpected;
    private String frenchScoreDisplayedExpected;

    public TennisGameKataContainersParametersTest(int player1Score,
                                                  int player2Score,
                                                  String mainScoreDisplayedExpected,
                                                  String frenchScoreDisplayedExpected) {
        this.player1Score = player1Score;
        this.player2Score = player2Score;
        this.mainScoreDisplayedExpected = mainScoreDisplayedExpected;
        this.frenchScoreDisplayedExpected = frenchScoreDisplayedExpected;
    }

    @Parameters
    public static Collection<Object[]> getAllScores() {
        return Arrays.asList(new Object[][]{
                {0, 0, "love-all", "zéro-partout"},
                {1, 1, "fifteen-all", "quinze-partout"},
                {2, 2, "thirty-all", "trente-partout"},
                {3, 3, "deuce", "égalité"},
                {4, 4, "deuce", "égalité"},

                {1, 0, "fifteen-love", "quinze-zéro"},
                {0, 1, "love-fifteen", "zéro-quinze"},
                {2, 0, "thirty-love", "trente-zéro"},
                {0, 2, "love-thirty", "zéro-trente"},
                {3, 0, "forty-love", "quarante-zéro"},
                {0, 3, "love-forty", "zéro-quarante"},
                {4, 0, "win for player1", "jeu pour player1"},
                {0, 4, "win for player2", "jeu pour player2"},

                {2, 1, "thirty-fifteen", "trente-quinze"},
                {1, 2, "fifteen-thirty", "quinze-trente"},
                {3, 1, "forty-fifteen", "quarante-quinze"},
                {1, 3, "fifteen-forty", "quinze-quarante"},
                {4, 1, "win for player1", "jeu pour player1"},
                {1, 4, "win for player2", "jeu pour player2"},

                {3, 2, "forty-thirty", "quarante-trente"},
                {2, 3, "thirty-forty", "trente-quarante"},
                {4, 2, "win for player1", "jeu pour player1"},
                {2, 4, "win for player2", "jeu pour player2"},

                {4, 3, "advantage player1", "avantage player1"},
                {3, 4, "advantage player2", "avantage player2"},
                {5, 4, "advantage player1", "avantage player1"},
                {4, 5, "advantage player2", "avantage player2"},
                {15, 14, "advantage player1", "avantage player1"},
                {14, 15, "advantage player2", "avantage player2"},

                {6, 4, "win for player1", "jeu pour player1"},
                {4, 6, "win for player2", "jeu pour player2"},
                {16, 14, "win for player1", "jeu pour player1"},
                {14, 16, "win for player2", "jeu pour player2"},
        });
    }

    @Test
    public void should_have_right_regular_display() {
        TennisGameKataContainer tennisGameKataContainer = new TennisGameKataContainer();
        String scoreDisplayed = tennisGameKataContainer.displayScore("player1", this.player1Score, "player2", this.player2Score);
        assertThat(scoreDisplayed).isEqualTo(this.mainScoreDisplayedExpected);
    }

    @Test
    public void should_have_right_french_display() throws Exception {
        TennisGameKataContainer tennisGameKataContainer = new TennisGameKataContainer();
        String alternativeScoreDisplayed = tennisGameKataContainer.displayFrenchScore("player1", this.player1Score, "player2", this.player2Score);
        assertThat(alternativeScoreDisplayed).isEqualTo(this.frenchScoreDisplayedExpected);
    }
}