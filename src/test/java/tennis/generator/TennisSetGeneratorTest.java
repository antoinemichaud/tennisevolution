package tennis.generator;

import org.junit.Test;
import tennis.game.PlayerScore;
import tennis.history.HistoryKeeper;

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
        HistoryKeeper tennisGameHistory = tennisSetGenerator.generate(tennisGame -> true);

        // Then
        assertThat(tennisGameHistory.list()).containsExactly(0);
    }

    @Test
    public void should_generate_a_list_with_four_0() throws Exception {
        // Given
        TennisSetGenerator tennisSetGenerator = new TennisSetGenerator(new Random() {
            @Override
            public double nextDouble() {
                return 0;
            }
        });

        // When
        HistoryKeeper tennisGameHistory = tennisSetGenerator.generateSet(tennisSet -> tennisSet.score().equals("1-0 Love-All"));

        // Then
        assertThat(tennisGameHistory.list()).containsExactly(0, 0, 0, 0);
    }

    @Test
    public void should_generate_a_list_with_five_0() throws Exception {
        // Given
        TennisSetGenerator tennisSetGenerator = new TennisSetGenerator(new Random() {
            @Override
            public double nextDouble() {
                return 0;
            }
        });

        // When
        HistoryKeeper tennisGameHistory = tennisSetGenerator.generateSet(tennisSet -> tennisSet.score().equals("1-0 Fifteen-Love"));

        // Then
        assertThat(tennisGameHistory.list()).containsExactly(0, 0, 0, 0, 0);
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
        HistoryKeeper tennisGameHistory = tennisSetGenerator.generate(tennisGame -> true);

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
        HistoryKeeper tennisGameHistory = tennisSetGenerator.generate(tennisGame ->
                tennisGame.getScore().firstPlayerScore() == PlayerScore.THIRTY);

        // Then
        assertThat(tennisGameHistory.list()).containsExactly(0, 0);
    }

    @Test
    public void generate_a_set() throws Exception {
        // Given
        TennisSetGenerator tennisSetGenerator = new TennisSetGenerator(new Random());

        // When
        HistoryKeeper tennisHistory = tennisSetGenerator.generateSet(tennisSet -> tennisSet.score().contains("Set for"));

        // Then
        System.out.println(tennisSetGenerator.tennisSetScore());
        System.out.println(tennisHistory.list());
    }
}