package nullobjects.arh1.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MapController {
    @GetMapping("/")
    public ModelAndView Kucesko() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main_page"); // Set the view name (HTML file name without extension)
        return modelAndView;
    }
}
