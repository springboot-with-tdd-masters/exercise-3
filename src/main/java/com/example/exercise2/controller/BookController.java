package com.example.exercise2.controller;

import com.example.exercise2.service.BookService;
import com.example.exercise2.service.model.Book;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

  @Autowired
  private BookService bookService;

  @PostMapping()
  public ResponseEntity<Book> save(@RequestBody Book bookRequest) {
    return ResponseEntity.ok().body(bookService.save(bookRequest));
  }

  @GetMapping()
  public ResponseEntity<List<Book>> getAll() {
    return ResponseEntity.ok().body(bookService.findAll());
  }

  @ExceptionHandler
  public ResponseEntity handleException(RuntimeException e) {
    return ResponseEntity.internalServerError().build();
  }

  @GetMapping("/test")
  public ResponseEntity<String> get() {
    return ResponseEntity.ok().body("SUCCESS");
  }

}
