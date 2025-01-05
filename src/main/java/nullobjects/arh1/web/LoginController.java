package nullobjects.arh1.web;

import jakarta.servlet.http.HttpSession;
import nullobjects.arh1.model.User;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/login")
public class LoginController {
    private final LoginService loginService;
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
    public String LoginUser(@RequestParam String username, @RequestParam String password, HttpSession httpSession) {
        if (loginService.LoginUser(username, password)) {
            User user = loginService.GetUserByUserName(username);
            httpSession.setAttribute("user", user);
            return "redirect:/";
        }
        return "redirect:/login";
    }

    @PostMapping("/register_redirect")
    public String RegisterUser() {
        return "redirect:/login/register";
    }

    @GetMapping("/register")
    public String RegisterPage(Model model, RedirectAttributes redirectAttributes) {
        model.addAllAttributes(redirectAttributes.getFlashAttributes());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String confirm_password,
            RedirectAttributes redirectAttributes
    ) {
        if (!password.equals(confirm_password)) {
            redirectAttributes.addFlashAttribute("error", "Passwords do not match.");
            return "redirect:/login/register";
        }

        try {
            User user = loginService.RegisterUser(username, password);
            if (user != null) {
                return "redirect:/login";
            } else {
                redirectAttributes.addFlashAttribute("error", "Registration failed for an unknown reason.");
                return "redirect:/login/register";
            }
        } catch (UsernameTooShortException e) {
            redirectAttributes.addFlashAttribute("error", "Username is too short.");
        } catch (PasswordTooShortException e) {
            redirectAttributes.addFlashAttribute("error", "Password is too short.");
        } catch (UsernameExistsException e) {
            redirectAttributes.addFlashAttribute("error", "Username already exists.");
        }

        return "redirect:/login/register";
    }
}
