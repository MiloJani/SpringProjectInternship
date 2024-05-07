package com.example.demo.core.exceptions;

public class EmployeeIsAlreadyInProject extends RuntimeException {

    public EmployeeIsAlreadyInProject(String message)  {
        super(message);
    }
}
