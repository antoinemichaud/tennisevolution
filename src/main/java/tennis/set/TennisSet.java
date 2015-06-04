package tennis.set;

import tennis.game.EnglishGameDisplayer;
import tennis.game.TennisGameDisplayer;

public class TennisSet {

    public static final String PLAYER_1 = "player1";
    public static final String PLAYER_2 = "player2";

    TennisGameDisplayer tennisGameDisplayer = new EnglishGameDisplayer(PLAYER_1, PLAYER_2);

    public void player1WonPoint() {
        tennisGameDisplayer.wonPoint(PLAYER_1);
    }

    public void player2WonPoint() {
        tennisGameDisplayer.wonPoint(PLAYER_2);
    }

    public String score() {
        return "0-0 " + tennisGameDisplayer.getScore();
    }
}
