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
        TennisSet tennisSet = new TennisSet();
        tennisSet.player1WonPoint();
        assertThat(tennisSet.score()).isEqualTo("0-0 Fifteen-Love");
    }
}