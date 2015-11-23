package tennis.game.noavantage;

public class FrenchGameDisplayer extends TennisGameDisplayer {
    public FrenchGameDisplayer(String player1Name, String player2Name, TennisGame tennisGame) {
        super(player1Name, player2Name, tennisGame);
    }

    @Override
    protected String loveAll() {
        return "Zéro partout";
    }

    @Override
    protected String fifteenAll() {
        return "Quinze A";
    }

    @Override
    protected String thirtyAll() {
        return "Trente A";
    }

    @Override
    protected String deuce() {
        return "Egalité";
    }

    @Override
    protected String advantagePlayer1() {
        return "Avantage " + player1Name;
    }

    @Override
    protected String advantagePlayer2() {
        return "Avantage " + player2Name;
    }

    @Override
    protected String gameForPlayer1() {
        return "Jeu pour " + player1Name;
    }

    @Override
    protected String gameForPlayer2() {
        return "Jeu pour " + player2Name;
    }

    @Override
    protected String love() {
        return "Zéro";
    }

    @Override
    protected String fifteen() {
        return "Quinze";
    }

    @Override
    protected String thirty() {
        return "Trente";
    }

    @Override
    protected String forty() {
        return "Quarante";
    }
}
