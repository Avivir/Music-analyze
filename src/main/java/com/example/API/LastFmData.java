package com.example.API;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LastFmData {

    String api_key;
    String url = "https://ws.audioscrobbler.com/2.0/";
    private static final HttpClient client = HttpClient.newHttpClient();
    private WeeklyTrackChart weeklyUserTrack;

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
        List<User> users = getUserFriend(user);

        if (users == null) {
            return new ArrayList<>();
        }

        for (User u : users) {
            Boolean isActive = checkIfUserIsActive(u.getName());
            u.setIsUserActive(isActive);
            int count = 0;
            for(WeeklyTrackChart.Track track : weeklyUserTrack.getTrackChart().getTrack()){
                if(track.getName() != null && track.getPlaycount() != null && track.getArtist() != null){
                    u.setMostListenedMusic(track.getName());
                    u.setNumberOfPlaycounts(track.getPlaycount());
                    u.setListOfArtist(track.getArtist().getText());
                    count++;}


                if(count>=10){
                    break;
                }
            }
        }

        return users;
    }

    public Boolean checkIfUserIsActive(String user) {
        String requestURL = getRequestURL("user.getweeklytrackchart", user);
        String jsonResponse = fetchData(requestURL);

        if (jsonResponse != null) {
            return parseUserActivity(jsonResponse, user);
        }

        return false;
    }

    private boolean parseUserActivity(String jsonResponse, String username) {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            weeklyUserTrack = objectMapper.readValue(jsonResponse, WeeklyTrackChart.class);

            //System.out.println(weeklyTrackChart.getTrackChart().getTrack().get(0).getName());

            if (weeklyUserTrack.getTrackChart().getTrack().isEmpty()) {
                setUserActiveStatus(username, false);
                return false;
            }

            setUserActiveStatus(username, true);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void setUserActiveStatus(String username, boolean isActive) {
        List<User> users = getUserFriend(username);
        if (users != null) {
            for (User user : users) {
                if (user.getName().equals(username)) {
                    user.setIsUserActive(isActive);
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

    private List<String> checkMostListenedSongs(WeeklyTrackChart weeklyTrackChart){
        System.out.println(weeklyTrackChart.getTrackChart().getTrack());
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