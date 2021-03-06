import java.util.Scanner;

import org.scribe.builder.*;
import org.scribe.builder.api.*;
import org.scribe.model.*;
import org.scribe.oauth.*;

public class Main {
    private static final String PROTECTED_RESOURCE_URL = "https://api.twitter.com/1.1/account/verify_credentials.json";
    private static final String LAST_TWETTS = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=jebinha&count=10";

    public static void main(String[] args){
        // If you choose to use a callback, "oauth_verifier" will be the return value by Twitter (request param)
        OAuthService service = new ServiceBuilder()
                .provider(TwitterApi.class)
                .apiKey("GKk9lFvbl2xqVyB5nQyrUjr7k")
                .apiSecret("NC85y0Onglf5o7LyEMkkKSMA4Wd8lL5na9MtuIB3TnmIDmQy55")
                .build();
        Scanner in = new Scanner(System.in);

        System.out.println("=== Twitter's OAuth Workflow ===");
        System.out.println();

        // Obtain the Request Token
        System.out.println("Fetching the Request Token...");
        Token requestToken = service.getRequestToken();
        System.out.println("Got the Request Token!");
        System.out.println();

        System.out.println("Now go and authorize Scribe here:");
        System.out.println(service.getAuthorizationUrl(requestToken));
        System.out.println("And paste the verifier here");
        System.out.print(">>");
        Verifier verifier = new Verifier(in.nextLine());
        System.out.println();

        // Trade the Request Token and Verfier for the Access Token
        System.out.println("Trading the Request Token for an Access Token...");
        Token accessToken = service.getAccessToken(requestToken, verifier);
        System.out.println("Got the Access Token!");
        System.out.println("(if you're curious, it looks like this: " + accessToken + " )");
        System.out.println();

        // Now let's go and ask for a protected resource!
        System.out.println("Now we're going to access a protected resource...");
        OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        service.signRequest(accessToken, request);
        Response response = request.send();
        System.out.println("Got it! Lets see what we found...");
        System.out.println();
        System.out.println(response.getBody());

        System.out.println();
        System.out.println("That's it man! Go and build something awesome with Scribe! :)");

        System.out.println("Recuperando ultimos dez twetts");
        OAuthRequest request2 = new OAuthRequest(Verb.GET, LAST_TWETTS);
        service.signRequest(accessToken, request2);
        Response response2 = request2.send();
        System.out.println("Resultado...");
        System.out.println();
        System.out.println(response2.getBody());
    }

}