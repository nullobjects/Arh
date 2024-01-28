package nullobjects.arh1.service;

import nullobjects.arh1.model.User;
import nullobjects.arh1.model.exceptions.PasswordTooShortException;
import nullobjects.arh1.model.exceptions.UsernameExistsException;
import nullobjects.arh1.model.exceptions.UsernameTooShortException;
import nullobjects.arh1.repository.LoginRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {
    private final LoginRepository loginRepository;

    LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    // Register a new user
    public boolean RegisterUser(String username, String password) throws UsernameTooShortException, PasswordTooShortException, UsernameExistsException {
        if (username.length() < 5) {
            throw new UsernameTooShortException();
        } else if (password.length() < 5) {
            throw new PasswordTooShortException();
        }

        return loginRepository.RegisterUser(new User(username, password));
    }

    // Login a user
    public boolean LoginUser(String username, String password) {
        return loginRepository.LoginUser(new User(username, password));
    }

    // Retrieve a user by username
    public User GetUserByUserName(String username) {
        return loginRepository.GetUserByUserName(username);
    }

    // Get all users
    public List<User> getUsers(){
        return loginRepository.getUsers();
    }
}
