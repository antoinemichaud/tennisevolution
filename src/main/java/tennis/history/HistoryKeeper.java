package tennis.history;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class HistoryKeeper implements Observer {

    private List<WhichPlayer> points = new ArrayList<>();

    public HistoryKeeper(Observable tennis) {
        tennis.addObserver(this);
    }

    public List<WhichPlayer> list() {
        return points;
    }

    public void update(Observable o, Object arg) {
        if (arg instanceof WhichPlayer) {
            points.add((WhichPlayer) arg);
        }
    }
}
