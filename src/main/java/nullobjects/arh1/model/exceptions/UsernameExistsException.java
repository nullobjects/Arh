package nullobjects.arh1.model.exceptions;

public class UsernameExistsException extends RuntimeException {
    public UsernameExistsException() {
        super("This username already exists!");
    }
}