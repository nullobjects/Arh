package nullobjects.arh1.repository;

import nullobjects.arh1.model.MapMarker;
import nullobjects.arh1.model.Pipe;
import nullobjects.arh1.model.filters.MarkerValidationFilter;
import nullobjects.arh1.model.filters.UppercaseFilter;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class MapRepository implements AutoCloseable {
    private final Connection connection;

    MapRepository() {
        try {
            // Establish database connection and create tables
            connection = DriverManager.getConnection("jdbc:h2:file:./src/main/resources/static/mapdb", "map_user", "map_password");
            CreateMapMarkerTable();
            CreateMapMarkerCommentTable();

            // Insert map markers into the database
            InsertMapMarker(new MapMarker("Arte", "077834150, http://www.artonline.mk/, Boulevard Mitropolit Teodosij Gologanov 49", "Skopje", "/images/ARTE Galerija.jpg", 1.5, 60, 60, 41.99491581140953, 21.42130350535809));
            InsertMapMarker(new MapMarker("Zograf", "075584909, Dame Gruev", "Skopje", "/images/Art Gallery Zograf.JPG", 1.5, 60, 60, 41.99730016371808, 21.427663092023362));
            InsertMapMarker(new MapMarker("Daleria", "070300974, Dimitrie Cupovski 3", "Skopje", "/images/Art Gallery „Daleria“.jpg", 1.5, 60, 60, 42.00195409802084, 21.42461300829461));
            InsertMapMarker(new MapMarker("Bezisten", "023117150", "Skopje", "/images/Art Gallery Bezisten.png", 1.5, 60, 60, 41.994670891767875, 21.43045730630835));
            InsertMapMarker(new MapMarker("Martin", "077566233, http://www.artonline.mk/, Ss Cyril & Methodius", "Skopje", "/images/Gallery Martin.jpg", 1.5, 60, 60, 41.99363045955053, 21.425193531866014));
            InsertMapMarker(new MapMarker("Marta", "070691251 Blvd. Saint Clement of Ohrid 45", "Skopje", "/images/Art Mgm Gallery.jpg", 1.5, 60, 60, 42.01100546381839, 21.40570773620265));
            InsertMapMarker(new MapMarker("Kol", "071866354, 11th October St.", "Skopje", "/images/Kolektiv Gallery.jpg", 1.5, 60, 60, 41.996086039694575, 21.431969833161013));
            InsertMapMarker(new MapMarker("Gral", "023230615, http://www.gral.com.mk/, Blvd. Saint Clement of Ohrid 45", "Skopje", "/images/Gral Gallery.jpeg", 1.5, 60, 60, 41.99747568687593, 21.425561383907795));
            InsertMapMarker(new MapMarker("Pioneri", "070356202, http://pioneri.net/, Mitropolit Teodosij Gologanov 74", "Skopje", "/images/Pioneri Art platform gallery.jpeg", 1.5, 60, 60, 41.996525459498166, 21.40680432224659));
            InsertMapMarker(new MapMarker("Mala", "078675271, Boulevard Ilinden 65", "Skopje", "/images/MAЛА.jpg", 1.5, 60, 60, 42.002083516549824, 21.42366768038797));
            InsertMapMarker(new MapMarker("Artida", "071719126", "Skopje", "/images/Atelje ARTIDEA.jpg", 1.5,60, 60, 41.99934938805174, 21.42391253164884));
            InsertMapMarker(new MapMarker("Citygal", "11th October St.", "Skopje", "/images/City Gallery Dzani.png", 1.5,60, 60, 41.9975394794109, 21.425443369807002));
            InsertMapMarker(new MapMarker("3D", "023073333, ", "Skopje", "/images/Atelje 3D.jpg", 1.5,60, 60, 42.00140348595888, 21.412576164042584));
            InsertMapMarker(new MapMarker("Mala stanica", "023126856, http://www.nationalgallery.mk/, 18", "Skopje", "/images/MAЛА.jpg", 1.5,60, 60, 41.99154778918674, 21.424894254800908));
            InsertMapMarker(new MapMarker("Anagor", "023224227, http://www.anagor.com.mk/, Naroden Front 21", "Skopje", "/images/Anagor - Kapishtec.jpg", 1.5,60, 60, 41.99413749653703, 21.41566834950312));
            InsertMapMarker(new MapMarker("Anju", "078515342", "Skopje", "/images/AN JU Atelje.jpg", 1.5,60, 60, 41.999952364682294, 21.497607958477726));
            InsertMapMarker(new MapMarker("Bukefal", "070232459, http://www.bukefal.com.mk/, St Clement of Ohrid 54", "Ohrid", "/images/Art Gallery Bukefal.jpg", 1.5,60, 60, 41.114552018256965, 20.80021393874093));
            InsertMapMarker(new MapMarker("Dudan", "070501573, http://anastasdudan.com.mk/, Jane Sandanski 14", "Ohrid", "/images/Atelier Anastas Dudan.jpg", 1.5,60, 60, 41.110054283305445, 20.805640196713345));
            InsertMapMarker(new MapMarker("Emanuela", "046253328, Sveti Kliment Ohridski", "Ohrid", "/images/Art Galerie Emanuela.jpg", 1.5,60, 60, 41.113900756374626, 20.799741633820744));
        } catch (SQLException e) {
            throw new RuntimeException("Failed to establish a database connection.", e);
        }
    }

    // Creates the table for storing map markers if it doesn't already exist.
    private void CreateMapMarkerTable() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS PUBLIC.MAP_TRACKERS (
                        "NAME" VARCHAR(255) PRIMARY KEY,
                        "DESCRIPTION" TEXT,
                        "CITY" TINYTEXT,
                        "IMAGE_URL" TEXT,
                        "REVIEW" DOUBLE,
                        "WORKING_START" DOUBLE,
                        "WORKING_END" DOUBLE,
                        "X_COORD" DOUBLE,
                        "Y_COORD" DOUBLE
                    )
                """);
            statement.close();

            connection.commit();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    // Creates the table for storing comments related to map markers if it doesn't already exist.
    private void CreateMapMarkerCommentTable() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("""
                CREATE TABLE IF NOT EXISTS PUBLIC.MAP_TRACKER_COMMENTS (
                    ID INT AUTO_INCREMENT PRIMARY KEY,
                    MARKER_NAME VARCHAR(255),
                    USERNAME VARCHAR(32),
                    COMMENT TEXT
                )
            """);
            statement.close();

            connection.commit();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    // Inserts or updates a map marker into the database.
    public void InsertMapMarker(MapMarker mapMarker) {
        try {
            PreparedStatement statement = connection.prepareStatement("""
                MERGE INTO PUBLIC.MAP_TRACKERS t
                USING (VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)) v ("NAME", "DESCRIPTION", "CITY", "IMAGE_URL", "REVIEW", "WORKING_START", "WORKING_END", "X_COORD", "Y_COORD")
                ON t."NAME" = v."NAME"
                WHEN MATCHED THEN
                    UPDATE SET
                        "DESCRIPTION" = v."DESCRIPTION",
                        "CITY" = v."CITY",
                        "IMAGE_URL" = v."IMAGE_URL",
                        "REVIEW" = v."REVIEW",
                        "WORKING_START" = v."WORKING_START",
                        "WORKING_END" = v."WORKING_END",
                        "X_COORD" = v."X_COORD",
                        "Y_COORD" = v."Y_COORD"
                WHEN NOT MATCHED THEN
                    INSERT (
                        "NAME",
                        "DESCRIPTION",
                        "CITY",
                        "IMAGE_URL",
                        "REVIEW",
                        "WORKING_START",
                        "WORKING_END",
                        "X_COORD",
                        "Y_COORD"
                    ) VALUES (
                        v."NAME",
                        v."DESCRIPTION",
                        v."CITY",
                        v."IMAGE_URL",
                        v."REVIEW",
                        v."WORKING_START",
                        v."WORKING_END",
                        v."X_COORD",
                        v."Y_COORD"
                    )
            """);
            statement.setString(1, mapMarker.getName());
            statement.setString(2, mapMarker.getDescription());
            statement.setString(3, mapMarker.getCity());
            statement.setString(4, mapMarker.getImage_url());
            statement.setDouble(5, mapMarker.getReview());
            statement.setDouble(6, mapMarker.getWorking_start());
            statement.setDouble(7, mapMarker.getWorking_end());
            statement.setDouble(8, mapMarker.getX_coord());
            statement.setDouble(9, mapMarker.getY_coord());
            statement.execute();

            connection.commit();
            statement.close();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    // Inserts a comment for a specific map marker into the database.
    public void InsertMarkerComment(String name, String username, String comment) {
        try {
            PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO PUBLIC.MAP_TRACKER_COMMENTS ("MARKER_NAME", "USERNAME", "COMMENT") VALUES (?, ?, ?)
            """);
            statement.setString(1, name);
            statement.setString(2, username);
            statement.setString(3, comment);
            statement.execute();

            connection.commit();
            statement.close();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    // Retrieves comments for a specific map marker from the database.
    public List<String> GetMarkerComments(String name) {
        List<String> comments = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM PUBLIC.MAP_TRACKER_COMMENTS WHERE UPPER(MARKER_NAME) = ?");
            statement.setString(1, '%' + name.toUpperCase() + '%');

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String comment = resultSet.getString("COMMENT");
                comments.add(comment);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return comments;
    }

    // Retrieves all map markers from the database.
    public List<MapMarker> getAllMarkers() {
        List<MapMarker> markers = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM PUBLIC.MAP_TRACKERS")) {

            while (resultSet.next()) {
                markers.add(mapResultSetToMapMarker(resultSet));
            }

        } catch (SQLException e) {
            handleSQLException(e);
        }

        return markers;
    }

    // Retrieves a map marker from the database.
    public MapMarker getMarker(String name) {
        MapMarker marker = null;

        try {
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM PUBLIC.MAP_TRACKERS WHERE UPPER(NAME) LIKE ?");
             statement.setString(1, '%' + name.toUpperCase() + '%');
             ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                marker = mapResultSetToMapMarker(resultSet);
            }

        } catch (SQLException e) {
            handleSQLException(e);
        }

        return marker;
    }

    // Closes the database connection.
    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    // Finds a map marker by its name in the database.
    public MapMarker findMarkerByName(String markerName) {
        try (PreparedStatement statement = prepareStatementForName(markerName);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return mapResultSetToMapMarker(resultSet);
            }

        } catch (SQLException e) {
            handleSQLException(e); // Handle exception appropriately
        }

        return null;
    }

    // Deletes a map marker from the database by its name.
    public boolean delete(String name) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM PUBLIC.MAP_TRACKERS WHERE NAME = ?");
            statement.setString(1, name);
            int rowsAffected = statement.executeUpdate();
            statement.close();
            return rowsAffected > 0;
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return false;
    }

    // Finds a map marker by its city in the database.
    public MapMarker findMarkerByCity(String cityName) {
        Pipe<String> pipe1 = new Pipe<>();
        pipe1.addFilter(new UppercaseFilter());

        Pipe<MapMarker> pipe2 = new Pipe<>();
        pipe2.addFilter(new MarkerValidationFilter());

        try (PreparedStatement statement = prepareStatementForCity(cityName)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToMapMarker(resultSet);
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return null;
    }

    // Adds a new map marker to the database.
    public void add(String name, String disc, String city, String image, int start, int end, double x, double y) {
        InsertMapMarker(new MapMarker(name, disc, city, image, 0D, start, end, x, y));
    }


    public void addComment(String name, String comment) {

    }

    //Retrieves comments associated with a map marker.
    private List<String> getMarkerComments(String markerName) {
        // Implement logic to retrieve comments for the marker
        return Collections.emptyList(); // Placeholder, replace with actual logic
    }

    /**
     * Prepares a PreparedStatement for retrieving map tracker data based on a partial city name.
     * Constructs a SQL query to select data from the MAP_TRACKERS table where the city matches
     * the given cityName. Sets the parameter in the PreparedStatement
     * to search for the given cityName pattern in uppercase. Returns the prepared statement.
     */
    private PreparedStatement prepareStatementForCity(String cityName) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM PUBLIC.MAP_TRACKERS WHERE UPPER(CITY) LIKE ?");
        statement.setString(1, "%" + cityName.toUpperCase() + "%");
        return statement;
    }


    /**
     * Prepares a PreparedStatement for retrieving map tracker data based on a partial marker name.
     * Constructs a SQL query to select data from the MAP_TRACKERS table where the name matches
     * the given markerName. Sets the parameter in the PreparedStatement
     * to search for the given markerName pattern in uppercase. Returns the prepared statement.
     */
    private PreparedStatement prepareStatementForName(String markerName) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM PUBLIC.MAP_TRACKERS WHERE UPPER(NAME) LIKE ?");
        statement.setString(1, "%" + markerName.toUpperCase() + "%");
        return statement;
    }

    /**
     * Converts a ResultSet containing data about a map marker into a MapMarker object.
     * Retrieves information such as name, description, city, image URL, review, working hours,
     * and coordinates from the ResultSet. Sets comments related to the map marker's name
     * using the getMarkerComments method. Applies filters to the MapMarker object for validation
     * and processing. Returns the processed MapMarker object.
     */
    private MapMarker mapResultSetToMapMarker(ResultSet resultSet) throws SQLException {
        Pipe<String> pipe1 = new Pipe<>();
        pipe1.addFilter(new UppercaseFilter());

        Pipe<MapMarker> pipe2 = new Pipe<>();
        pipe2.addFilter(new MarkerValidationFilter());

        String name = resultSet.getString("NAME");
        String description = resultSet.getString("DESCRIPTION");
        String city = resultSet.getString("CITY");
        String imageUrl = resultSet.getString("IMAGE_URL");
        Double review = resultSet.getDouble("REVIEW");
        Integer workingStart = resultSet.getInt("WORKING_START");
        Integer workingEnd = resultSet.getInt("WORKING_END");
        double xCoord = resultSet.getDouble("X_COORD");
        double yCoord = resultSet.getDouble("Y_COORD");

        MapMarker mapMarker = new MapMarker(name, description, city, imageUrl, review, workingStart, workingEnd, xCoord, yCoord);
        mapMarker.setComments(getMarkerComments(name)); // Assuming getMarkerComments is defined elsewhere
        mapMarker = pipe2.runFilters(mapMarker);
        return mapMarker;
    }

    // Handle exception
    private void handleSQLException(SQLException e) {
        e.printStackTrace();
    }

    /**
     * Converts a ResultSet containing data about a map marker into a MapMarker object.
     */
    public String GetMarkerComment(String markerName, String commentText) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM PUBLIC.MAP_TRACKER_COMMENTS WHERE UPPER(MARKER_NAME) LIKE ? AND UPPER(COMMENT) LIKE ?");
            statement.setString(1, '%' + markerName.toUpperCase() + '%');
            statement.setString(2, '%' + commentText.toUpperCase() + '%');

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getString("COMMENT");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return null;
    }
}

