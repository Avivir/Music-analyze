package com.example.API2;

import com.example.API.User;
import com.example.API.WeeklyTrackChart;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class LastFmAPI {
    String api_key;
    String url = "https://ws.audioscrobbler.com/2.0/";
    private static final HttpClient client = HttpClient.newHttpClient();
    private WeeklyTrackChart weeklyUserTrack;

    public LastFmAPI(String api_key){
        this.api_key=api_key;
    }

    public String getRequestURL(String method, String user){
        return url + '?' + "method=" + method + '&' +"user=" + user + '&'+ "api_key=" + api_key + '&' + "format=json";
    }

    public String getData(String user, String method){
        String requestURL = getRequestURL(method, user);

        String jsonResponse = fetchData(requestURL);

        if (jsonResponse != null) {
            return jsonResponse;
        }

        return null;
    }

    private String fetchData(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                throw new Exception("Error: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
