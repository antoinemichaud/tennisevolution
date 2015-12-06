package tennis;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;
import static tennis.TennisScore.*;
import static tennis.TennisSetState.*;

public class TennisSetTest {

    @Test
    public void should_give_00_00() throws Exception {
        // Given
        TennisSet tennisSet = new TennisSet();

        // When / Then
        assertThat(tennisSet.score()).isEqualTo(new TennisSetScore(0, 0, ZERO, ZERO));
    }

    @Test
    public void should_give_00_15_0() throws Exception {
        // Given
        TennisSet tennisSet = new TennisSet();

        // When
        tennisSet.player1Win();

        // Then
        assertThat(tennisSet.score()).isEqualTo(new TennisSetScore(0, 0, FIFTEEN, ZERO));
    }

    @Test
    public void should_give_00_15_15() throws Exception {
        // Given
        TennisSet tennisSet = new TennisSet();

        // When
        tennisSet.player1Win();
        tennisSet.player2Win();

        // Then
        assertThat(tennisSet.score()).isEqualTo(new TennisSetScore(0, 0, FIFTEEN, FIFTEEN));
    }

    @Test
    public void should_give_00_A_40() throws Exception {
        // Given
        TennisSet tennisSet = new TennisSet();

        // When
        tennisSet.player1Win();
        tennisSet.player1Win();
        tennisSet.player1Win();
        tennisSet.player2Win();
        tennisSet.player2Win();
        tennisSet.player2Win();
        tennisSet.player1Win();

        // Then
        assertThat(tennisSet.score()).isEqualTo(new TennisSetScore(0, 0, ADVANTAGE, FORTY));
    }

    @Test
    public void should_give_10_0_0() throws Exception {
        // Given
        TennisSet tennisSet = new TennisSet();

        // When
        tennisSet.player1Win();
        tennisSet.player1Win();
        tennisSet.player1Win();
        tennisSet.player2Win();
        tennisSet.player2Win();
        tennisSet.player2Win();
        tennisSet.player1Win();
        tennisSet.player1Win();

        // Then
        assertThat(tennisSet.score()).isEqualTo(new TennisSetScore(1, 0, ZERO, ZERO));
    }

    @Test
    public void should_give_01_0_0() throws Exception {
        // Given
        TennisSet tennisSet = new TennisSet();

        // When
        tennisSet.player2Win();
        tennisSet.player2Win();
        tennisSet.player2Win();
        tennisSet.player1Win();
        tennisSet.player1Win();
        tennisSet.player1Win();
        tennisSet.player2Win();
        tennisSet.player2Win();

        // Then
        assertThat(tennisSet.score()).isEqualTo(new TennisSetScore(0, 1, ZERO, ZERO));
    }

    @Test
    public void should_give_player1_winner() throws Exception {
        // Given
        TennisSet tennisSet = new TennisSet();

        // When
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                tennisSet.player1Win();
            }
        }

        // Then
        TennisSetScore tennisSetScore = new TennisSetScore(6, 0, ZERO, ZERO);
        assertThat(tennisSet.score()).isEqualTo(tennisSetScore);
        assertThat(tennisSet.state()).isEqualTo(PLAYER_1_WON);
    }

    @Test
    public void should_give_player2_winner() throws Exception {
        // Given
        TennisSet tennisSet = new TennisSet();

        // When
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                tennisSet.player2Win();
            }
        }

        // Then
        TennisSetScore tennisSetScore = new TennisSetScore(0, 6, ZERO, ZERO);
        assertThat(tennisSet.score()).isEqualTo(tennisSetScore);
        assertThat(tennisSet.state()).isEqualTo(PLAYER_2_WON);
    }

    @Test
    public void should_give_noone_as_a_winner() throws Exception {
        // Given
        TennisSet tennisSet = new TennisSet();

        // When
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                tennisSet.player2Win();
            }
        }

        // Then
        TennisSetScore tennisSetScore = new TennisSetScore(0, 5, ZERO, ZERO);
        assertThat(tennisSet.score()).isEqualTo(tennisSetScore);
        assertThat(tennisSet.state()).isEqualTo(NO_ONE_WIN);
    }

}