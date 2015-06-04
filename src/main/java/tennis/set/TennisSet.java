package tennis.set;

import tennis.game.EnglishGameDisplayer;
import tennis.game.TennisGameDisplayer;

public class TennisSet {

    public static final String PLAYER_1 = "player1";
    public static final String PLAYER_2 = "player2";

    TennisGameDisplayer tennisGameDisplayer = new EnglishGameDisplayer(PLAYER_1, PLAYER_2);
    private int player1wonGames;
    private int player2wonGames;

    public TennisSet() {
        player1wonGames = 0;
        player2wonGames = 0;
    }

    public void player1WonPoint() {
        tennisGameDisplayer.wonPoint(PLAYER_1);
        if (tennisGameDisplayer.getScore().equals("Win for player1")) {
            tennisGameDisplayer = new EnglishGameDisplayer(PLAYER_1, PLAYER_2);
            player1wonGames++;
        }
    }

    public void player2WonPoint() {
        tennisGameDisplayer.wonPoint(PLAYER_2);
    }

    public String score() {
        return player1wonGames + "-" + player2wonGames + " " + tennisGameDisplayer.getScore();
    }
}
