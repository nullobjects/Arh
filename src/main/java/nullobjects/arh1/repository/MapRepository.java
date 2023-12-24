package nullobjects.arh1.repository;

import nullobjects.arh1.model.MapMarker;
import nullobjects.arh1.model.Pipe;
import nullobjects.arh1.model.filters.MarkerValidationFilter;
import nullobjects.arh1.model.filters.UppercaseFilter;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MapRepository implements AutoCloseable {
    private final Connection connection;

    MapRepository() {
        try {
            connection = DriverManager.getConnection("jdbc:h2:file:./src/main/resources/static/mapdb", "map_user", "map_password");
            CreateMapMarkerTable();

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
            e.printStackTrace();
        }
    }

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
            e.printStackTrace();
        }
    }

    public List<MapMarker> getAllMarkers() {
        Pipe<String> pipe1 = new Pipe<>();
        pipe1.addFilter(new UppercaseFilter());

        Pipe<MapMarker> pipe2 = new Pipe<>();
        pipe2.addFilter(new MarkerValidationFilter());

        List<MapMarker> markers = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM PUBLIC.MAP_TRACKERS")) {

            while (resultSet.next()) {
                String name = resultSet.getString("NAME");
                //name = pipe1.runFilters(name);
                String description = resultSet.getString("DESCRIPTION");
                String city = resultSet.getString("CITY");
                String imageUrl = resultSet.getString("IMAGE_URL");
                Double review = resultSet.getDouble("REVIEW");
                Integer working_start = resultSet.getInt("WORKING_START");
                Integer working_end = resultSet.getInt("WORKING_END");
                double xCoord = resultSet.getDouble("X_COORD");
                double yCoord = resultSet.getDouble("Y_COORD");

                MapMarker mapMarker = new MapMarker(name, description, city, imageUrl, review, working_start, working_end, xCoord, yCoord);
                mapMarker = pipe2.runFilters(mapMarker);

                markers.add(mapMarker);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return markers;
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public MapMarker findMarkerByName(String markerName) {
        Pipe<String> pipe1 = new Pipe<>();
        pipe1.addFilter(new UppercaseFilter());

        Pipe<MapMarker> pipe2 = new Pipe<>();
        pipe2.addFilter(new MarkerValidationFilter());

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM PUBLIC.MAP_TRACKERS WHERE UPPER(NAME) LIKE ?");
            statement.setString(1, "%" + markerName.toUpperCase() + "%");

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("NAME");
                //name = pipe1.runFilters(name);
                String description = resultSet.getString("DESCRIPTION");
                String city = resultSet.getString("CITY");
                String imageUrl = resultSet.getString("IMAGE_URL");
                Double review = resultSet.getDouble("REVIEW");
                Integer working_start = resultSet.getInt("WORKING_START");
                Integer working_end = resultSet.getInt("WORKING_END");
                double xCoord = resultSet.getDouble("X_COORD");
                double yCoord = resultSet.getDouble("Y_COORD");

                MapMarker mapMarker = new MapMarker(name, description, city, imageUrl, review, working_start, working_end, xCoord, yCoord);
                mapMarker = pipe2.runFilters(mapMarker);
                resultSet.close();
                statement.close();
                return mapMarker;
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean delete(String name) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM PUBLIC.MAP_TRACKERS WHERE NAME = ?");
            statement.setString(1, name);
            int rowsAffected = statement.executeUpdate();
            statement.close();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public MapMarker findMarkerByCity(String cityName) {
        Pipe<String> pipe1 = new Pipe<>();
        pipe1.addFilter(new UppercaseFilter());

        Pipe<MapMarker> pipe2 = new Pipe<>();
        pipe2.addFilter(new MarkerValidationFilter());

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM PUBLIC.MAP_TRACKERS WHERE UPPER(CITY) LIKE ?");
            statement.setString(1, "%" + cityName.toUpperCase() + "%");

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("NAME");
                //name = pipe1.runFilters(name);
                String description = resultSet.getString("DESCRIPTION");
                String city = resultSet.getString("CITY");
                String imageUrl = resultSet.getString("IMAGE_URL");
                Double review = resultSet.getDouble("REVIEW");
                Integer working_start = resultSet.getInt("WORKING_START");
                Integer working_end = resultSet.getInt("WORKING_END");
                double xCoord = resultSet.getDouble("X_COORD");
                double yCoord = resultSet.getDouble("Y_COORD");

                MapMarker mapMarker = new MapMarker(name, description, city, imageUrl, review, working_start, working_end, xCoord, yCoord);
                mapMarker = pipe2.runFilters(mapMarker);
                resultSet.close();
                statement.close();
                return mapMarker;
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void add(String name,String disc,String city,String image,int start,int end,double x,double y){
        InsertMapMarker(new MapMarker(name,disc,city,image,0D,start,end,x,y));
    }
}

