package com.example.exercise3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.exercise3.model.Book;
import com.example.exercise3.service.AuthorServiceImpl;

@RestController
@RequestMapping("/authors") 
public class AuthorController {
	
	@Autowired
	private AuthorServiceImpl authorService;
	
	@PostMapping
	public Book addAuthor(@RequestBody Book book) {
		return authorService.addBook(book);
	}
	
	@GetMapping public List<Book> getBooks() {
		return authorService.getAllBooks(); 
	}
	
	@GetMapping("/{id}")
	public Book getBook(@PathVariable long id) {
		return authorService.getBookById(id);
	}
	
	@PostMapping("/update")
	public Book updateBook(@RequestBody Book book) {
		return authorService.updateBook(book);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteBook(@PathVariable Long id) {
		authorService.deleteBook(id);
	}
}
