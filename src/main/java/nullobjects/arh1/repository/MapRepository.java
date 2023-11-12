package nullobjects.arh1.repository;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class MapRepository implements AutoCloseable {
    private final Connection connection;

    MapRepository() {
        try {
            connection = DriverManager.getConnection("jdbc:h2:file:./src/main/resources/static/mapdb", "map_user", "map_password");
            System.out.println(connection);
            try {
                Statement statement = connection.createStatement();
                String createTableQuery = """
                    CREATE TABLE IF NOT EXISTS PUBLIC."map_trackers" (
                        "name" VARCHAR(255) PRIMARY KEY,
                        "description" TEXT,
                        "image_url" TEXT,
                        "x_coord" FLOAT(15),
                        "y_coord" FLOAT(15)
                    )
                """;
                boolean success = statement.execute(createTableQuery);
                System.out.println(statement);
                if (!success) {
                    int updateCount = statement.getUpdateCount();
                    if (updateCount == -1) {
                        // If updateCount is -1, it means there are no results, which is expected for DDL statements.
                        System.out.println("Query ran successfully, but there are no results.");
                    } else {
                        // If updateCount is not -1, it means the query was an update statement.
                        System.out.println("Query ran successfully, and " + updateCount + " rows were affected.");
                    }
                }                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to establish a database connection.", e);
        }
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }
    }
}
