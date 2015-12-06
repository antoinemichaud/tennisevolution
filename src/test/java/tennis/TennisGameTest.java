package tennis;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class TennisGameTest {

    @Test
    public void should_score_0_0_for_1_1() throws Exception {
        // Given
        TennisGame tennisGame = new TennisGame(1, 1);

        // When / Then
        assertThat(tennisGame.score()).isEqualTo(TennisScores.scores(TennisScore.ZERO, TennisScore.ZERO));
    }

    @Test
    public void should_score_15_15_for_2_2() throws Exception {
        // Given
        TennisGame tennisGame = new TennisGame(2, 2);

        // When / Then
        assertThat(tennisGame.score()).isEqualTo(TennisScores.scores(TennisScore.FIFTEEN, TennisScore.FIFTEEN));
    }

    @Test
    public void should_score_game_any_for_4_3() throws Exception {
        // Given
        TennisGame tennisGame = new TennisGame(5, 4);

        // When / Then
        assertThat(tennisGame.score()).isEqualTo(TennisScores.scores(TennisScore.GAME, TennisScore.ANY));
    }
}