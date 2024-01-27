package nullobjects.arh1.repository;

import nullobjects.arh1.model.User;
import nullobjects.arh1.model.exceptions.UsernameExistsException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            handleSQLException(e);
        }
    }

    public boolean RegisterUser(User user) throws UsernameExistsException {
        if (isUsernameExists(user.getUsername())) {
            throw new UsernameExistsException();
        }

        String insertQuery = "INSERT INTO PUBLIC.USERS (USERNAME, PASSWORD) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }

    private boolean isUsernameExists(String username) {
        String selectQuery = "SELECT 1 FROM PUBLIC.USERS WHERE USERNAME = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            handleSQLException(e);
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
            handleSQLException(e);
        }

        return null;
    }

    public boolean LoginUser(User user) {
        String query = "SELECT '1' FROM PUBLIC.USERS WHERE USERNAME = ? AND PASSWORD = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return false;
    }

    public List<User> getUsers() {
        List<User> userList = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM PUBLIC.USERS")) {

            while (resultSet.next()) {
                String username = resultSet.getString("USERNAME");
                String password = resultSet.getString("PASSWORD");
                User user = new User(username, password);
                userList.add(user);
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return userList;
    }

    // Handle exception
    private void handleSQLException(SQLException e) {
        e.printStackTrace();
    }

}
