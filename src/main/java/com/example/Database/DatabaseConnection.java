package com.example.Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";  // Zmienna URL bazy
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            String query = "SELECT * FROM lastfmusers";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    String name = resultSet.getString("username");  // Załóżmy, że masz kolumnę "name"
                    System.out.println(name);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd połączenia z bazą danych.");
        }
    }
}
