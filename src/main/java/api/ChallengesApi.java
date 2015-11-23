package api;

import com.google.common.base.Splitter;
import net.codestory.http.WebServer;
import tennis.TennisAlternativeService;
import tennis.TennisFrenchService;
import tennis.TennisService;
import tennis.TennisSetService;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ChallengesApi {

    public static void main(String[] args) {
        TennisService tennisService = new TennisService();
        TennisAlternativeService tennisAlternativeService = new TennisAlternativeService();
        TennisFrenchService tennisFrenchService = new TennisFrenchService();
        TennisSetService tennisSetService = new TennisSetService();

        new WebServer().configure(
                routes -> routes
                        .get("/displayScore", (context) -> tennisService
                                .displayScore(
                                        context.query().getInteger("player1Score"),
                                        context.query().getInteger("player2Score")))
                        .get("/displayAlternativeScore", (context) -> tennisAlternativeService
                                .displayAlternativeScore(context.get("player1Name"),
                                        context.query().getInteger("player1Score"),
                                        context.get("player2Name"),
                                        context.query().getInteger("player2Score")))
                        .get("/displayFrenchScore", (context) -> tennisFrenchService
                                .displayFrenchScore(context.get("player1Name"),
                                        context.query().getInteger("player1Score"),
                                        context.get("player2Name"),
                                        context.query().getInteger("player2Score")))
                        .get("/sets/displayScore", (context) -> {
                            List<Integer> scores = Splitter.on(",").splitToList(context.get("scores")).stream().map(Integer::parseInt).collect(toList());
                            ;
                            return tennisSetService.displaySetScore(scores);
                        })
        ).start(8080);
    }

}
