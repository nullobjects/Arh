package nullobjects.arh1.web;

import jakarta.servlet.http.HttpSession;
import nullobjects.arh1.model.User;
import nullobjects.arh1.service.LoginService;
import nullobjects.arh1.service.MapService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MapController {
    private final MapService mapService;

    MapController(MapService mapService, LoginService loginService) {
        this.mapService = mapService;
    }

    @GetMapping("/")
    public ModelAndView MainPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main_page");
        return modelAndView;
    }

    @GetMapping("/add_gal")
    public String addGal(Model model){
        model.addAttribute("marker",mapService.getAllMarkers());
        return "add_gal";
    }

    @PostMapping("/add")
    public String save(@RequestParam String name,
                       @RequestParam String desc,
                       @RequestParam String city,
                       @RequestParam String image,
                       @RequestParam int start,
                       @RequestParam int end,
                       @RequestParam double x_coord,
                       @RequestParam double y_coord) {
        mapService.add(name, desc, city, image, start, end, x_coord, y_coord);
        return "redirect:/";
    }


    @GetMapping("/delete_gal")
    public String DeletePage(Model model) {
        model.addAttribute("markers", mapService.getAllMarkers());
        return "delete_gal";
    }

    @PostMapping("/delete_gal")
    public String deleteGal(@RequestParam String name){
        mapService.delete(name);
        return "redirect:/";
    }

    @PostMapping("/add_comment")
    public String AddComment(@RequestParam String name, @RequestParam String comment, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (user != null) {
            mapService.addComment(name, user.getUsername(), comment);
        }
        return "redirect:/?0";
    }
}
