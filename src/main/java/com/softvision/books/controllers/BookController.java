package com.softvision.books.controllers;

import com.softvision.books.services.BookService;
import com.softvision.books.services.domain.Book;
import com.softvision.books.services.domain.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rest/authors/{authorId}/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public Pagination<Book> getAll(@PathVariable("authorId") Long authorId, Pageable pageable) {
        return bookService.findAll(authorId, pageable);
    }

    @GetMapping("{id:\\d+}")
    public ResponseEntity<Book> get(@PathVariable Long id) {

        final Book book = bookService.findById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(book);
    }

    @PostMapping
    public ResponseEntity<Book> create(@PathVariable("authorId") Long authorId, @RequestBody Book book) {

        final Book savedBook = bookService.add(authorId, book);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedBook);
    }

    @PutMapping("{id:\\d+}")
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book book) {

        final Book savedBook = bookService.update(id, book);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(savedBook);
    }

    @DeleteMapping("{id:\\d+}")
    public void delete(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
