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

            InsertMapMarker(new MapMarker("Arte", "077834150, http://www.artonline.mk/, Boulevard Mitropolit Teodosij Gologanov 49", "/images/ARTE Galerija.jpg", 41.99491581140953, 21.42130350535809));
            InsertMapMarker(new MapMarker("Zograf", "075584909, Dame Gruev", "/images/Art Gallery Zograf.JPG", 41.99730016371808, 21.427663092023362));
            InsertMapMarker(new MapMarker("Daleria", "070300974, Dimitrie Cupovski 3", "/images/Art Gallery „Daleria“.jpg", 42.00195409802084, 21.42461300829461));
            InsertMapMarker(new MapMarker("Bezisten", "023117150", "/images/Art Gallery Bezisten.png", 41.994670891767875, 21.43045730630835));
            InsertMapMarker(new MapMarker("Martin", "077566233, http://www.artonline.mk/, Ss Cyril & Methodius", "/images/Gallery Martin.jpg", 41.99363045955053, 21.425193531866014));
            InsertMapMarker(new MapMarker("Marta", "070691251 Blvd. Saint Clement of Ohrid 45", "/images/Art Mgm Gallery.jpg", 42.01100546381839, 21.40570773620265));
            InsertMapMarker(new MapMarker("Kol", "071866354, 11th October St.", "/images/Kolektiv Gallery.jpg", 41.996086039694575, 21.431969833161013));
            InsertMapMarker(new MapMarker("Gral", "023230615, http://www.gral.com.mk/, Blvd. Saint Clement of Ohrid 45", "/images/Gral Gallery.jpeg", 41.99747568687593, 21.425561383907795));
            InsertMapMarker(new MapMarker("Pioneri", "070356202, http://pioneri.net/, Mitropolit Teodosij Gologanov 74", "/images/Pioneri Art platform gallery.jpeg", 41.996525459498166, 21.40680432224659));
            InsertMapMarker(new MapMarker("Mala", "078675271, Boulevard Ilinden 65", "/images/MAЛА.jpg", 42.002083516549824, 21.42366768038797));
            InsertMapMarker(new MapMarker("Artida", "071719126", "/images/Atelje ARTIDEA.jpg", 41.99934938805174, 21.42391253164884));
            InsertMapMarker(new MapMarker("Citygal", "11th October St.", "/images/City Gallery Dzani.png", 41.9975394794109, 21.425443369807002));
            InsertMapMarker(new MapMarker("3D", "023073333, ", "/images/Atelje 3D.jpg", 42.00140348595888, 21.412576164042584));
            InsertMapMarker(new MapMarker("Mala stanica", "023126856, http://www.nationalgallery.mk/, 18", "/images/MAЛА.jpg", 41.99154778918674, 21.424894254800908));
            InsertMapMarker(new MapMarker("Anagor", "023224227, http://www.anagor.com.mk/, Naroden Front 21", "/images/Anagor - Kapishtec.jpg", 41.99413749653703, 21.41566834950312));
            InsertMapMarker(new MapMarker("Anju", "078515342", "/images/AN JU Atelje.jpg", 41.999952364682294, 21.497607958477726));
            InsertMapMarker(new MapMarker("Bukefal", "070232459, http://www.bukefal.com.mk/, St Clement of Ohrid 54", "/images/Art Gallery Bukefal.jpg", 41.114552018256965, 20.80021393874093));
            InsertMapMarker(new MapMarker("Dudan", "070501573, http://anastasdudan.com.mk/, Jane Sandanski 14", "/images/Atelier Anastas Dudan.jpg", 41.110054283305445, 20.805640196713345));
            InsertMapMarker(new MapMarker("Emanuela", "046253328, Sveti Kliment Ohridski", "/images/Art Galerie Emanuela.jpg", 41.113900756374626, 20.799741633820744));
        } catch (SQLException e) {
            throw new RuntimeException("Failed to establish a database connection.", e);
        }
    }

    private void CreateMapMarkerTable() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS PUBLIC.MAP_TRACKERS (
                        "name" VARCHAR(255) PRIMARY KEY,
                        "description" TEXT,
                        "image_url" TEXT,
                        "x_coord" DOUBLE,
                        "y_coord" DOUBLE
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
                USING (VALUES (?, ?, ?, ?, ?)) v ("name", "description", "image_url", "x_coord", "y_coord")
                ON t."name" = v."name"
                WHEN MATCHED THEN
                    UPDATE SET
                        "description" = v."description",
                        "image_url" = v."image_url",
                        "x_coord" = v."x_coord",
                        "y_coord" = v."y_coord"
                WHEN NOT MATCHED THEN
                    INSERT (
                        "name",
                        "description",
                        "image_url",
                        "x_coord",
                        "y_coord"
                    ) VALUES (
                        v."name",
                        v."description",
                        v."image_url",
                        v."x_coord",
                        v."y_coord"
                    )
            """);
            statement.setString(1, mapMarker.getName());
            statement.setString(2, mapMarker.getDescription());
            statement.setString(3, mapMarker.getImage_url());
            statement.setDouble(4, mapMarker.getX_coord());
            statement.setDouble(5, mapMarker.getY_coord());
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
                String name = resultSet.getString("name");
                name = pipe1.runFilters(name);
                String description = resultSet.getString("description");
                String imageUrl = resultSet.getString("image_url");
                double xCoord = resultSet.getDouble("x_coord");
                double yCoord = resultSet.getDouble("y_coord");

                MapMarker mapMarker = new MapMarker(name, description, imageUrl, xCoord, yCoord);
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
}
