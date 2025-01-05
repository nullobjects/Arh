package nullobjects.arh1.service;

import nullobjects.arh1.model.Comment;
import nullobjects.arh1.model.MapMarker;
import nullobjects.arh1.model.User;
import nullobjects.arh1.repository.CommentRepository;
import nullobjects.arh1.repository.MapRepository;
import nullobjects.arh1.repository.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapService {
    private final MapRepository mapRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    MapService(MapRepository mapRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.mapRepository = mapRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    // Get all markers
    public List<MapMarker> getAllMarkers() {
        return mapRepository.findAll();
    }

    // Get a marker
    public MapMarker getMarker(String name) {
        return mapRepository.findById(name).orElse(null);
    }

    // Delete a marker by name
    public boolean delete(String name) {
        try {
            mapRepository.deleteById(name);
            return true;
        } catch (EmptyResultDataAccessException e) {
            // The marker doesn't exist
            return false;
        } catch (Exception e) {
            // Other issues, such as database connection issues
            return false;
        }
    }

    // Find a marker by city
    public List<MapMarker> findMarkersByCity(String city) {
        return mapRepository.findAllByCity(city);
    }

    // Add a new marker
    public void add(String name, String disc, String city, String image, int start, int end, double x, double y) {
        mapRepository.save(new MapMarker(name, disc, city, image, start, end, x, y));
    }

    // Add a comment to a marker
    public void addComment(String marker_name, String username, String content) {
        User user = userRepository.findById(username).orElse(null);

        if (user != null) {
            Comment comment = new Comment(user, content);
            commentRepository.save(comment);

            MapMarker marker = getMarker(marker_name);
            marker.getComments().add(comment);
            mapRepository.save(marker);
        } else {
            // Username doesn't exist //
            throw new RuntimeException("User not found");
        }
    }

    // Get comments for a marker
    public List<Comment> getComments(String name) {
        MapMarker marker = getMarker(name);
        if (marker != null) {
            return marker.getComments();
        }
        return null;
    }
}
