package com.example.exercisethree.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.exercisethree.exception.RecordNotFoundException;
import com.example.exercisethree.model.Author;
import com.example.exercisethree.model.Book;
import com.example.exercisethree.repository.AuthorRepository;
import com.example.exercisethree.repository.BookRepository;
import com.example.exercisethree.response.AuthorResponse;
import com.example.exercisethree.response.BookResponse;
import com.example.exercisethree.service.BookService;

@Service
public class BookServiceImpl implements BookService{
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	AuthorRepository authorRepository;
	
	@Override
	public AuthorResponse createAuthor(Author authorRequest) throws RecordNotFoundException {
		Author authorEntity = new Author();
		if (authorRequest.getName() != null) {
			authorEntity.setName(authorRequest.getName());
			authorRepository.save(authorEntity);
			
			return AuthorResponse.convertToAuthorResponse(authorEntity);
		} else {
			throw new RecordNotFoundException("There is no record with that ID");
		}
		
	}

	@Override
	public Page<AuthorResponse> getAllAuthors(Pageable page) {
		return authorRepository.findAll(page).map(AuthorResponse::convertToAuthorResponse);
	}

	@Override
	public AuthorResponse getAuthorById(Long id) throws RecordNotFoundException {
		Optional<Author> author = authorRepository.findById(id);
		
		if (author.isPresent()) {
			return AuthorResponse.convertToAuthorResponse(author.get());
		} else {
			throw new RecordNotFoundException("There is no record with that ID");
		}
	}

	@Override
	public BookResponse createBook(Long id, Book book) throws RecordNotFoundException {
		Optional<Author> author = authorRepository.findById(id);
		
		if (author.isPresent()) {
			Book newBook = new Book();
			newBook.setTitle(book.getTitle());
			newBook.setDescription(book.getDescription());
			newBook.setAuthor(author.get());
			bookRepository.save(newBook);
			return BookResponse.convertToBookResponse(newBook);
		} else {
			throw new RecordNotFoundException("There is no record with that ID");
		}
	}

	@Override
	public Page<BookResponse> getAllBooks(Long id, Pageable page) throws RecordNotFoundException {
		Optional<Author> author = authorRepository.findById(id);
		
		if (author.isPresent()) {
			return bookRepository.findByAuthorId(id, page).map(BookResponse::convertToBookResponse);
		} else {
			throw new RecordNotFoundException("There is no record with that ID");
		}
	}

	@Override
	public Page<BookResponse> getBookById(Long authorId, Long bookId, Pageable page) throws RecordNotFoundException {
		Optional<Author> author = authorRepository.findById(authorId);
		
		if (author.isPresent()) {
			return bookRepository.findByAuthorIdAndId(authorId, bookId, page).map(BookResponse::convertToBookResponse);
		} else {
			throw new RecordNotFoundException("There is no record with that ID");
		}
	}

	@Override
	public Page<BookResponse> getBookByTitle(String title, Pageable page) {
		return bookRepository.findByTitleContaining(title, page).map(BookResponse::convertToBookResponse);
	}

	@Override
	public BookResponse updateBook(Long authorId, Long bookId, Book book) throws RecordNotFoundException {
		Optional<Author> author = authorRepository.findById(authorId);
		
		if (author.isPresent()) {
			Optional<Book> bookEntity = bookRepository.findById(bookId);
			Book newBook = bookEntity.get();
			newBook.setTitle(book.getTitle());
			newBook.setDescription(book.getDescription());
			bookRepository.save(newBook);
			return BookResponse.convertToBookResponse(newBook);
		} else {
			throw new RecordNotFoundException("There is no record with that ID");
		}
	}

	public Book deleteByBookId(Long authorId, Long bookId) throws RecordNotFoundException {
		Optional<Book> book = bookRepository.findById(bookId);
		
		if (book.isPresent()) {
			bookRepository.deleteById(bookId);
			return book.get();
		} else {
			throw new RecordNotFoundException("There is no record with that ID");
		}
	}
	
}
