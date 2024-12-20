package com.example;
import com.example.API.LastFmData;
import com.example.API.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        LastFmData lastData = new LastFmData("c8b3d6a3ab8ee34be63041d024194261");
        List<User> friends = lastData.getUserFriendsWithStatus("music");

        //for(User friend : friends) {
          //  System.out.println(friend.getName());
            //System.out.println(friend.getCountry());
            //System.out.println(friend.getIsUserActive());
            //int index = 0;
            //for(String track : friend.getMostListenedMusic()){
              //  System.out.println(track + " " + index);
                //System.out.println(friend.getNumberOfPlaycounts().get(index));
               // System.out.println(friend.getListOfArtist().get(index));
                //index++;
            //}
       // }
    }
}