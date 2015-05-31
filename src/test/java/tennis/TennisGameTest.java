package tennis;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TennisGameTest {

    @Test
    public void should_return_regular_score() throws Exception {
        TennisGame tennisGame = new TennisGame();
        TennisGameTestUtils.initScores(tennisGame, 0, 0);
        TennisScore tennisScore = tennisGame.getScore();

        assertThat(tennisScore).isInstanceOf(RegularScore.class);
        assertThat(((RegularScore) tennisScore).firstPlayerScore()).isEqualTo(PlayerScore.LOVE);
        assertThat(((RegularScore) tennisScore).secondPlayerScore()).isEqualTo(PlayerScore.LOVE);
    }

    @Test
    public void should_return_regular_score_with_forty_for_player_1() throws Exception {
        TennisGame tennisGame = new TennisGame();
        TennisGameTestUtils.initScores(tennisGame, 3, 0);
        TennisScore tennisScore = tennisGame.getScore();

        assertThat(tennisScore).isInstanceOf(RegularScore.class);
        assertThat(((RegularScore) tennisScore).firstPlayerScore()).isEqualTo(PlayerScore.FORTY);
        assertThat(((RegularScore) tennisScore).secondPlayerScore()).isEqualTo(PlayerScore.LOVE);
    }

    @Test
    public void should_return_regular_score_with_forty_for_player_2() throws Exception {
        TennisGame tennisGame = new TennisGame();
        TennisGameTestUtils.initScores(tennisGame, 0, 3);
        TennisScore tennisScore = tennisGame.getScore();

        assertThat(tennisScore).isInstanceOf(RegularScore.class);
        assertThat(((RegularScore) tennisScore).firstPlayerScore()).isEqualTo(PlayerScore.LOVE);
        assertThat(((RegularScore) tennisScore).secondPlayerScore()).isEqualTo(PlayerScore.FORTY);
    }

    @Test
    public void should_return_regular_score_with_thirty_fifteen() throws Exception {
        TennisGame tennisGame = new TennisGame();
        TennisGameTestUtils.initScores(tennisGame, 2, 1);
        TennisScore tennisScore = tennisGame.getScore();

        assertThat(tennisScore).isInstanceOf(RegularScore.class);
        assertThat(((RegularScore) tennisScore).firstPlayerScore()).isEqualTo(PlayerScore.THIRTY);
        assertThat(((RegularScore) tennisScore).secondPlayerScore()).isEqualTo(PlayerScore.FIFTEEN);
    }

}