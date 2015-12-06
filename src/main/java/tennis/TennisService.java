package tennis;

public class TennisService {

    public String displayScore(String player1Name, int player1Score, String player2Name, int player2Score) {
        return new TennisEnglishDisplayer().displayScore(player1Name, player1Score, player2Name, player2Score);
    }

    public String displayAlternativeScore(String player1Name, int player1Score, String player2Name, int player2Score) {
        return new TennisNumericDisplayer().displayScore(player1Name, player1Score, player2Name, player2Score);
    }

    public String displayFrenchScore(String player1Name, int player1Score, String player2Name, int player2Score) {
        return new TennisFrenchDisplayer().displayScore(player1Name, player1Score, player2Name, player2Score);
    }

    public String displayGermanScore(String player1Name, int player1Score, String player2Name, int player2Score) {
        return new TennisGermanDisplayer().displayScore(player1Name, player1Score, player2Name, player2Score);
    }
}
