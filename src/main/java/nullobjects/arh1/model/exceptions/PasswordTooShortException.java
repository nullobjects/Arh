package nullobjects.arh1.model.exceptions;

public class PasswordTooShortException extends RuntimeException {
    public PasswordTooShortException() {
        super("The password has to be atleast 5 characters long!");
    }
}
