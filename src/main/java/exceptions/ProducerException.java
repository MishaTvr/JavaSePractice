package exceptions;

public class ProducerException extends Exception {
    public ProducerException(String message) {super(message);}

    public ProducerException(String message, InterruptedException ex) {
        super(message, ex);
    }
    public ProducerException(InterruptedException ex) {super(ex);}
}
