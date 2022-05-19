package com.brownbag.exercise3.errorhandler;

public class AuthorNotFoundException extends Exception {

    public AuthorNotFoundException() {
        super("Author Not Found Exception.");
    }
}
