package com.example;

import com.example.API.LastFmAPI;
import com.example.Database.DatabaseConnection;
import com.example.InformationFromJson.Root;
import com.example.InformationFromJson.User;
import com.example.InformationFromJson.WeeklyTrackChart;
import com.example.Json.JsonParse;
import com.fasterxml.jackson.core.type.TypeReference;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        LastFmAPI lastData = new LastFmAPI("c8b3d6a3ab8ee34be63041d024194261");
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        JsonParse jsonParser = new JsonParse();
        List<String> usersNames = new ArrayList<>(Arrays.asList("music"));

        String getFriends = "user.getfriends";
        String getUserWeeklyTrackChart = "user.getweeklytrackchart";

        while(!usersNames.isEmpty()){
           Root root = jsonParser.parseJson(lastData.getData(usersNames.get(0), getFriends), new TypeReference<Root>(){});
            List<User> users = root.getFriends().getUsers();

            List<User> activeUsersToLoad = searchForActiveUsers(users,jsonParser,lastData, getUserWeeklyTrackChart);

            databaseConnection.batchInsert(connection, activeUsersToLoad);

            for (User user : activeUsersToLoad) {
                usersNames.add(user.getName());
            }
            activeUsersToLoad.removeAll(activeUsersToLoad);
            usersNames.remove(0);
        }
    }

    public static List<User> searchForActiveUsers(List<User> users, JsonParse jsonParser, LastFmAPI lastData, String method) {
        List<User> activeUsers = new ArrayList<>();

        for (User user : users) {
            WeeklyTrackChart weeklyTrackChart = jsonParser.parseJson(lastData.getData(user.getName(), method), new TypeReference<WeeklyTrackChart>(){});

            if (weeklyTrackChart != null && !weeklyTrackChart.getTrackChart().getTrack().isEmpty()) {
                int i = 0;
                for (WeeklyTrackChart.Track tracks : weeklyTrackChart.getTrackChart().getTrack()) {
                    user.setMostListenedMusic(tracks.getName());
                    user.setNumberOfPlaycounts(tracks.getPlaycount());
                    user.setListOfArtist(tracks.getArtist().getText());
                    i++;
                    if(i>=10){
                        break;
                    }
                }
                activeUsers.add(user);
            }
        }

        return activeUsers;
    }

}


