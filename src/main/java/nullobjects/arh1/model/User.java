package nullobjects.arh1.model;
import lombok.Data;

@Data
public class User {
    // Username of the user
    private String username;

    // Password of the user
    private String password;

    /**
     * Constructor to create a new User object with the specified username and password.
     * @param username The username of the user.
     * @param password The password of the user.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
