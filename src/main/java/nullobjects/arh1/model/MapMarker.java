package nullobjects.arh1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "MapMarkers")
public class MapMarker {
    @Id
    private String name; // Name of the marker
    private String description; // Description of the marker
    private String city; // City where the marker is located
    private String image_url; // URL of the marker's image
    private Double review; // Review rating of the marker
    private Integer working_start; // Start time of operation in hours
    private Integer working_end; // End time of operation in hours
    private Double x_coord; // X-coordinate of the marker
    private Double y_coord; // Y-coordinate of the marker
    @OneToMany
    private List<Comment> comments; // List of comments associated with the marker

    /**
     * Constructor to initialize a MapMarker object.
     * @param name Name of the marker
     * @param description Description of the marker
     * @param city City where the marker is located
     * @param image_url URL of the marker's image
     * @param review Review rating of the marker
     * @param working_start Start time of operation in hours
     * @param working_end End time of operation in hours
     * @param x_coord X-coordinate of the marker
     * @param y_coord Y-coordinate of the marker
     */
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
        this.comments = new ArrayList<>();
    }

    public MapMarker(String name, String description, String city, String image_url, Integer working_start, Integer working_end, Double x_coord, Double y_coord) {
        this.name = name;
        this.description = description;
        this.city = city;
        this.image_url = image_url;
        this.review = 0.;
        this.working_start = working_start;
        this.working_end = working_end;
        this.x_coord = x_coord;
        this.y_coord = y_coord;
        this.comments = new ArrayList<>();
    }

    public MapMarker() {

    }
}
