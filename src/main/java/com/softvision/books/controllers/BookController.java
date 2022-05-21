package com.softvision.books.controllers;

import com.softvision.books.services.BookService;
import com.softvision.books.services.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rest/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity getAll() {

        final List<Book> books = bookService.findAll();

        return ResponseEntity
                .ok(books);
    }

    @PostMapping
    public ResponseEntity<Book> save(@RequestBody Book book) {

        final Book savedBook = bookService.save(book);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedBook);
    }

}
