package com.brownbag.exercise3.errorhandler;

public class BookNotFoundException extends Exception {

    public BookNotFoundException() {
        super("Book Not Found Exception.");
    }
}
