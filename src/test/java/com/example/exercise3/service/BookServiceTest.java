package com.example.exercise3.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.exercise3.model.Author;
import com.example.exercise3.model.Book;
import com.example.exercise3.model.dto.BookDto;
import com.example.exercise3.repository.BookRepository;

public class BookServiceTest {
	
	@Mock
	private BookRepository bookRepository;
	
	@InjectMocks
	private BookServiceImpl bookService;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void getBooksWithPagingAndSorting() {
		Author newAuthor = new Author();
	    newAuthor.setName("J. K. Rowling");
	     
	    Book book1 = new Book();
	    book1.setTitle("Harry Potter and the Sorcerer's Stone");
	    book1.setAuthor(newAuthor);

	    Book book2 = new Book();
	    book2.setTitle("Harry Potter and the Chamber of Secrets");
	    book2.setAuthor(newAuthor);
	     
		Page<Book> books = new PageImpl(Arrays.asList(book1, book2));

		Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "title"));

	 	when(bookRepository.findByAuthorId(1L, pageable))
	 		.thenReturn(books);

	    Page<BookDto> pagedBooks = bookService.getBooks(1L, pageable);
	    
	 	assertAll(
			   () -> assertEquals(2, pagedBooks.getNumberOfElements()),
			   () -> assertEquals("Harry Potter and the Sorcerer's Stone", pagedBooks.getContent().get(0).getTitle()),			    
			   () -> assertEquals("Harry Potter and the Chamber of Secrets", pagedBooks.getContent().get(1).getTitle())	   
	 		);		
	}
	
	@Test
	public void getBookWithCorrectDetails() {
		Author newAuthor = new Author();
	    newAuthor.setName("J. K. Rowling");
	     
	    Book book1 = new Book();
	    book1.setId(1L);
	    book1.setTitle("Harry Potter and the Sorcerer's Stone");
	    book1.setCreatedDate(new Date());
	    book1.setAuthor(newAuthor);
	     
		Page<Book> books = new PageImpl(Arrays.asList(book1));

		Pageable pageable = PageRequest.of(0, 20);

		when(bookRepository.findByIdAndAuthorId(1L, 1L, pageable))
			.thenReturn(books);
		
	 	Page<BookDto> pagedBook = bookService.getBook(1L, 1L, pageable);
	    
	 	BookDto book = pagedBook.getContent().get(0);
	 	assertAll(
			   () -> assertNotNull(book),
			   () -> assertNotNull(book.getTitle()),
			   () -> assertNotNull(book.getCreatedDate())		    
	 	);
	}
	
	
	@Test
	public void getBooksByTitle() {
		Author author = new Author();
		author.setId(1L);
		author.setName("J. K. Rowling");
		
		Book book1 = new Book();
	    book1.setTitle("Harry Potter and the Sorcerer's Stone");
	    book1.setCreatedDate(new Date());
	    book1.setAuthor(author);
	   
	    Book book2 = new Book();
	    book2.setTitle("Harry Potter and the Chamber of Secrets");
	    book2.setCreatedDate(new Date());
	    book2.setAuthor(author);

		Page<Book> books = new PageImpl(Arrays.asList(book1, book2));
		
	 	Pageable pageable = PageRequest.of(0, 20);

		when(bookRepository.findByTitleContains("Harry Potter", pageable))
			.thenReturn(books);
		
		Page<BookDto> pagedBooks = bookService.getBooks("Harry Potter");
		
		List<BookDto> bookList = pagedBooks.getContent();
		
		assertEquals(2, pagedBooks.getNumberOfElements());
		
	 	assertAll(
			() -> assertNotNull(bookList.get(0)),
			() -> assertNotNull(bookList.get(0).getTitle()),
			() -> assertNotNull(bookList.get(0).getCreatedDate())		    
	 	);		
	 	
	 	assertAll(
			() -> assertNotNull(bookList.get(1)),
			() -> assertNotNull(bookList.get(1).getTitle()),
			() -> assertNotNull(bookList.get(1).getCreatedDate())		    
		 );	
	}
}
