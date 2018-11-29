package Exceptions;

import javax.mail.MessagingException;

public class SenderException extends Exception {
    public SenderException (String message) {
        super(message);
    }

    public SenderException (String message, MessagingException ex) {
        super(message, ex);
    }
}
