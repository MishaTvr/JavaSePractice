package exceptions;

import java.util.concurrent.ExecutionException;

public class OrchestratorException extends Exception {

    public OrchestratorException (String message) {super(message);}

    public OrchestratorException (String message, ExecutionException ex) {
        super(message, ex);
    }

    public OrchestratorException (Exception ex) {
        super(ex);
    }
}
