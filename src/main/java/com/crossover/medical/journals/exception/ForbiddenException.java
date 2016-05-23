package com.crossover.medical.journals.exception;

public class ForbiddenException extends RuntimeException {

    private static final long serialVersionUID = -1362881522229612970L;

    public ForbiddenException(String msg) {
        super(msg);
    }
}
