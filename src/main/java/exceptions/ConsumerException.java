package exceptions;

public class ConsumerException extends Exception {
    public ConsumerException(String message) {super(message);}

    public ConsumerException(String message, InterruptedException ex) {
        super(message, ex);
    }

    public ConsumerException(String message, SenderException ex) {
        super(message, ex);
    }

    public ConsumerException(InterruptedException ex) {super(ex);}
}