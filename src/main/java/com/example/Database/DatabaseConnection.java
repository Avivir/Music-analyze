package com.example.Database;
import com.example.API.Friends;
import com.example.API.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.*;

import java.sql.*;
import java.util.List;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres"; // Zmień na swoją nazwę użytkownika
    private static final String PASSWORD = "postgres"; // Zmień na swoje hasło

    // Metoda do nawiązania połączenia
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void batchInsert(Connection connection, List<User> users) {
        String insertQuery = "INSERT INTO users (name, country, isuseractive, mostlistenedmusic, listofartist, numberofplaycounts) VALUES (?, ?, ? , ? , ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            int count = 0;

            for (User user : users) {
                statement.setString(1, user.getName());
                statement.setString(2, user.getCountry());
                statement.setBoolean(3, user.getIsUserActive());
                statement.setString(4, user.getMostListenedMusic().toString());
                statement.setString(5, user.getListOfArtist().toString());
                statement.setString(6, user.getNumberOfPlaycounts().toString());

                statement.addBatch();

                if (++count % 1000 == 0) {
                    statement.executeBatch();
                }
            }

            statement.executeBatch(); // Wykonaj pozostałe wpisy
            System.out.println("Dane zostały pomyślnie wstawione do tabeli w batchach.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd podczas wstawiania danych do tabeli.");
        }
    }

    // Metoda do wyciągania danych
    public static void fetchData() {
        String selectQuery = "SELECT * FROM users";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("Dane w tabeli users:");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                System.out.println("Name: " + name + ", Age: " + age);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd podczas wyciągania danych z tabeli.");
        }
    }
}
