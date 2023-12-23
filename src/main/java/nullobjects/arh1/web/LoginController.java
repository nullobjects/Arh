package nullobjects.arh1.web;

import nullobjects.arh1.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login")
public class LoginController {
    private LoginService loginService;
    LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public ModelAndView LoginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping("/register_redirect")
    public String RegisterUser() {
        return "redirect:/login/register";
    }

    @GetMapping("/register")
    public ModelAndView RegisterPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping("/register")
    public String RegisterUser(@RequestParam String username, @RequestParam String password, @RequestParam String confirm_password) {
        loginService.RegisterUser(username, password);
        return "redirect:/register/login";
    }
}
