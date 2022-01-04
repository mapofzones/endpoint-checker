package com.mapofzones.endpointchecker.common.exceptions;

public class EndpointCheckerException extends BaseException {

    protected EndpointCheckerException(String message) {
        super(message);
    }

    public EndpointCheckerException(String message, Throwable cause) {
        super(message, cause);
    }
}
