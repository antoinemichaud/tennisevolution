package api;

import net.codestory.http.WebServer;
import tennis.TennisAlternativeService;
import tennis.TennisFrenchService;
import tennis.TennisService;

public class ChallengesApi {

    public static void main(String[] args) {
        TennisService tennisService = new TennisService();
        TennisAlternativeService tennisAlternativeService = new TennisAlternativeService();
        TennisFrenchService tennisFrenchService = new TennisFrenchService();

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
        ).start(8080);
    }

}
