package tennis.game.noavantage;

import org.junit.Test;
import tennis.game.base.PlayerScore;
import tennis.game.base.TennisGameUtils;
import tennis.game.base.TennisScore;

import static org.assertj.core.api.Assertions.assertThat;

public class TennisGameTest {

    @Test
    public void should_return_regular_score() throws Exception {
        TennisGameNoAdvantage tennisGame = new TennisGameNoAdvantage();
        TennisGameUtils.initScores(tennisGame, 0, 0);
        TennisScore tennisScore = tennisGame.getScore();

        assertThat(tennisScore.firstPlayerScore()).isEqualTo(PlayerScore.LOVE);
        assertThat(tennisScore.secondPlayerScore()).isEqualTo(PlayerScore.LOVE);
    }

    @Test
    public void should_return_regular_score_with_forty_for_player_1() throws Exception {
        TennisGameNoAdvantage tennisGame = new TennisGameNoAdvantage();
        TennisGameUtils.initScores(tennisGame, 3, 0);
        TennisScore tennisScore = tennisGame.getScore();

        assertThat(tennisScore.firstPlayerScore()).isEqualTo(PlayerScore.FORTY);
        assertThat(tennisScore.secondPlayerScore()).isEqualTo(PlayerScore.LOVE);
    }

    @Test
    public void should_return_regular_score_with_forty_for_player_2() throws Exception {
        TennisGameNoAdvantage tennisGame = new TennisGameNoAdvantage();
        TennisGameUtils.initScores(tennisGame, 0, 3);
        TennisScore tennisScore = tennisGame.getScore();

        assertThat(tennisScore.firstPlayerScore()).isEqualTo(PlayerScore.LOVE);
        assertThat(tennisScore.secondPlayerScore()).isEqualTo(PlayerScore.FORTY);
    }

    @Test
    public void should_return_regular_score_with_thirty_fifteen() throws Exception {
        TennisGameNoAdvantage tennisGame = new TennisGameNoAdvantage();
        TennisGameUtils.initScores(tennisGame, 2, 1);
        TennisScore tennisScore = tennisGame.getScore();

        assertThat(tennisScore.firstPlayerScore()).isEqualTo(PlayerScore.THIRTY);
        assertThat(tennisScore.secondPlayerScore()).isEqualTo(PlayerScore.FIFTEEN);
    }

    @Test
    public void should_player1_win_the_game() throws Exception {
        TennisGameNoAdvantage tennisGame = new TennisGameNoAdvantage();
        TennisGameUtils.initScores(tennisGame, 4, 3);
        TennisScore tennisScore = tennisGame.getScore();

        assertThat(tennisScore.firstPlayerScore()).isEqualTo(PlayerScore.GAME);
    }

    @Test
    public void should_player2_win_the_game() throws Exception {
        TennisGameNoAdvantage tennisGame = new TennisGameNoAdvantage();
        TennisGameUtils.initScores(tennisGame, 3, 4);
        TennisScore tennisScore = tennisGame.getScore();

        assertThat(tennisScore.secondPlayerScore()).isEqualTo(PlayerScore.GAME);
    }

    @Test
    public void should_give_player1_for_perfect_game() throws Exception {
        TennisGameNoAdvantage tennisGame = new TennisGameNoAdvantage();
        TennisGameUtils.initScores(tennisGame, 4, 0);
        TennisScore tennisScore = tennisGame.getScore();

        assertThat(tennisScore.firstPlayerScore()).isEqualTo(PlayerScore.GAME);
        assertThat(tennisScore.secondPlayerScore()).isEqualTo(PlayerScore.LOVE);
    }

}