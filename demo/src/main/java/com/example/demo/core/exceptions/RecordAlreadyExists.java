package com.example.demo.core.exceptions;

public class RecordAlreadyExists  extends RuntimeException {

    public RecordAlreadyExists(String message) {
        super(message);
    }
}