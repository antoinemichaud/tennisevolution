package tennis.set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class TennisSetKataContainersParametersTest {

    private final GamesScores gamesScores;
    private final String mainScoreDisplayedExpected;

    public TennisSetKataContainersParametersTest(GamesScores gamesScores, String mainScoreDisplayedExpected) {
        this.gamesScores = gamesScores;
        this.mainScoreDisplayedExpected = mainScoreDisplayedExpected;
    }

    @Parameters
    public static Collection<Object[]> getAllScores() {
        GamesScores fourToFive = GamesScores.with(1, 4).then(4, 1).then(1, 4)
                .then(4, 1).then(1, 4)
                .then(4, 1).then(1, 4)
                .then(4, 1).then(1, 4);

        GamesScores fiveToSix = fourToFive.then(4, 2).then(2, 4);
        GamesScores sevenToFive = fourToFive.then(4, 2).then(4, 2).then(4, 1);
        return Arrays.asList(new Object[][]{
                {fourToFive, "4-5 Love-All"},
                {fourToFive.then(2, 3), "4-5 Thirty-Forty"},
                {fourToFive.then(3, 5), "Set for player2!"},
                {fiveToSix, "Set for player2!"},
                {fiveToSix.then(2, 4), "Set for player2!"},
                {sevenToFive, "Set for player1!"}
        });
    }

    @Test
    public void should_have_right_regular_display() {
        TennisSetKataContainer tennisGameKataContainer = new TennisSetKataContainer();
        String scoreDisplayed = tennisGameKataContainer.displayScore(gamesScores.getPlayer1Scores(), gamesScores.getPlayer2Scores());
        assertThat(scoreDisplayed).isEqualTo(this.mainScoreDisplayedExpected);
    }

}