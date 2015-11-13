package tennis.api;

import com.google.common.base.Splitter;
import net.codestory.http.WebServer;
import tennis.game.TennisGameKataContainer;
import tennis.generator.TennisSetGenerator;
import tennis.history.HistoryKeeper;
import tennis.set.TennisSetKataContainer;

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
                            String player1ScoresAsString = context.get("player1Scores");
                            String player2ScoresAsString = context.get("player2Scores");

                            List<Integer> player1Scores = Splitter.on(",").splitToList(player1ScoresAsString).stream().map(Integer::parseInt).collect(toList());
                            List<Integer> player2Scores = Splitter.on(",").splitToList(player2ScoresAsString).stream().map(Integer::parseInt).collect(toList());

                            return tennisSetKataContainer.displayScore(player1Scores, player2Scores);
                        })
        ).start(8080);

        TennisSetGenerator tennisSetGenerator = new TennisSetGenerator(new Random());
        Random gameIsFinished = new Random();

        HistoryKeeper historyKeeper = tennisSetGenerator.generate(tennisGame -> gameIsFinished.nextInt(5) == 0);


//        new WebServer().configure(
//                routes -> routes
//                        .get("/generateTest/step1", context ->
//                        )
    }
}
