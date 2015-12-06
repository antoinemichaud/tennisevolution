package tennis;

import static tennis.TennisScore.GAME;
import static tennis.TennisSetState.*;

public class TennisSet {

    private TennisGame tennisGame;
    private int player1WonGames = 0;
    private int player2WonGames = 0;

    public TennisSet() {
        tennisGame = new TennisGame();
    }

    public TennisSetScore score() {
        return new TennisSetScore(player1WonGames, player2WonGames, tennisGame.score().player1, tennisGame.score().player2);
    }

    public void player1Win() {
        tennisGame.player1WinPoint();
        if (tennisGame.score().player1 == GAME) {
            player1WonGames++;
            tennisGame = new TennisGame();
        }
    }

    public void player2Win() {
        tennisGame.player2WinPoint();
        if (tennisGame.score().player2 == GAME) {
            player2WonGames++;
            tennisGame = new TennisGame();
        }
    }

    public TennisSetState state() {
        if (player1WonGames < 6 && player2WonGames < 6)
            return NO_ONE_WIN;
        if (player1WonGames > player2WonGames)
            return PLAYER_1_WON;
        return PLAYER_2_WON;
    }

    public TennisGame getTennisGame() {
        return tennisGame;
    }
}
