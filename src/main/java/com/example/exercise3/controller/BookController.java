package com.example.exercise3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.exercise3.model.Book;
import com.example.exercise3.service.BookService;

@RestController
@RequestMapping("/books") 
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@GetMapping 
	public List<Book> getBooks(@RequestParam String title) {
		return bookService.getAllBooks(); 
	}
}
