package nullobjects.arh1.service;

import nullobjects.arh1.model.User;
import nullobjects.arh1.model.exceptions.PasswordTooShortException;
import nullobjects.arh1.model.exceptions.UsernameExistsException;
import nullobjects.arh1.model.exceptions.UsernameTooShortException;
import nullobjects.arh1.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final UserRepository userRepository;

    LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Register a new user
    public User RegisterUser(String username, String password) throws UsernameTooShortException, PasswordTooShortException, UsernameExistsException {
        if (username.length() < 5) {
            throw new UsernameTooShortException();
        } else if (password.length() < 5) {
            throw new PasswordTooShortException();
        }

        if (userRepository.findById(username).isPresent()) {
            throw new UsernameExistsException();
        }

        return userRepository.save(new User(username, password));
    }

    // Login a user
    public boolean LoginUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password) != null;
    }

    // Retrieve a user by username
    public User GetUserByUserName(String username) {
        return userRepository.findById(username).orElse(null);
    }
}
