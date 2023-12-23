package nullobjects.arh1.web;

import nullobjects.arh1.model.MapMarker;
import nullobjects.arh1.service.MapService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import nullobjects.arh1.repository.MapRepository;

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

    @GetMapping("/add-gal")
    public String addGal(Model model){
        model.addAttribute("marker",mapService.getAllMarkers());
        return "add-gal";
    }

    @GetMapping("/edit/{name}")
    public String edit(@PathVariable String name, Model model){
        if(mapService.findMarkerByName(name) != null){
            MapMarker mapMarker = mapService.findMarkerByName(name);
            model.addAttribute("mapName",mapMarker.getName());
            model.addAttribute("marker",mapService.getAllMarkers());
            return "edit-gal";
        }
        return "error";
    }

    @GetMapping("/search")
    public MapMarker searchMarkersByName(@RequestParam String name) {
        return mapService.findMarkerByName(name);
    }
}
