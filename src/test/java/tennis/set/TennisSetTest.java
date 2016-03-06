package tennis.set;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TennisSetTest {

    private TennisSet tennisSet;

    @Before
    public void setUp() throws Exception {
        tennisSet = new TennisSet();
    }

    @Test
    public void should_have_0_0_on_no_point_won() throws Exception {
        assertThat(new TennisSet().score()).isEqualTo("0-0 Love-All");
    }

    @Test
    public void should_have_0_0_then_Fifteen_Love_on_one_point_won_by_player1() throws Exception {
        //When
        tennisSet.player1WonPoint();

        //Then
        assertThat(tennisSet.score()).isEqualTo("0-0 Fifteen-Love");
    }

    @Test
    public void should_have_0_0_then_Love_Fifteen_on_one_point_won_by_player2() throws Exception {
        //When
        tennisSet.player2WonPoint();

        //Then
        assertThat(tennisSet.score()).isEqualTo("0-0 Love-Fifteen");
    }

    @Test
    public void should_have_1_0_then_Love_All_on_four_points_won_by_player1() throws Exception {
        //When
        player1WinGame();

        //Then
        assertThat(tennisSet.score()).isEqualTo("1-0 Love-All");
    }

    @Test
    public void should_have_2_0_then_Love_All_on_eight_points_won_by_player1() throws Exception {
        //When
        player1WinGame();
        player1WinGame();

        //Then
        assertThat(tennisSet.score()).isEqualTo("2-0 Love-All");
    }

    @Test
    public void should_have_0_1_then_Love_All_on_four_points_won_by_player2() throws Exception {
        //When
        player2WinGame();

        //Then
        assertThat(tennisSet.score()).isEqualTo("0-1 Love-All");
    }

    @Test
    public void should_not_win_if_3_5() throws Exception {
        //When
        player1WinGames(3);
        player2WinGames(5);

        //Then
        assertThat(tennisSet.score()).isEqualTo("3-5 Love-All");
    }

    @Test
    public void player2_should_win_when_4_6() throws Exception {
        //When
        player1WinGames(4);
        player2WinGames(6);

        //Then
        assertThat(tennisSet.score()).isEqualTo("Set for player2!");
    }

    @Test
    public void player1_should_win_when_6_4() throws Exception {
        //When
        player1WinGames(6);
        player2WinGames(4);

        //Then
        assertThat(tennisSet.score()).isEqualTo("Set for player1!");
    }

    @Test
    public void player1_should_win_when_7_5() throws Exception {
        // When
        player1WinGames(7);
        player2WinGames(5);

        // Then
        assertThat(tennisSet.score()).isEqualTo("Set for player1!");
    }

    @Test
    public void player2_should_win_when_5_7() throws Exception {
        // When
        player1WinGames(5);
        player2WinGames(7);

        // Then
        assertThat(tennisSet.score()).isEqualTo("Set for player2!");
    }

    private void player1WinGames(int numberOfGamesWon) {
        for (int i = 0; i < numberOfGamesWon; i++) {
            player1WinGame();
        }
    }

    private void player2WinGames(int numberOfGamesWon) {
        for (int i = 0; i < numberOfGamesWon; i++) {
            player2WinGame();
        }
    }

    private void player1WinGame() {
        player1WinTimes(4);
    }

    private void player2WinGame() {
        player2WinTimes(4);
    }

    private void player1WinTimes(int times) {
        for (int i = 0; i < times; i++) {
            this.tennisSet.player1WonPoint();
        }
    }

    private void player2WinTimes(int times) {
        for (int i = 0; i < times; i++) {
            this.tennisSet.player2WonPoint();
        }
    }
}