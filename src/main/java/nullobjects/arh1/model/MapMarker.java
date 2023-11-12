package nullobjects.arh1.model;

import lombok.Data;

@Data
public class MapMarker {
    private String name;
    private String description;
    private String image_url;
    private Double x_coord;
    private Double y_coord;

    public MapMarker(String name, String description, String image_url, Double x_coord, Double y_coord) {
        this.name = name;
        this.description = description;
        this.image_url = image_url;
        this.x_coord = x_coord;
        this.y_coord = y_coord;
    }
}
