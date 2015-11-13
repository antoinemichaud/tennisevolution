package tennis.history;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static tennis.history.WhichPlayer.PLAYER_ONE;

public class AggregatorTest {

    @Test
    public void should_display_0_0() throws Exception {
        // Given
        Aggregator aggregator = new Aggregator("player1", "player2");

        HistoryKeeper historyKeeper = Mockito.mock(HistoryKeeper.class);
        Mockito.when(historyKeeper.list()).thenReturn(new ArrayList<>());

        // When
        Map<String, Integer> gameScore = aggregator.aggregateToGameScore(historyKeeper);


        // Then
        assertThat(gameScore)
                .hasSize(2)
                .containsEntry("player1", 0)
                .containsEntry("player2", 0);
    }

    @Test
    public void should_display_alfred_henri() throws Exception {
        // Given
        Aggregator aggregator = new Aggregator("alfred", "henri");

        HistoryKeeper historyKeeper = Mockito.mock(HistoryKeeper.class);
        Mockito.when(historyKeeper.list()).thenReturn(new ArrayList<>());

        // When
        Map<String, Integer> gameScore = aggregator.aggregateToGameScore(historyKeeper);


        // Then
        assertThat(gameScore)
                .hasSize(2)
                .containsEntry("alfred", 0)
                .containsEntry("henri", 0);
    }

    @Test
    public void should_display_1_0() throws Exception {
        // Given
        Aggregator aggregator = new Aggregator("player1", "player2");

        HistoryKeeper historyKeeper = Mockito.mock(HistoryKeeper.class);
        Mockito.when(historyKeeper.list()).thenReturn(newArrayList(PLAYER_ONE));

        // When
        Map<String, Integer> gameScore = aggregator.aggregateToGameScore(historyKeeper);


        // Then
        assertThat(gameScore)
                .hasSize(2)
                .containsEntry("player1", 1)
                .containsEntry("player2", 0);
    }
}