package com.example;
import com.example.API.LastFmData;
import com.example.API.User;
import com.example.Database.DatabaseConnection;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        LastFmData lastData = new LastFmData("c8b3d6a3ab8ee34be63041d024194261");

        List<User> friends = lastData.getUserFriendsWithStatus("rj");

        friends.forEach(w -> {
            List<User> friends2 = lastData.getUserFriendsWithStatus(w.getName());
          if (Objects.nonNull(friends2) && friends2.size() > 0) {
              friends.add(w);
              System.out.println(friends.size());
                  friends2.forEach(w2 -> {
                      List<User> friends3 = lastData.getUserFriendsWithStatus(w.getName());
                      if (Objects.nonNull(friends3) && friends3.size() > 0) {
                      friends.add(w2);
                      System.out.println(friends.size());
                      friends3.forEach(w3 -> {
                          List<User> friends4 = lastData.getUserFriendsWithStatus(w2.getName());
                          if (Objects.nonNull(friends4) && friends4.size() > 0) {
                              friends.add(w3);
                              System.out.println(friends.size());
//                              friends4.forEach(w4 -> {
//                                  List<User> friends5 = lastData.getUserFriendsWithStatus(w2.getName());
//
//                              });
                          }
                      });
                      }
                  });
          }
        });

        System.out.println("dupa");

//        Connection connection = databaseConnection.getConnection();
//
//        databaseConnection.batchInsert(connection, friends);
        }
    }
