package team.themoment.hellogsmv3.global.exception.error;

import org.springframework.http.HttpStatus;

public class HelloException extends RuntimeException {

    private final HttpStatus statusCode;

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public HelloException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HelloException(HttpStatus statusCode) {
        this(statusCode.getReasonPhrase(), statusCode);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

}