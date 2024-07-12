package app.exception;

public class CartaoNotFoundException extends RuntimeException {
    public CartaoNotFoundException(String message) {
        super(message);
    }
}
