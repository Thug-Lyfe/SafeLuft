package httpErrors;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorMessage {

    public ErrorMessage(Throwable ex, int code, int error) {
        this.httpError = error;
        this.errorCode = code;
        switch (errorCode) {
            case 1:
                message = "No flights found.";
                break;
            case 2:
                message = "No available tickets.";
                break;
            case 3:
                message = "Illegal input, change your input and try again.";
                break;
            case 4:
                message = "The fuck? Have you tried turning it off and on again?";
                break;
            default:
                message = "Error: No error message";
        }

    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getHttpError() {
        return httpError;
    }

    public void setHttpError(int httpError) {
        this.httpError = httpError;
    }

    public String getMessage() {
        
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private int errorCode;
    private String message;
    private int httpError;
}
