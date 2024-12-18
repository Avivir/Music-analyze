package com.example.API;

import com.fasterxml.jackson.databind.ObjectMapper;
import netscape.javascript.JSObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class LastFmData {

    String api_key;
    String url = "https://ws.audioscrobbler.com/2.0/";
    private static final HttpClient client = HttpClient.newHttpClient();
    private JSObject response;

    public LastFmData(String api_key){
        this.api_key=api_key;
    }

    private String getRequestURL(String method, String user){
        return url + '?' + "method=" + method + '&' +"user=" + user + '&'+ "api_key=" + api_key + '&' + "format=json";
    }

    public List<User> getUserFriend(String user){
        String requestURL = getRequestURL("user.getfriends", user);
        String jsonResponse = fetchData(requestURL);

        if (jsonResponse != null) {
            return parseUserFriends(jsonResponse);
        }

        return null;
    }

    public List<User> getUserFriendsWithStatus(String user) {
        // Pobierz znajomych
        List<User> users = getUserFriend(user);

        // Jeśli nie udało się pobrać znajomych, zwróć pustą listę
        if (users == null) {
            return new ArrayList<>();
        }

        // Sprawdź aktywność dla każdego użytkownika
        for (User u : users) {
            Boolean isActive = CheckIfUserIsActive(u.getName());
            u.setIsUserActive(isActive);  // Ustaw status aktywności użytkownika
        }

        return users;  // Zwróć zaktualizowaną listę użytkowników
    }

    public Boolean CheckIfUserIsActive(String user) {
        String requestURL = getRequestURL("user.getweeklytrackchart", user);
        String jsonResponse = fetchData(requestURL);

        if (jsonResponse != null) {
            return parseUserActivity(jsonResponse, user);
        }

        return false;  // Jeśli nie udało się sprawdzić aktywności, zwróć false
    }

    private boolean parseUserActivity(String jsonResponse, String username) {
        try {
            // Tworzymy obiekt ObjectMapper do przetwarzania JSON
            ObjectMapper objectMapper = new ObjectMapper();

            // Zdeserializuj JSON na obiekt
            WeeklyTrackChart weeklyTrackChart = objectMapper.readValue(jsonResponse, WeeklyTrackChart.class);

            // Jeśli lista tracków jest pusta, ustaw aktywność na false
            if (weeklyTrackChart.getTrack().isEmpty()) {
                setUserActiveStatus(username, false);
                return false;
            } else {
                setUserActiveStatus(username, true);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Jeśli wystąpił błąd, zwróć false
        }
    }

    private void setUserActiveStatus(String username, boolean isActive) {
        List<User> users = getUserFriend(username);
        if (users != null) {
            for (User user : users) {
                if (user.getName().equals(username)) {
                    user.setIsUserActive(isActive);  // Ustaw aktywność użytkownika
                }
            }
        }
    }


    private List<User> parseUserFriends(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            Root root = objectMapper.readValue(jsonResponse, Root.class);

            return root.getFriends().getUsers();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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