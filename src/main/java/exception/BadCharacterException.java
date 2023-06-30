package exception;

public class BadCharacterException extends IllegalArgumentException {

    public BadCharacterException(String message) {
        super(message);
    }

}
