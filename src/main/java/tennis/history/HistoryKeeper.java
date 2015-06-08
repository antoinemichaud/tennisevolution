package tennis.history;

import tennis.game.TennisGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class HistoryKeeper implements Observer {

    private ArrayList<Integer> points = new ArrayList<Integer>();

    public HistoryKeeper(TennisGame tennisGame) {
        tennisGame.addObserver(this);
    }

    public List<Integer> list() {
        return points;
    }

    public void player1win() {
        points.add(0);
    }

    public void player2win() {
        points.add(1);
    }

    public void update(Observable o, Object arg) {
        if (arg instanceof Integer) {
            Integer player = (Integer) arg;
            points.add(player);
        }
    }
}
