package com.softvision.library.tdd.controller;

import com.softvision.library.tdd.model.Book;
import com.softvision.library.tdd.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookService bookService;

    public ResponseEntity<List<Book>> getAll() {
        List<Book> result = bookService.getAll();
        if (result == null || result.isEmpty()) {
            return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> get(@PathVariable long id) {
        Book result = bookService.getById(id);
        if (result == null) {
            return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        Book result = bookService.createOrUpdate(book);
        return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Book>> getAll(@PageableDefault Pageable page) {
        Page<Book> result = bookService.getAll(page);
        if (result.isEmpty()) {
            return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Long> delete(@PathVariable long id) {
        bookService.delete(id);
        return new ResponseEntity<>(id, new HttpHeaders(), HttpStatus.OK);
    }
}
