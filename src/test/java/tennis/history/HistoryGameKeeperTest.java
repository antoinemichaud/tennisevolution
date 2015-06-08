package tennis.history;

import org.junit.Before;
import org.junit.Test;
import tennis.game.TennisGame;

import static org.assertj.core.api.Assertions.assertThat;

public class HistoryGameKeeperTest {

    private TennisGame tennisGame;
    private HistoryGameKeeper historyGameKeeper;

    @Before
    public void setUp() throws Exception {
        tennisGame = new TennisGame();
        historyGameKeeper = new HistoryGameKeeper(tennisGame);
    }

    @Test
    public void should_have_empty_array_on_empty_game() throws Exception {
        assertThat(historyGameKeeper.list()).isEmpty();
    }

    @Test
    public void should_have_singleton_with_0() throws Exception {
        // When
        tennisGame.incrementPlayer1Score();

        // Then
        assertThat(historyGameKeeper.list()).containsExactly(0);
    }

    @Test
    public void should_have_singleton_with_1() throws Exception {
        // When
        tennisGame.incrementPlayer2Score();

        // Then
        assertThat(historyGameKeeper.list()).containsExactly(1);
    }
}