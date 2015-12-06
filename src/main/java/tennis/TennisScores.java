package tennis;

import java.util.Objects;

public class TennisScores {

    public TennisScore player1;
    public TennisScore player2;

    public TennisScores(TennisScore player1, TennisScore player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public static TennisScores scores(TennisScore player1, TennisScore player2) {
        return new TennisScores(player1, player2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TennisScores that = (TennisScores) o;
        return player1 == that.player1 &&
                player2 == that.player2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(player1, player2);
    }
}
