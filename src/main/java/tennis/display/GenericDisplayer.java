package tennis.display;

import tennis.game.base.TennisGame;

public class GenericDisplayer extends TennisGameDisplayer {

    public static final String LOVE_ALL = "%s-%s";
    public static final String FIFTEEN_ALL = "%s-%s";
    public static final String THIRTY_ALL = "%s-%s";
    public static final String DEUCE = "%s";
    public static final String ADVANTAGE = "%s ";
    public static final String WIN_FOR = "%s ";
    public static final String LOVE = "%s";
    public static final String FIFTEEN = "%s";
    public static final String THIRTY = "%s";
    public static final String FORTY = "%s";

    private ScoreTranslator scoreTranslator = new ScoreTranslator();
    private String language;

    public GenericDisplayer(String language, String player1Name, String player2Name, TennisGame tennisGame) {
        super(player1Name, player2Name, tennisGame);
        this.language = language;
    }

    @Override
    protected String loveAll() {
        return String.format(LOVE_ALL,
                scoreTranslator.translate(ScoreLabelBase.LOVE, language),
                scoreTranslator.translate(ScoreLabelBase.ALL, language));
    }

    @Override
    protected String fifteenAll() {
        return String.format(FIFTEEN_ALL,
                scoreTranslator.translate(ScoreLabelBase.FIFTEEN, language),
                scoreTranslator.translate(ScoreLabelBase.ALL, language));
    }

    @Override
    protected String thirtyAll() {
        return String.format(THIRTY_ALL,
                scoreTranslator.translate(ScoreLabelBase.THIRTY, language),
                scoreTranslator.translate(ScoreLabelBase.ALL, language));
    }

    @Override
    protected String deuce() {
        return String.format(DEUCE,
                scoreTranslator.translate(ScoreLabelBase.DEUCE, language));

    }

    @Override
    protected String advantagePlayer1() {
        return String.format(ADVANTAGE, scoreTranslator.translate(ScoreLabelBase.ADVANTAGE, language)) + player1Name;
    }

    @Override
    protected String advantagePlayer2() {
        return String.format(ADVANTAGE, scoreTranslator.translate(ScoreLabelBase.ADVANTAGE, language)) + player2Name;
    }

    @Override
    protected String gameForPlayer1() {
        return String.format(WIN_FOR, scoreTranslator.translate(ScoreLabelBase.WIN_FOR, language)) + player1Name;
    }

    @Override
    protected String gameForPlayer2() {
        return String.format(WIN_FOR, scoreTranslator.translate(ScoreLabelBase.WIN_FOR, language)) + player2Name;
    }

    @Override
    protected String love() {
        return scoreTranslator.translate(ScoreLabelBase.LOVE, language);
    }

    @Override
    protected String fifteen() {
        return scoreTranslator.translate(ScoreLabelBase.FIFTEEN, language);
    }

    @Override
    protected String thirty() {
        return scoreTranslator.translate(ScoreLabelBase.THIRTY, language);
    }

    @Override
    protected String forty() {
        return scoreTranslator.translate(ScoreLabelBase.FOURTY, language);
    }

}
