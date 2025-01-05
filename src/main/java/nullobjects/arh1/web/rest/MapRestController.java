package nullobjects.arh1.web.rest;

import nullobjects.arh1.model.Comment;
import nullobjects.arh1.model.MapMarker;
import nullobjects.arh1.service.MapService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MapRestController {
    private final MapService mapService;

    MapRestController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping("/api/GetMarkers")
    public List<MapMarker> GetMarkers() {
        return mapService.getAllMarkers();
    }

    @GetMapping("/api/GetMarker/{name}")
    public MapMarker GetMarker(@PathVariable String name) {
        return mapService.getMarker(name);
    }

    @GetMapping("/city")
    public List<MapMarker> searchMarkersByCity(@RequestParam String name) {
        return mapService.findMarkersByCity(name);
    }

    @GetMapping("/api/GetComments")
    public List<Comment> GetComments(@RequestParam String name) {
        return mapService.getComments(name);
    }
}
