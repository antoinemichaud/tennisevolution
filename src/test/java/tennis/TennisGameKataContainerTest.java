package tennis;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TennisGameKataContainerTest {

    private TennisGameKataContainer tennisGameKataContainer;

    @Before
    public void setUp() throws Exception {
        tennisGameKataContainer = new TennisGameKataContainer();
    }

    @Test
    public void should_display_advantage_bertrand() throws Exception {
        // Given / When
        String scoreDisplayed = tennisGameKataContainer.displayScore("Bertrand", 4, "Nicolas", 3);

        // Then
        assertThat(scoreDisplayed).isEqualTo("Advantage Bertrand");
    }

    @Test
    public void should_display_win_for_nicolas() throws Exception {
        // Given / When
        String scoreDisplayed = tennisGameKataContainer.displayScore("Bertrand", 4, "Nicolas", 6);

        // Then
        assertThat(scoreDisplayed).isEqualTo("Win for Nicolas");
    }
}