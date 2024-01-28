package nullobjects.arh1.service;

import nullobjects.arh1.model.MapMarker;
import nullobjects.arh1.repository.MapRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapService {
    private final MapRepository mapRepository;

    MapService(MapRepository mapRepository) {
        this.mapRepository = mapRepository;
    }

    // Get all markers
    public List<MapMarker> getAllMarkers() {
        return mapRepository.getAllMarkers();
    }

    // Get a marker
    public MapMarker getMarker(String name) {
        return mapRepository.getMarker(name);
    }

    // Find a marker by name
    public MapMarker findMarkerByName(String name) {
        return mapRepository.findMarkerByName(name);
    }

    // Delete a marker by name
    public boolean delete(String name) {
        return mapRepository.delete(name);
    }

    // Find a marker by city
    public MapMarker findMarkerByCity(String city) {
        return mapRepository.findMarkerByCity(city);
    }

    // Add a new marker
    public void add(String name, String disc, String city, String image, int start, int end, double x, double y) {
        mapRepository.add(name, disc, city, image, start, end, x, y);
    }

    // Add a comment to a marker
    public void addComment(String name, String username, String comment) {
        mapRepository.InsertMarkerComment(name, username, comment);
    }

    // Get comments for a marker
    public List<String> getComments(String name) {
        return mapRepository.GetMarkerComments(name);
    }

    // Find a specific comment from a marker with given text
    public String getComment(String markerName, String commentText) {
        return mapRepository.GetMarkerComment(markerName, commentText);
    }
}
