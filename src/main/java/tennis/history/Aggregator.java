package tennis.history;

import tennis.api.GameQuestion;

public class Aggregator {

    private GameQuestion gameQuestion;

    public Aggregator(String player1Name, String player2Name) {
        gameQuestion = new GameQuestion(player1Name, player2Name);
    }

    public GameQuestion aggregateToGameScore(HistoryKeeper historyKeeper) {
        for (WhichPlayer whichPlayer : historyKeeper.list()) {
            switch (whichPlayer) {
                case PLAYER_ONE:
                    gameQuestion.incrementPlayer1();
                    break;
                case PLAYER_TWO:
                    gameQuestion.incrementPlayer2();
            }
        }
        return gameQuestion;
    }

}
