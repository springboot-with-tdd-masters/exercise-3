package com.example.exercise3.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.hibernate.transform.ToListResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.exercise3.exception.AuthorNotFoundException;
import com.example.exercise3.exception.BookNotFoundException;
import com.example.exercise3.model.Author;
import com.example.exercise3.model.Book;
import com.example.exercise3.model.dto.AuthorDto;
import com.example.exercise3.repository.AuthorRepository;
import com.example.exercise3.repository.BookRepository;

import ch.qos.logback.core.pattern.Converter;

@Service
public class AuthorServiceImpl implements AuthorService{
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	public AuthorDto createAuthor(String authorName) {
		Author author = new Author();
		author.setName(authorName);
		
		Author savedAuthor = authorRepository.save(author);
		
		return AuthorDto.convertToDto(savedAuthor);
	}
	

	public AuthorDto getAuthor(Long id) {
		return authorRepository.findById(id)
				.map(AuthorDto::convertToDto)
				.orElseThrow(AuthorNotFoundException::new);
	}

	public Page<AuthorDto> findAll(Pageable pageable) {
		return authorRepository.findAll(pageable).map(AuthorDto::convertToDto);
	}
	
	public List<Book> getAllBooks() { 
		return bookRepository.findAll(); 
	}
	
	public Book getBookById(Long id) {
		return bookRepository.findById(id)
				.orElseThrow(BookNotFoundException::new);
	}
	
	public Book addBook(Book book) {
		return bookRepository.save(book);
	}
	
	public Book updateBook(Book book) {
		Book toUpdate = getBookById(book.getId());
		toUpdate.setTitle(book.getTitle());
		toUpdate.setAuthor(book.getAuthor());
		return bookRepository.save(toUpdate);
	}
	
	public void deleteBook(Long id) {
		Book toDelete = getBookById(id);
		bookRepository.delete(toDelete);
	}
}
