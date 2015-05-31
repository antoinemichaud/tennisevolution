package tennis;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TennisGameTest {

    @Test
    public void should_return_regular_score() throws Exception {
        TennisGameDisplayer tennisGameDisplayer = new TennisGameDisplayer("player1", "player2");

        TennisScore tennisScore = tennisGameDisplayer.getScoreAsBusiness();

        assertThat(tennisScore).isInstanceOf(RegularScore.class);
        assertThat(((RegularScore)tennisScore).firstPlayerScore()).isEqualTo(PlayerScore.LOVE);
        assertThat(((RegularScore)tennisScore).secondPlayerScore()).isEqualTo(PlayerScore.LOVE);
    }

    @Test
    public void should_return_regular_score_with_forty_for_player_1() throws Exception {
        String player1 = "player1";
        String player2 = "player2";
        TennisGameDisplayer tennisGameDisplayer = new TennisGameDisplayer(player1, player2);
        TennisGameTestUtils.initScores(tennisGameDisplayer, player1, 3, player2, 0);

        TennisScore tennisScore = tennisGameDisplayer.getScoreAsBusiness();

        assertThat(tennisScore).isInstanceOf(RegularScore.class);
        assertThat(((RegularScore)tennisScore).firstPlayerScore()).isEqualTo(PlayerScore.FORTY);
        assertThat(((RegularScore)tennisScore).secondPlayerScore()).isEqualTo(PlayerScore.LOVE);
    }

    @Test
    public void should_return_regular_score_with_forty_for_player_2() throws Exception {
        String player1 = "player1";
        String player2 = "player2";
        TennisGameDisplayer tennisGameDisplayer = new TennisGameDisplayer(player1, player2);
        TennisGameTestUtils.initScores(tennisGameDisplayer, player1, 0, player2, 3);

        TennisScore tennisScore = tennisGameDisplayer.getScoreAsBusiness();

        assertThat(tennisScore).isInstanceOf(RegularScore.class);
        assertThat(((RegularScore)tennisScore).firstPlayerScore()).isEqualTo(PlayerScore.LOVE);
        assertThat(((RegularScore)tennisScore).secondPlayerScore()).isEqualTo(PlayerScore.FORTY);
    }

    @Test
    public void should_return_regular_score_with_thirty_fifteen() throws Exception {
        String player1 = "player1";
        String player2 = "player2";
        TennisGameDisplayer tennisGameDisplayer = new TennisGameDisplayer(player1, player2);
        TennisGameTestUtils.initScores(tennisGameDisplayer, player1, 2, player2, 1);

        TennisScore tennisScore = tennisGameDisplayer.getScoreAsBusiness();

        assertThat(tennisScore).isInstanceOf(RegularScore.class);
        assertThat(((RegularScore)tennisScore).firstPlayerScore()).isEqualTo(PlayerScore.THIRTY);
        assertThat(((RegularScore)tennisScore).secondPlayerScore()).isEqualTo(PlayerScore.FIFTEEN);
    }

}