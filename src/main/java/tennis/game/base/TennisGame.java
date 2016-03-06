package tennis.game.base;

public interface TennisGame {
    TennisScore getScore();

    void incrementPlayer1Score();

    void incrementPlayer2Score();

    boolean isEndOfGame();
}
