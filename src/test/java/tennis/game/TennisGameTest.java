package tennis.game;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TennisGameTest {

    @Test
    public void should_return_regular_score() throws Exception {
        TennisGame tennisGame = new TennisGame();
        TennisGameTestUtils.initScores(tennisGame, 0, 0);
        TennisScore tennisScore = tennisGame.getScore();

        assertThat(tennisScore.firstPlayerScore()).isEqualTo(PlayerScore.LOVE);
        assertThat(tennisScore.secondPlayerScore()).isEqualTo(PlayerScore.LOVE);
    }

    @Test
    public void should_return_regular_score_with_forty_for_player_1() throws Exception {
        TennisGame tennisGame = new TennisGame();
        TennisGameTestUtils.initScores(tennisGame, 3, 0);
        TennisScore tennisScore = tennisGame.getScore();

        assertThat(tennisScore.firstPlayerScore()).isEqualTo(PlayerScore.FORTY);
        assertThat(tennisScore.secondPlayerScore()).isEqualTo(PlayerScore.LOVE);
    }

    @Test
    public void should_return_regular_score_with_forty_for_player_2() throws Exception {
        TennisGame tennisGame = new TennisGame();
        TennisGameTestUtils.initScores(tennisGame, 0, 3);
        TennisScore tennisScore = tennisGame.getScore();

        assertThat(tennisScore.firstPlayerScore()).isEqualTo(PlayerScore.LOVE);
        assertThat(tennisScore.secondPlayerScore()).isEqualTo(PlayerScore.FORTY);
    }

    @Test
    public void should_return_regular_score_with_thirty_fifteen() throws Exception {
        TennisGame tennisGame = new TennisGame();
        TennisGameTestUtils.initScores(tennisGame, 2, 1);
        TennisScore tennisScore = tennisGame.getScore();

        assertThat(tennisScore.firstPlayerScore()).isEqualTo(PlayerScore.THIRTY);
        assertThat(tennisScore.secondPlayerScore()).isEqualTo(PlayerScore.FIFTEEN);
    }

    @Test
    public void should_give_player1_for_advantage() throws Exception {
        TennisGame tennisGame = new TennisGame();
        TennisGameTestUtils.initScores(tennisGame, 4, 3);
        TennisScore tennisScore = tennisGame.getScore();

        assertThat(tennisScore.firstPlayerScore()).isEqualTo(PlayerScore.ADVANTAGE);
        assertThat(tennisScore.secondPlayerScore()).isEqualTo(PlayerScore.FORTY);
    }

    @Test
    public void should_give_player2_for_advantage() throws Exception {
        TennisGame tennisGame = new TennisGame();
        TennisGameTestUtils.initScores(tennisGame, 3, 4);
        TennisScore tennisScore = tennisGame.getScore();

        assertThat(tennisScore.firstPlayerScore()).isEqualTo(PlayerScore.FORTY);
        assertThat(tennisScore.secondPlayerScore()).isEqualTo(PlayerScore.ADVANTAGE);
    }

    @Test
    public void should_give_player1_game() throws Exception {
        TennisGame tennisGame = new TennisGame();
        TennisGameTestUtils.initScores(tennisGame, 5, 3);
        TennisScore tennisScore = tennisGame.getScore();

        assertThat(tennisScore.firstPlayerScore()).isEqualTo(PlayerScore.GAME);
        assertThat(tennisScore.secondPlayerScore()).isEqualTo(PlayerScore.FORTY);
    }

    @Test
    public void should_give_player2_game() throws Exception {
        TennisGame tennisGame = new TennisGame();
        TennisGameTestUtils.initScores(tennisGame, 3, 5);
        TennisScore tennisScore = tennisGame.getScore();

        assertThat(tennisScore.firstPlayerScore()).isEqualTo(PlayerScore.FORTY);
        assertThat(tennisScore.secondPlayerScore()).isEqualTo(PlayerScore.GAME);
    }

    @Test
    public void should_give_player1_for_perfect_game() throws Exception {
        TennisGame tennisGame = new TennisGame();
        TennisGameTestUtils.initScores(tennisGame, 4, 0);
        TennisScore tennisScore = tennisGame.getScore();

        assertThat(tennisScore.firstPlayerScore()).isEqualTo(PlayerScore.GAME);
        assertThat(tennisScore.secondPlayerScore()).isEqualTo(PlayerScore.LOVE);
    }

}