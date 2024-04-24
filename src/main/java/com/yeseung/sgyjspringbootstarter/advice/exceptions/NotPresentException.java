package com.yeseung.sgyjspringbootstarter.advice.exceptions;

public class NotPresentException extends RuntimeException {

    public NotPresentException(String message) {
        super(message);
    }
}
