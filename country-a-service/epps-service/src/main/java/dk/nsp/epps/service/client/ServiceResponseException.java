package dk.nsp.epps.service.client;

public class ServiceResponseException extends Exception {
    private final int statusCode;
    private final String body;

    ServiceResponseException(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
