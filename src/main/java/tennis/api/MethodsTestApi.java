package tennis.api;

import com.google.common.base.Splitter;
import net.codestory.http.WebServer;
import tennis.game.TennisGameKataContainer;
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
                        .get("/sets/displayScore", (context) -> {
                            List<Integer> scores = Splitter.on(",").splitToList(context.get("scores")).stream().map(Integer::parseInt).collect(toList());;
                          /*  String player1ScoresAsString = context.get("player1Score");
                            String player2Name = context.get("player2Name");
                            String player2ScoresAsString = context.get("player2Score");*/

                            /*List<Integer> player1Scores = Splitter.on(",").splitToList(player1ScoresAsString).stream().map(Integer::parseInt).collect(toList());
                            List<Integer> player2Scores = Splitter.on(",").splitToList(player2ScoresAsString).stream().map(Integer::parseInt).collect(toList());*/

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
