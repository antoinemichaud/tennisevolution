package tennis.history;

import org.junit.Test;
import tennis.api.GameQuestion;

import java.util.ArrayList;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static tennis.history.WhichPlayer.PLAYER_ONE;

public class AggregatorTest {

    @Test
    public void should_display_0_0() throws Exception {
        // Given
        Aggregator aggregator = new Aggregator("player1", "player2");

        HistoryKeeper historyKeeper = mock(HistoryKeeper.class);
        when(historyKeeper.list()).thenReturn(new ArrayList<>());

        // When
        GameQuestion gameScore = aggregator.aggregateToGameScore(historyKeeper);


        // Then
        assertThat(gameScore.getPlayer1GameScore().getPlayerName()).isEqualTo("player1");
        assertThat(gameScore.getPlayer1GameScore().getPlayerScore()).isEqualTo(0);
        assertThat(gameScore.getPlayer2GameScore().getPlayerName()).isEqualTo("player2");
        assertThat(gameScore.getPlayer2GameScore().getPlayerScore()).isEqualTo(0);
    }

    @Test
    public void should_display_alfred_henri() throws Exception {
        // Given
        Aggregator aggregator = new Aggregator("alfred", "henri");

        HistoryKeeper historyKeeper = mock(HistoryKeeper.class);
        when(historyKeeper.list()).thenReturn(new ArrayList<>());

        // When
        GameQuestion gameScore = aggregator.aggregateToGameScore(historyKeeper);

        // Then
        assertThat(gameScore.getPlayer1GameScore().getPlayerName()).isEqualTo("alfred");
        assertThat(gameScore.getPlayer1GameScore().getPlayerScore()).isEqualTo(0);
        assertThat(gameScore.getPlayer2GameScore().getPlayerName()).isEqualTo("henri");
        assertThat(gameScore.getPlayer2GameScore().getPlayerScore()).isEqualTo(0);
    }

    @Test
    public void should_display_1_0() throws Exception {
        // Given
        Aggregator aggregator = new Aggregator("player1", "player2");

        HistoryKeeper historyKeeper = mock(HistoryKeeper.class);
        when(historyKeeper.list()).thenReturn(newArrayList(PLAYER_ONE));

        // When
        GameQuestion gameScore = aggregator.aggregateToGameScore(historyKeeper);


        // Then
        assertThat(gameScore.getPlayer1GameScore().getPlayerName()).isEqualTo("player1");
        assertThat(gameScore.getPlayer1GameScore().getPlayerScore()).isEqualTo(1);
        assertThat(gameScore.getPlayer2GameScore().getPlayerName()).isEqualTo("player2");
        assertThat(gameScore.getPlayer2GameScore().getPlayerScore()).isEqualTo(0);
    }
}