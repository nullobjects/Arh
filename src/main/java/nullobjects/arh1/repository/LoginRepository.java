package nullobjects.arh1.repository;

import nullobjects.arh1.model.User;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class LoginRepository {
    private final Connection connection;

    LoginRepository() {
        try {
            connection = DriverManager.getConnection("jdbc:h2:file:./src/main/resources/static/mapdb", "map_user", "map_password");
            CreateUserTable();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to establish a database connection.", e);
        }
    }

    private void CreateUserTable() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("""
                CREATE TABLE IF NOT EXISTS PUBLIC.USERS (
                    "USERNAME" VARCHAR(32) PRIMARY KEY,
                    "PASSWORD" VARCHAR(32)
                )
            """);
            statement.close();

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean RegisterUser(User user) {
        String query = "INSERT INTO PUBLIC.USERS (USERNAME, PASSWORD) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User GetUserByUserName(String username) {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT PASSWORD FROM PUBLIC.USERS")) {

            if (resultSet.next()) {
                return new User(username, resultSet.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
