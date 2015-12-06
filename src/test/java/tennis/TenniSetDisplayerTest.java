package tennis;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.*;

public class TenniSetDisplayerTest {

    @Test
    public void should_display() throws Exception {
        // Given
        TenniSetDisplayer tenniSetDisplayer = new TenniSetDisplayer(Collections.emptyList());

        // When / Then
        assertThat(tenniSetDisplayer.display()).isEqualTo("0-0 Love-All");
    }

    @Test
    public void should_display_set_according_to_ints() throws Exception {
        // Given
        TenniSetDisplayer tenniSetDisplayer = new TenniSetDisplayer(Arrays.asList(1, 2, 1));

        // When / Then
        assertThat(tenniSetDisplayer.display()).isEqualTo("0-0 Thirty-Fifteen");
    }

    @Test
    public void should_display_won_set() throws Exception {
        // Given
        ArrayList<Integer> player1WonPoints = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                player1WonPoints.add(1);
            }
        }

        TenniSetDisplayer tenniSetDisplayer = new TenniSetDisplayer(player1WonPoints);

        // When / Then
        assertThat(tenniSetDisplayer.display()).isEqualTo("Set for player1!");
    }
}