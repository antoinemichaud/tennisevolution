package tennis.history;

import org.junit.Before;
import org.junit.Test;
import tennis.game.classic.TennisGame;

import static org.assertj.core.api.Assertions.assertThat;
import static tennis.history.WhichPlayer.PLAYER_ONE;
import static tennis.history.WhichPlayer.PLAYER_TWO;

public class HistoryKeeperTest {

    private TennisGame tennisGame;
    private HistoryKeeper historyKeeper;

    @Before
    public void setUp() throws Exception {
        tennisGame = new TennisGame();
        historyKeeper = new HistoryKeeper(tennisGame);
    }

    @Test
    public void should_have_empty_array_on_empty_game() throws Exception {
        assertThat(historyKeeper.list()).isEmpty();
    }

    @Test
    public void should_have_singleton_with_0() throws Exception {
        // When
        tennisGame.incrementPlayer1Score();

        // Then
        assertThat(historyKeeper.list()).containsExactly(PLAYER_ONE);
    }

    @Test
    public void should_have_singleton_with_1() throws Exception {
        // When
        tennisGame.incrementPlayer2Score();

        // Then
        assertThat(historyKeeper.list()).containsExactly(PLAYER_TWO);
    }
}