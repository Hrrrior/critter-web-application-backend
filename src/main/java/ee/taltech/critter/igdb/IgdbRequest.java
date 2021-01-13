package ee.taltech.critter.igdb;

import ee.taltech.critter.exception.ApiBadRequestException;
import org.json.JSONArray;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.MessageFormat;


public class IgdbRequest {
    public final String clientID = "";
    public final String authoriztionToken = "";
    public final HttpClient httpClient = HttpClient
            .newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public JSONArray sendPost(RequestTypes requestTypes, String search, int limiter) {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(MessageFormat
                        .format(requestTypes.getBody(), search, limiter)))
                .uri(URI.create(requestTypes.getLink()))
                .setHeader("Client-ID", clientID)
                .setHeader("Authorization", authoriztionToken)
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JSONArray jsonArray = new JSONArray(response.body());
            return jsonArray.length() > 0 ? jsonArray : null;
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            throw new ApiBadRequestException("Request to IGDB has failed. Please try again.");
        }
    }

}
