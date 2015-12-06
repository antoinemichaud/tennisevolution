package tennis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TennisTest {

    private int player1Score;
    private int player2Score;
    private String expectedScore;
    private String expectedNumericScore;
    private final String expectedFrenchScore;
    private String expectedGermanScore;

    public TennisTest(int player1Score, int player2Score, String expectedScore, String expectedNumericScore, String expectedFrenchScore, String expectedGermanScore) {
        this.player1Score = player1Score;
        this.player2Score = player2Score;
        this.expectedScore = expectedScore;
        this.expectedNumericScore = expectedNumericScore;
        this.expectedFrenchScore = expectedFrenchScore;
        this.expectedGermanScore = expectedGermanScore;
    }

    @Parameters
    public static Collection<Object[]> getAllScores() {
        return Arrays.asList(new Object[][] {
                { 0, 0, "Love-All", "0-0" , "Zéro partout", "Null alles"},
                { 1, 1, "Fifteen-All", "15-15" , "Quinze partout", "Fünfzehn alles"},
                { 2, 2, "Thirty-All", "30-30", "Trente partout", "Dreißig alles"},
                { 3, 3, "Deuce", "40-40", "Egalité", "Gleichstand"},
                { 4, 4, "Deuce", "40-40", "Egalité", "Gleichstand"},

                { 1, 0, "Fifteen-Love", "15-0", "Quinze-Zéro", "Fünfzehn-Null"},
                { 0, 1, "Love-Fifteen", "0-15", "Zéro-Quinze", "Null-Fünfzehn"},
                { 2, 0, "Thirty-Love", "30-0", "Trente-Zéro", "Dreißig-Null"},
                { 0, 2, "Love-Thirty", "0-30", "Zéro-Trente", "Null-Dreißig"},
                { 3, 0, "Forty-Love", "40-0", "Quarante-Zéro", "Vierzig-Null"},
                { 0, 3, "Love-Forty", "0-40", "Zéro-Quarante", "Null-Vierzig"},
                { 4, 0, "Win for player1", "Game player1!", "Jeu player1", "Spiel player1"},
                { 0, 4, "Win for player2", "Game player2!", "Jeu player2", "Spiel player2"},

                { 2, 1, "Thirty-Fifteen", "30-15", "Trente-Quinze", "Dreißig-Fünfzehn"},
                { 1, 2, "Fifteen-Thirty", "15-30", "Quinze-Trente", "Fünfzehn-Dreißig"},
                { 3, 1, "Forty-Fifteen", "40-15", "Quarante-Quinze", "Vierzig-Fünfzehn"},
                { 1, 3, "Fifteen-Forty", "15-40", "Quinze-Quarante", "Fünfzehn-Vierzig"},
                { 4, 1, "Win for player1", "Game player1!", "Jeu player1", "Spiel player1"},
                { 1, 4, "Win for player2", "Game player2!", "Jeu player2", "Spiel player2"},

                { 4, 3, "Win for player1", "Game player1!", "Jeu player1", "Vorteil player1"},
                { 3, 4, "Win for player2", "Game player2!", "Jeu player2", "Vorteil player2"},
                { 5, 4, "Win for player1", "Game player1!", "Jeu player1", "Vorteil player1"},
                { 4, 5, "Win for player2", "Game player2!", "Jeu player2", "Vorteil player2"},
                { 15, 14, "Win for player1", "Game player1!", "Jeu player1", "Vorteil player1"},
                { 14, 15, "Win for player2", "Game player2!", "Jeu player2", "Vorteil player2"},

                { 6, 4, "Win for player1", "Game player1!", "Jeu player1", "Spiel player1"},
                { 4, 6, "Win for player2", "Game player2!", "Jeu player2", "Spiel player2"},
                { 16, 14, "Win for player1", "Game player1!", "Jeu player1", "Spiel player1"},
                { 14, 16, "Win for player2", "Game player2!", "Jeu player2", "Spiel player2"},
        });
    }

    public void checkAllScores(TennisService game) {
        assertEquals(this.expectedScore, game.displayScore("player1", this.player1Score, "player2", this.player2Score));
        assertEquals(this.expectedNumericScore, game.displayAlternativeScore("player1", this.player1Score, "player2", this.player2Score));
        assertEquals(this.expectedFrenchScore, game.displayFrenchScore("player1", this.player1Score, "player2", this.player2Score));
        assertEquals(this.expectedGermanScore, game.displayGermanScore("player1", this.player1Score, "player2", this.player2Score));
    }

    @Test
    public void checkAllScoresTennisGame1() {
        TennisService game = new TennisService();
        checkAllScores(game);
    }
}