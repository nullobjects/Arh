package nullobjects.arh1.web;

import nullobjects.arh1.model.MapMarker;
import nullobjects.arh1.service.MapService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import nullobjects.arh1.repository.MapRepository;

import java.util.List;

@Controller
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
    @ResponseBody
    public List<MapMarker> GetMarkers() {
        return mapService.getAllMarkers();
    }

    @GetMapping("/add_gal")
    public String addGal(Model model){
        //model.addAttribute("marker",mapService.getAllMarkers());
        return "add_gal";
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

    @GetMapping("/delete_gal/{name}")
    public String deleteGal(@PathVariable String name,Model model){
        mapService.delete(name);
        model.addAttribute("marker",mapService.getAllMarkers());
        return "delete_gal";
    }

    @GetMapping("/search")
    @ResponseBody
    public MapMarker searchMarkersByName(@RequestParam String name) {
        return mapService.findMarkerByName(name);
    }

    @GetMapping("/city")
    @ResponseBody
    public MapMarker searchMarkersByCity(@RequestParam String city){ return mapService.findMarkerByCity(city);}
}
