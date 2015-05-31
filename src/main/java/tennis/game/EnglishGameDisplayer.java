package tennis.game;

public class EnglishGameDisplayer extends TennisGameDisplayer {

    public static final String LOVE_ALL = "Love-All";
    public static final String FIFTEEN_ALL = "Fifteen-All";
    public static final String THIRTY_ALL = "Thirty-All";
    public static final String DEUCE = "Deuce";
    public static final String ADVANTAGE = "Advantage ";
    public static final String WIN_FOR = "Win for ";
    public static final String LOVE = "Love";
    public static final String FIFTEEN = "Fifteen";
    public static final String THIRTY = "Thirty";
    public static final String FORTY = "Forty";


    public EnglishGameDisplayer(String player1Name, String player2Name) {
        super(player1Name, player2Name);
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
