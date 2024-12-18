package com.example;
import com.example.API.LastFmData;
import com.example.API.User;

import java.io.Console;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        LastFmData lastData = new LastFmData("c8b3d6a3ab8ee34be63041d024194261");
        List<User> friends = lastData.getUserFriend("music");

        for(User friend : friends) {
            System.out.println(friend.getName());
            System.out.println(friend.getIsUserActive());
        }
    }
}