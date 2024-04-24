package com.yeseung.sgyjspringbootstarter.advice.exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

}
