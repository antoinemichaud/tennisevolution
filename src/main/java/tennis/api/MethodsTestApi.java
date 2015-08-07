package tennis.api;

import net.codestory.http.WebServer;
import tennis.game.TennisGameKataContainer;

public class MethodsTestApi {

    public static void main(String[] args) {
        TennisGameKataContainer tennisGameKataContainer = new TennisGameKataContainer();

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
        ).start();

    }
}
