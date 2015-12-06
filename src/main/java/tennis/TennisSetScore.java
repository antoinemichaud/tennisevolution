package tennis;

import java.util.Objects;

public class TennisSetScore {
    public final int player1WonGames;
    public final int player2WonGames;
    public final TennisScore player1GameScore;
    public final TennisScore player2GameScore;

    public TennisSetScore(int player1WonGames, int player2WonGames, TennisScore player1GameScore, TennisScore player2GameScore) {
        this.player1WonGames = player1WonGames;
        this.player2WonGames = player2WonGames;
        this.player1GameScore = player1GameScore;
        this.player2GameScore = player2GameScore;
    }

    @Override
    public String toString() {
        return "TennisSetScore{" +
                "player1WonGames=" + player1WonGames +
                ", player2WonGames=" + player2WonGames +
                ", player1GameScore=" + player1GameScore +
                ", player2GameScore=" + player2GameScore +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TennisSetScore that = (TennisSetScore) o;
        return player1WonGames == that.player1WonGames &&
                player2WonGames == that.player2WonGames &&
                player1GameScore == that.player1GameScore &&
                player2GameScore == that.player2GameScore;
    }

    @Override
    public int hashCode() {
        return Objects.hash(player1WonGames, player2WonGames, player1GameScore, player2GameScore);
    }
}
