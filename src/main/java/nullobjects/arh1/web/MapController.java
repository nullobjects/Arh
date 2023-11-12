package nullobjects.arh1.web;

import nullobjects.arh1.model.MapMarker;
import nullobjects.arh1.service.MapService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class MapController {
    private MapService mapService;

    MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping("/")
    public ModelAndView MainPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main_page");
        return modelAndView;
    }

    @GetMapping("/api/GetMarkers")
    public List<MapMarker> GetMarkers() {
        List<MapMarker> mapMarkers = mapService.getAllMarkers();
        return mapMarkers;
    }
}
