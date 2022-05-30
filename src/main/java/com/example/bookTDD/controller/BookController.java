package com.example.bookTDD.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.bookTDD.exception.RecordNotFoundException;
import com.example.bookTDD.model.Book;
import com.example.bookTDD.service.BookService;

@Controller
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	BookService bookService;
	
	@GetMapping
	public ResponseEntity<List<Book>> getAllBooks() {
		List<Book> list = bookService.getAllBooks();
		
		return new ResponseEntity<List<Book>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Book> createOrUpdateBook(@RequestBody Book books) throws RecordNotFoundException {
		Book updated = bookService.createOrUpdateBook(books);

		return new ResponseEntity<Book>(updated, new HttpHeaders(), HttpStatus.CREATED);
	}


}
