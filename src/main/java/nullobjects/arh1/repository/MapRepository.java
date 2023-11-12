package nullobjects.arh1.repository;

import nullobjects.arh1.model.MapMarker;
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

            InsertMapMarker(new MapMarker("Arte", "Description", "/images/326554246_606384704828028_7636741843512573046_n.jpg", 41.99491581140953, 21.42130350535809));
            InsertMapMarker(new MapMarker("Zograf", "Description", "/images/326554246_606384704828028_7636741843512573046_n.jpg", 41.99730016371808, 21.427663092023362));
            InsertMapMarker(new MapMarker("Daleria", "Description", "/images/326554246_606384704828028_7636741843512573046_n.jpg", 42.00195409802084, 21.42461300829461));
            InsertMapMarker(new MapMarker("Bezi", "Description", "/images/326554246_606384704828028_7636741843512573046_n.jpg", 41.994670891767875, 21.43045730630835));
            InsertMapMarker(new MapMarker("Martin", "Description", "/images/326554246_606384704828028_7636741843512573046_n.jpg", 41.99363045955053, 21.425193531866014));
            InsertMapMarker(new MapMarker("Mart", "Description", "/images/326554246_606384704828028_7636741843512573046_n.jpg", 42.01100546381839, 21.40570773620265));
            InsertMapMarker(new MapMarker("Kol", "Description", "/images/326554246_606384704828028_7636741843512573046_n.jpg", 41.996086039694575, 21.431969833161013));
            InsertMapMarker(new MapMarker("Gral", "Description", "/images/326554246_606384704828028_7636741843512573046_n.jpg", 41.99747568687593, 21.425561383907795));
            InsertMapMarker(new MapMarker("Pioner", "Description", "/images/326554246_606384704828028_7636741843512573046_n.jpg", 41.996525459498166, 21.40680432224659));
            InsertMapMarker(new MapMarker("Mala", "Description", "/images/326554246_606384704828028_7636741843512573046_n.jpg", 42.002083516549824, 21.42366768038797));
            InsertMapMarker(new MapMarker("Artida", "Description", "/images/326554246_606384704828028_7636741843512573046_n.jpg", 41.99934938805174, 21.42391253164884));
            InsertMapMarker(new MapMarker("Citygal", "Description", "/images/326554246_606384704828028_7636741843512573046_n.jpg", 41.9975394794109, 21.425443369807002));
            InsertMapMarker(new MapMarker("D3", "Description", "/images/326554246_606384704828028_7636741843512573046_n.jpg", 42.00140348595888, 21.412576164042584));
            InsertMapMarker(new MapMarker("Malstanci", "Description", "/images/326554246_606384704828028_7636741843512573046_n.jpg", 41.99154778918674, 21.424894254800908));
            InsertMapMarker(new MapMarker("Anago", "Description", "/images/326554246_606384704828028_7636741843512573046_n.jpg", 41.99413749653703, 21.41566834950312));
            InsertMapMarker(new MapMarker("Anju", "Description", "/images/326554246_606384704828028_7636741843512573046_n.jpg", 41.999952364682294, 21.497607958477726));
            InsertMapMarker(new MapMarker("Bukefal", "Description", "/images/326554246_606384704828028_7636741843512573046_n.jpg", 41.114552018256965, 20.80021393874093));
            InsertMapMarker(new MapMarker("Dudan", "Description", "/images/326554246_606384704828028_7636741843512573046_n.jpg", 41.110054283305445, 20.805640196713345));
            InsertMapMarker(new MapMarker("Emanuel", "Description", "/images/326554246_606384704828028_7636741843512573046_n.jpg", 41.113900756374626, 20.799741633820744));
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
        List<MapMarker> markers = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM PUBLIC.MAP_TRACKERS")) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String imageUrl = resultSet.getString("image_url");
                double xCoord = resultSet.getDouble("x_coord");
                double yCoord = resultSet.getDouble("y_coord");

                MapMarker mapMarker = new MapMarker(name, description, imageUrl, xCoord, yCoord);
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
