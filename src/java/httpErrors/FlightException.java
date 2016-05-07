/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpErrors;

import java.io.Serializable;

/**
 *
 * @author David
 */
public class FlightException extends Exception implements Serializable{

    private static final long serialVersionUID = 1L;
    
    int errorCode;
    int httpError;
    
    
    
    public FlightException(int code, int error) {
        super();
        errorCode = code;
        httpError = error;
    }

    public FlightException(int code,int error, String msg) {
        super(msg);
        errorCode = code;
        httpError = error;
    }
    public FlightException(int code,int error, String msg, Exception e)  {
        super(msg, e);
        errorCode = code;
        httpError = error;
    }

    public int getErrorCode() {
        return errorCode;
        
    }

    public int getHttpError() {
        return httpError;
    }
    
    
    
    
    
}
