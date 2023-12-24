package nullobjects.arh1.web;

import nullobjects.arh1.model.exceptions.PasswordTooShortException;
import nullobjects.arh1.model.exceptions.UsernameExistsException;
import nullobjects.arh1.model.exceptions.UsernameTooShortException;
import nullobjects.arh1.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @PostMapping
    public String LoginUser(@RequestParam String username, @RequestParam String password) {
        if (loginService.LoginUser(username, password)) {
            return "redirect:/";
        }
        return "redirect:/login";
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
    public String RegisterUser(@RequestParam String username, @RequestParam String password, @RequestParam String confirm_password, Model model) {
        if (!password.equals(confirm_password)) {
            return "redirect:/login/register";
        }

        try {
            boolean good = loginService.RegisterUser(username, password);
            if (good) {
                return "redirect:/login";
            } else {
                return "redirect:/login/register";
            }
        } catch (UsernameTooShortException e) {
            model.addAttribute("error", "Username is too short.");
            return "redirect:/login/register";
        } catch (PasswordTooShortException e) {
            model.addAttribute("error", "Password is too short.");
            return "redirect:/login/register";
        } catch (UsernameExistsException e) {
            model.addAttribute("error", "Username already exists.");
            return "redirect:/login/register";
        }
    }
}
