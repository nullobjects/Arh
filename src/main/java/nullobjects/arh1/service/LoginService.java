package nullobjects.arh1.service;

import nullobjects.arh1.model.User;
import nullobjects.arh1.repository.LoginRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private LoginRepository loginRepository;
    LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public boolean RegisterUser(String username, String password) {
        if (username.length() < 5) {
            throw new IllegalArgumentException("The username has to be atleast 5 characters long");
        } else if (password.length() < 5) {
            throw new IllegalArgumentException("The password has to be atleast 5 characters long");
        }

        return loginRepository.RegisterUser(new User(username, password));
    }

    public boolean LoginUser(String username, String password) {
        return loginRepository.LoginUser(new User(username, password));
    }
}
