package tennis.set;

import java.util.ArrayList;
import java.util.List;

public class GamesScores {
    private final List<Integer> player1Scores = new ArrayList<Integer>();
    private final List<Integer> player2Scores = new ArrayList<Integer>();

    private GamesScores(int player1Score, int player2Score) {
        this.player1Scores.add(player1Score);
        this.player2Scores.add(player2Score);
    }

    private GamesScores(List<Integer> player1Scores, List<Integer> player2Scores, int player1Score, int player2Score) {
        this.player1Scores.addAll(player1Scores);
        this.player2Scores.addAll(player2Scores);
        this.player1Scores.add(player1Score);
        this.player2Scores.add(player2Score);
    }

    public static GamesScores with(int player1Score, int player2Score) {
        return new GamesScores(player1Score, player2Score);
    }

    public GamesScores then(int player1Score, int player2Score) {
        return new GamesScores(this.player1Scores, this.player2Scores, player1Score, player2Score);
    }

    public List<Integer> getPlayer1Scores() {
        return new ArrayList<Integer>(player1Scores);
    }

    public List<Integer> getPlayer2Scores() {
        return new ArrayList<Integer>(player2Scores);
    }
}
