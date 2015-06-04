package tennis.set;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TennisSetTest {

    @Test
    public void should_have_0_0_on_no_point_won() throws Exception {
        assertThat(new TennisSet().score()).isEqualTo("0-0 Love-All");
    }

    @Test
    public void should_have_0_0_then_Fifteen_Love_on_one_point_won_by_player1() throws Exception {
        //Given
        TennisSet tennisSet = new TennisSet();

        //When
        tennisSet.player1WonPoint();

        //Then
        assertThat(tennisSet.score()).isEqualTo("0-0 Fifteen-Love");
    }

    @Test
    public void should_have_0_0_then_Love_Fifteen_on_one_point_won_by_player2() throws Exception {
        //Given
        TennisSet tennisSet = new TennisSet();

        //When
        tennisSet.player2WonPoint();

        //Then
        assertThat(tennisSet.score()).isEqualTo("0-0 Love-Fifteen");
    }

    @Test
    public void should_have_1_0_then_Love_All_on_four_points_won_by_player1() throws Exception {
        //Given
        TennisSet tennisSet = new TennisSet();

        //When
        tennisSet.player1WonPoint();
        tennisSet.player1WonPoint();
        tennisSet.player1WonPoint();
        tennisSet.player1WonPoint();

        //Then
        assertThat(tennisSet.score()).isEqualTo("1-0 Love-All");
    }
}