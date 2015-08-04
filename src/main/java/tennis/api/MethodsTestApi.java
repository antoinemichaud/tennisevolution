package tennis.api;

import net.codestory.http.WebServer;

public class MethodsTestApi {

    public static void main(String[] args) {
        new WebServer().configure(routes -> routes.get("/", "Hello World")).start();
    }

}
