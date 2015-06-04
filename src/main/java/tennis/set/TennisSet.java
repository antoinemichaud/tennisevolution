package tennis.set;

import tennis.game.EnglishGameDisplayer;
import tennis.game.TennisGameDisplayer;

public class TennisSet {

    TennisGameDisplayer tennisGameDisplayer = new EnglishGameDisplayer("player1", "player2");

    public void player1WonPoint() {
        tennisGameDisplayer.wonPoint("player1");
    }

    public void player2WonPoint() {

    }

    public String score() {
        return "0-0 " + tennisGameDisplayer.getScore();
    }
}
