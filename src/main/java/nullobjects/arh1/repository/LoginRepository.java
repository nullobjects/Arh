package nullobjects.arh1.repository;

import nullobjects.arh1.model.User;
import nullobjects.arh1.model.exceptions.UsernameExistsException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing user login and registration.
 */
@Repository
public class LoginRepository {
    // Database connection
    private final Connection connection;

    /**
     * Constructor to establish a database connection and create the USERS table if it doesn't exist.
     */
    LoginRepository() {
        try {
            // Establishing a database connection
            connection = DriverManager.getConnection("jdbc:h2:file:./src/main/resources/static/mapdb", "map_user", "map_password");
            // Creating the USERS table if it doesn't exist
            CreateUserTable();
        } catch (SQLException e) {
            // Throw a runtime exception if database connection fails
            throw new RuntimeException("Failed to establish a database connection.", e);
        }
    }

    /**
     * Creates the USERS table if it doesn't exist.
     */
    private void CreateUserTable() {
        try {
            Statement statement = connection.createStatement();
            // Creating the USERS table with columns USERNAME and PASSWORD
            statement.execute("""
                CREATE TABLE IF NOT EXISTS PUBLIC.USERS (
                    "USERNAME" VARCHAR(32) PRIMARY KEY,
                    "PASSWORD" VARCHAR(32)
                )
            """);
            statement.close();

            connection.commit();
        } catch (SQLException e) {
            // Handle SQL exception
            handleSQLException(e);
        }
    }

    /**
     * Registers a new user.
     * @param user The user to be registered.
     * @return True if the registration is successful, false otherwise.
     * @throws UsernameExistsException if the username already exists.
     */
    public boolean RegisterUser(User user) throws UsernameExistsException {
        // Check if the username already exists
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
            // Handle SQL exception
            handleSQLException(e);
            return false;
        }
    }

    /**
     * Checks if a username already exists.
     * @param username The username to check.
     * @return True if the username exists, false otherwise.
     */
    private boolean isUsernameExists(String username) {
        String selectQuery = "SELECT 1 FROM PUBLIC.USERS WHERE USERNAME = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            // Handle SQL exception
            handleSQLException(e);
            return false;
        }
    }

    /**
     * Retrieves a user by username.
     * @param username The username of the user to retrieve.
     * @return The user object if found, null otherwise.
     */
    public User GetUserByUserName(String username) {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT PASSWORD FROM PUBLIC.USERS")) {

            if (resultSet.next()) {
                return new User(username, resultSet.getString("password"));
            }
        } catch (SQLException e) {
            // Handle SQL exception
            handleSQLException(e);
        }

        return null;
    }

    /**
     * Authenticates a user login.
     * @param user The user to authenticate.
     * @return True if authentication is successful, false otherwise.
     */
    public boolean LoginUser(User user) {
        String query = "SELECT '1' FROM PUBLIC.USERS WHERE USERNAME = ? AND PASSWORD = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            // Handle SQL exception
            handleSQLException(e);
        }

        return false;
    }

    /**
     * Retrieves a list of all users.
     * @return A list of User objects.
     */
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
            // Handle SQL exception
            handleSQLException(e);
        }
        return userList;
    }

    // Handle SQL exception
    private void handleSQLException(SQLException e) {
        e.printStackTrace();
    }

}
