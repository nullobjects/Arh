package nullobjects.arh1.model.exceptions;

public class UsernameTooShortException extends RuntimeException {
    public UsernameTooShortException() {
        super("The username has to be atleast 5 characters long!");
    }
}