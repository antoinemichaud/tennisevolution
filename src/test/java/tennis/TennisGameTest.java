package tennis;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TennisGameTest {

    @Test
    public void should_return_regular_score() throws Exception {
        TennisGame tennisGame = new TennisGame();
        TennisScore tennisScore = setScoreAt(tennisGame, 0, 0);

        assertThat(tennisScore).isInstanceOf(RegularScore.class);
        assertThat(((RegularScore) tennisScore).firstPlayerScore()).isEqualTo(PlayerScore.LOVE);
        assertThat(((RegularScore) tennisScore).secondPlayerScore()).isEqualTo(PlayerScore.LOVE);
    }

    @Test
    public void should_return_regular_score_with_forty_for_player_1() throws Exception {
        TennisGame tennisGame = new TennisGame();
        TennisScore tennisScore = setScoreAt(tennisGame, 3, 0);

        assertThat(tennisScore).isInstanceOf(RegularScore.class);
        assertThat(((RegularScore) tennisScore).firstPlayerScore()).isEqualTo(PlayerScore.FORTY);
        assertThat(((RegularScore) tennisScore).secondPlayerScore()).isEqualTo(PlayerScore.LOVE);
    }

    @Test
    public void should_return_regular_score_with_forty_for_player_2() throws Exception {
        TennisGame tennisGame = new TennisGame();
        TennisScore tennisScore = setScoreAt(tennisGame, 0, 3);

        assertThat(tennisScore).isInstanceOf(RegularScore.class);
        assertThat(((RegularScore) tennisScore).firstPlayerScore()).isEqualTo(PlayerScore.LOVE);
        assertThat(((RegularScore) tennisScore).secondPlayerScore()).isEqualTo(PlayerScore.FORTY);
    }

    @Test
    public void should_return_regular_score_with_thirty_fifteen() throws Exception {
        TennisGame tennisGame = new TennisGame();
        TennisScore tennisScore = setScoreAt(tennisGame, 2, 1);

        assertThat(tennisScore).isInstanceOf(RegularScore.class);
        assertThat(((RegularScore) tennisScore).firstPlayerScore()).isEqualTo(PlayerScore.THIRTY);
        assertThat(((RegularScore) tennisScore).secondPlayerScore()).isEqualTo(PlayerScore.FIFTEEN);
    }

    private static TennisScore setScoreAt(TennisGame tennisGame, int player1Score, int player2Score) {
        TennisGameTestUtils.initScores(tennisGame, player1Score, player2Score);
        return tennisGame.getScore();
    }

}