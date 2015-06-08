package tennis.generator;

import org.junit.Test;
import tennis.game.PlayerScore;
import tennis.history.HistoryGameKeeper;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class TennisSetGeneratorTest {

    @Test
    public void should_generate_a_list_with_one_0() throws Exception {
        // Given
        TennisSetGenerator tennisSetGenerator = new TennisSetGenerator(new Random() {
            @Override
            public double nextDouble() {
                return 0;
            }
        });

        // When
        HistoryGameKeeper tennisGameHistory = tennisSetGenerator.generate(tennisGame -> true);

        // Then
        assertThat(tennisGameHistory.list()).containsExactly(0);
    }

    @Test
    public void should_generate_a_list_with_one_1() throws Exception {
        // Given
        TennisSetGenerator tennisSetGenerator = new TennisSetGenerator(new Random() {
            @Override
            public double nextDouble() {
                return 1;
            }
        });

        // When
        HistoryGameKeeper tennisGameHistory = tennisSetGenerator.generate(tennisGame -> true);

        // Then
        assertThat(tennisGameHistory.list()).containsExactly(1);
    }

    @Test
    public void should_generate_a_list_with_0_then_0() throws Exception {
        // Given
        TennisSetGenerator tennisSetGenerator = new TennisSetGenerator(new Random() {
            @Override
            public double nextDouble() {
                return 0;
            }
        });

        // When
        HistoryGameKeeper tennisGameHistory = tennisSetGenerator.generate(tennisGame ->
                tennisGame.getScore().firstPlayerScore() == PlayerScore.THIRTY);

        // Then
        assertThat(tennisGameHistory.list()).containsExactly(0, 0);
    }

    @Test
    public void generate_a_game() throws Exception {
        // Given
        TennisSetGenerator tennisSetGenerator = new TennisSetGenerator(new Random());

        // When
        HistoryGameKeeper tennisGameHistory = tennisSetGenerator.generate(tennisGame ->
                tennisGame.getScore().firstPlayerScore() == PlayerScore.GAME ||
                        tennisGame.getScore().secondPlayerScore() == PlayerScore.GAME);

        // Then
        System.out.println(tennisGameHistory.list());
    }
}