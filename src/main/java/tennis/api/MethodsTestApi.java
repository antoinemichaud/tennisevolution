package tennis.api;

import com.google.common.base.Splitter;
import net.codestory.http.WebServer;
import tennis.game.classic.TennisGameKataContainer;
import tennis.generator.TennisSetGenerator;
import tennis.history.Aggregator;
import tennis.history.HistoryKeeper;
import tennis.set.TennisSetKataContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

public class MethodsTestApi {

    public static void main(String[] args) {
        TennisGameKataContainer tennisGameKataContainer = new TennisGameKataContainer();
        tennis.game.services.TennisGameKataContainer tennisNoAvantageGameKataContainer = new tennis.game.services.TennisGameKataContainer();
        tennis.game.noavantage.TennisGameKataContainer tennisServiceGameKataContainer = new tennis.game.noavantage.TennisGameKataContainer();
        tennis.game.withlife.TennisGameKataContainer tennisWithLifeGameKataContainer = new tennis.game.withlife.TennisGameKataContainer();
        TennisSetKataContainer tennisSetKataContainer = new TennisSetKataContainer();

        new WebServer().configure(
                routes -> routes
                        .get("/displayScore", (context) -> tennisGameKataContainer
                                .displayScore(context.get("player1Name"),
                                        context.query().getInteger("player1Score"),
                                        context.get("player2Name"),
                                        context.query().getInteger("player2Score")))
                        .get("/displayAlternativeScore", (context) -> tennisGameKataContainer
                                .displayAlternativeScore(context.get("player1Name"),
                                        context.query().getInteger("player1Score"),
                                        context.get("player2Name"),
                                        context.query().getInteger("player2Score")))
                        .get("/displayFrenchScore", (context) -> tennisGameKataContainer
                                .displayFrenchScore(context.get("player1Name"),
                                        context.query().getInteger("player1Score"),
                                        context.get("player2Name"),
                                        context.query().getInteger("player2Score")))
                        .get("/noAvantageScoring", (context) -> tennisNoAvantageGameKataContainer
                                .displayScore(context.get("player1Name"),
                                        context.query().getInteger("player1Score"),
                                        context.get("player2Name"),
                                        context.query().getInteger("player2Score")))
                        .get("/servicesScoring", (context) -> tennisServiceGameKataContainer
                                .displayScore(context.get("player1Name"),
                                        context.query().getInteger("player1Score"),
                                        context.get("player2Name"),
                                        context.query().getInteger("player2Score")))
                        .get("/withLifeScoring", (context) -> tennisWithLifeGameKataContainer
                                .displayScore(context.get("player1Name"),
                                        context.query().getInteger("player1Score"),
                                        context.get("player2Name"),
                                        context.query().getInteger("player2Score")))
                        .get("/sets/displayScore", (context) -> {
                            List<Integer> scores = Splitter.on(",").splitToList(context.get("scores")).stream().map(Integer::parseInt).collect(toList());;
                            return tennisSetKataContainer.displayScore(scores);
                        })
        ).start(8080);

        TennisSetGenerator tennisSetGenerator = new TennisSetGenerator(new Random());
        Random gameIsFinished = new Random();

        new WebServer().configure(
                routes -> routes
                        .get("/generateTest/generateGame", context -> {
                                    List<GameQuestion> gameQuestions = new ArrayList<>();
                                    for (int i = 0; i < 10; i++) {
                                        HistoryKeeper historyKeeper = tennisSetGenerator.generate(tennisGame -> gameIsFinished.nextInt(5) == 0);
                                        Aggregator aggregator = new Aggregator("player1", "player2");
                                        gameQuestions.add(aggregator.aggregateToGameScore(historyKeeper));
                                    }
                                    return gameQuestions;
                                }
                        ).get("/generateTest/generateSet", context -> {
                                    List<List<Integer>> gameQuestions = new ArrayList<>();
                                    for (int i = 0; i < 10; i++) {
                                        gameQuestions.add(new Random().ints(new Random().nextInt((90 - 30) + 1) + 30, 1, 3).boxed().collect(toList()));
                                    }
                                    return gameQuestions;
                                }
                        )
        ).start(8081);
    }
}
