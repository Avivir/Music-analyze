package com.example.Database;
import com.example.InformationFromJson.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void batchInsert(Connection connection, List<User> users) {
        String insertQuery = "INSERT INTO users (name, country, mostlistenedmusic, listofartist, numberofplaycounts) VALUES (?, ? , ? , ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            int count = 0;

            for (User user : users) {
                statement.setString(1, user.getName());
                statement.setString(2, user.getCountry());
                statement.setString(3, user.getMostListenedMusic().toString());
                statement.setString(4, user.getListOfArtist().toString());
                statement.setString(5, user.getNumberOfPlaycounts().toString());

                statement.addBatch();

                if (++count % 1000 == 0) {
                    statement.executeBatch();
                }
            }

            statement.executeBatch();
            System.out.println("Data was successfully inserted into the table in batches.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while inserting data into table.");
        }
    }

    public static void fetchData() {
        String selectQuery = "SELECT * FROM users";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("Data in the users table:");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                System.out.println("Name: " + name + ", Age: " + age);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while extracting data from table.");
        }
    }
}
