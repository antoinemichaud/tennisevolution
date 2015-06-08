package tennis.history;

import tennis.game.TennisGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class HistoryGameKeeper implements Observer {

    private List<Integer> points = new ArrayList<>();

    public HistoryGameKeeper(TennisGame tennisGame) {
        tennisGame.addObserver(this);
    }

    public List<Integer> list() {
        return points;
    }

    public void update(Observable o, Object arg) {
        if (arg instanceof Integer) {
            Integer player = (Integer) arg;
            points.add(player);
        }
    }
}
