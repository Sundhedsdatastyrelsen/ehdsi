package dk.sundhedsdatastyrelsen.ncpeh.jobqueue;

public class JobQueueException extends RuntimeException {
    public JobQueueException(String message) {
        super(message);
    }

    public JobQueueException(String message, Throwable cause) {
        super(message, cause);
    }
}
