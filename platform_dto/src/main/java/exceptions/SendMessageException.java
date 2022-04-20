package exceptions;

public class SendMessageException extends RuntimeException {
    public SendMessageException(String message) {
        super(message);
    }
}
