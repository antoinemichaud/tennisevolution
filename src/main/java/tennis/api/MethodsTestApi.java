package tennis.api;

import com.google.common.base.Splitter;
import net.codestory.http.WebServer;
import tennis.game.classic.TennisGameClassic;
import tennis.game.classic.TennisGameKataContainer;
import tennis.generator.TennisSetGenerator;
import tennis.history.Aggregator;
import tennis.history.HistoryKeeper;
import tennis.set.TennisSetKataContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public class MethodsTestApi {

    public static void main(String[] args) {
        TennisGameKataContainer classicTennisGameKataContainer = new TennisGameKataContainer();
        tennis.game.noavantage.TennisGameKataContainer tennisNoAvantageGameKataContainer = new tennis.game.noavantage.TennisGameKataContainer();
        tennis.game.withlife.TennisGameKataContainer tennisWithLifeGameKataContainer = new tennis.game.withlife.TennisGameKataContainer();
        TennisSetKataContainer tennisSetKataContainer = new TennisSetKataContainer();
        tennis.set.services.TennisSetKataContainer tennisServiceGameKataContainer = new tennis.set.services.TennisSetKataContainer();

        new WebServer().configure(
                routes -> routes
                        .get("/classic/displayScore", (context) -> classicTennisGameKataContainer
                                .displayScore(context.get("player1Name"),
                                        context.query().getInteger("player1Score"),
                                        context.get("player2Name"),
                                        context.query().getInteger("player2Score")))
                        .get("/classic/displayScore/:language", (context, language) ->
                                classicTennisGameKataContainer
                                .displayScore(language, context.get("player1Name"),
                                        context.query().getInteger("player1Score"),
                                        context.get("player2Name"),
                                        context.query().getInteger("player2Score")
                                )
                        )


                        .get("/withoutAdvantage/displayScore", (context) -> tennisNoAvantageGameKataContainer
                                .displayScore(context.get("player1Name"),
                                        context.query().getInteger("player1Score"),
                                        context.get("player2Name"),
                                        context.query().getInteger("player2Score")))
                        .get("/withoutAdvantage/displayScore/alternative", (context) -> tennisNoAvantageGameKataContainer
                                .displayAlternativeScore(context.get("player1Name"),
                                        context.query().getInteger("player1Score"),
                                        context.get("player2Name"),
                                        context.query().getInteger("player2Score")))
                        .get("/withoutAdvantage/displayScore/french", (context) -> tennisNoAvantageGameKataContainer
                                .displayFrenchScore(context.get("player1Name"),
                                        context.query().getInteger("player1Score"),
                                        context.get("player2Name"),
                                        context.query().getInteger("player2Score")))
                        .get("/withoutAdvantage/displayScore/german", (context) -> tennisNoAvantageGameKataContainer
                                .displayGermanScore(context.get("player1Name"),
                                        context.query().getInteger("player1Score"),
                                        context.get("player2Name"),
                                        context.query().getInteger("player2Score")))


                        .get("/withLifeScoring/displayScore", (context) -> tennisWithLifeGameKataContainer
                                .displayScore(context.get("player1Name"),
                                        context.query().getInteger("player1Score"),
                                        context.get("player2Name"),
                                        context.query().getInteger("player2Score")))
                        .get("/withLifeScoring/displayScore/alternative", (context) -> tennisWithLifeGameKataContainer
                                .displayAlternativeScore(context.get("player1Name"),
                                        context.query().getInteger("player1Score"),
                                        context.get("player2Name"),
                                        context.query().getInteger("player2Score")))
                        .get("/withLifeScoring/displayScore/french", (context) -> tennisWithLifeGameKataContainer
                                .displayFrenchScore(context.get("player1Name"),
                                        context.query().getInteger("player1Score"),
                                        context.get("player2Name"),
                                        context.query().getInteger("player2Score")))
                        .get("/withLifeScoring/displayScore/german", (context) -> tennisWithLifeGameKataContainer
                                .displayGermanScore(context.get("player1Name"),
                                        context.query().getInteger("player1Score"),
                                        context.get("player2Name"),
                                        context.query().getInteger("player2Score")))


                        .get("/sets/displayScore", (context) -> {
                            List<Integer> scores = Splitter.on(",").splitToList(context.get("scores")).stream().map(Integer::parseInt).collect(toList());
                            return tennisSetKataContainer.displayScore(scores);
                        })
                        .get("/sets/servicesScoring", (context) -> {
                            List<Integer> scores = Splitter.on(",").splitToList(context.get("scores")).stream().map(Integer::parseInt).collect(toList());
                            return tennisServiceGameKataContainer.displayScore(scores);
                        })
//        ).start(8080);
        ).start(8083);

        TennisSetGenerator tennisSetGenerator = new TennisSetGenerator(new Random());
        Random gameIsFinished = new Random();

        new WebServer().configure(
                routes -> routes
                        .get("/generateTest/generateGame", context -> {
                            Predicate<TennisGameClassic> endCondition = tennisGame -> gameIsFinished.nextInt(5) == 0;
                                    List<GameQuestion> gameQuestions = new ArrayList<>();
                                    for (int i = 0; i < 10; i++) {
                                        HistoryKeeper historyKeeper = tennisSetGenerator.generate(endCondition);
                                        Aggregator aggregator = new Aggregator("player1", "player2");
                                        gameQuestions.add(aggregator.aggregateToGameScore(historyKeeper));
                                    }
                                    return gameQuestions;
                                }
                        ).get("/generateTest/generateNoAvantageGame", context -> {
                            Predicate<TennisGameClassic> endCondition = tennisGame -> gameIsFinished.nextInt(4) == 0;
                                    List<GameQuestion> gameQuestions = new ArrayList<>();
                                    for (int i = 0; i < 10; i++) {
                                        HistoryKeeper historyKeeper = tennisSetGenerator.generateNoAdvantage(endCondition);
                                        Aggregator aggregator = new Aggregator("player1", "player2");
                                        gameQuestions.add(aggregator.aggregateToGameScore(historyKeeper));
                                    }
                                    return gameQuestions;
                                }
                        )
                        .get("/generateTest/generateSet", context -> {
                                    List<List<Integer>> gameQuestions = new ArrayList<>();
                                    for (int i = 0; i < 10; i++) {
                                        gameQuestions.add(new Random().ints(new Random().nextInt((90 - 30) + 1) + 30, 1, 3).boxed().collect(toList()));
                                    }
                                    return gameQuestions;
                                }
                        )
                        .get("/generateTest/generateServicesSet", context -> {
                                    List<List<Integer>> gameQuestions = new ArrayList<>();
                                    for (int i = 0; i < 10; i++) {
                                        List<Integer> question = new ArrayList<>();
                                        question.add(new Random().nextInt(3 - 1) + 1);
                                        question.addAll(new Random().ints(new Random().nextInt((90 - 30) + 1) + 30, 1, 3).boxed().collect(toList()));
                                        gameQuestions.add(question);
                                    }
                                    return gameQuestions;
                                }
                        )
        ).start(8081);
    }

}
