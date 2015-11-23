package tennis.game.withlife;

public class EnglishGameDisplayer extends TennisGameDisplayer {

    public static final String LOVE_ALL = "Null-Null";
    public static final String FIFTEEN_ALL = "Fünfzehn-Beide";
    public static final String THIRTY_ALL = "Dreißig-Beide";
    public static final String DEUCE = "Gleichstand";
    public static final String ADVANTAGE = "Vorteil";
    public static final String WIN_FOR = "Sieg für ";
    public static final String LOVE = "Null";
    public static final String FIFTEEN = "Fünfzehn";
    public static final String THIRTY = "Dreißig";
    public static final String FORTY = "Fierzig";


    public EnglishGameDisplayer(String player1Name, String player2Name, TennisGame tennisGame) {
        super(player1Name, player2Name, tennisGame);
    }

    @Override
    protected String loveAll() {
        return LOVE_ALL;
    }

    @Override
    protected String fifteenAll() {
        return FIFTEEN_ALL;
    }

    @Override
    protected String thirtyAll() {
        return THIRTY_ALL;
    }

    @Override
    protected String deuce() {
        return DEUCE;
    }

    @Override
    protected String advantagePlayer1() {
        return ADVANTAGE + player1Name;
    }

    @Override
    protected String advantagePlayer2() {
        return ADVANTAGE + player2Name;
    }

    @Override
    protected String gameForPlayer1() {
        return WIN_FOR + player1Name;
    }

    @Override
    protected String gameForPlayer2() {
        return WIN_FOR + player2Name;
    }

    @Override
    protected String love() {
        return LOVE;
    }

    @Override
    protected String fifteen() {
        return FIFTEEN;
    }

    @Override
    protected String thirty() {
        return THIRTY;
    }

    @Override
    protected String forty() {
        return FORTY;
    }
}
