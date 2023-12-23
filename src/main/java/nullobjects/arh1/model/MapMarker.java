package nullobjects.arh1.model;

import lombok.Data;

@Data
public class MapMarker {
    private String name;
    private String description;
    private String city;
    private String image_url;
    private Double review;
    private Integer working_start; // Working start in minutes (if it starts in 9am the value of this will be 9*60)
    private Integer working_end; // Working end in minutes (if it ends in 9pm the value of this will be 21*60)
    private Double x_coord;
    private Double y_coord;

    public MapMarker(String name, String description, String city, String image_url, Double review, Integer working_start, Integer working_end, Double x_coord, Double y_coord) {
        this.name = name;
        this.description = description;
        this.city = city;
        this.image_url = image_url;
        this.review = review;
        this.working_start = working_start;
        this.working_end = working_end;
        this.x_coord = x_coord;
        this.y_coord = y_coord;
    }
}
