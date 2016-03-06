package tennis.display;

import tennis.game.base.TennisGame;

public class FiguresGameDisplayer extends TennisGameDisplayer {

    public static final String LOVE_ALL = "0-0";
    public static final String FIFTEEN_ALL = "15-15";
    public static final String THIRTY_ALL = "30-30";
    public static final String DEUCE = "40-40";
    public static final String WIN_FOR = "Game ";
    public static final String LOVE = "0";
    public static final String FIFTEEN = "15";
    public static final String THIRTY = "30";
    public static final String FORTY = "40";

    public FiguresGameDisplayer(String player1Name, String player2Name, TennisGame tennisGame) {
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
        return "A-40";
    }

    @Override
    protected String advantagePlayer2() {
        return "40-A";
    }

    @Override
    protected String gameForPlayer1() {
        return WIN_FOR + player1Name + "!";
    }

    @Override
    protected String gameForPlayer2() {
        return WIN_FOR + player2Name + "!";
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
