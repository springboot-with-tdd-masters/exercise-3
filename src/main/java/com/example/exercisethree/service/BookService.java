package com.example.exercisethree.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.exercisethree.exception.RecordNotFoundException;
import com.example.exercisethree.model.Author;
import com.example.exercisethree.model.Book;
import com.example.exercisethree.response.AuthorResponse;
import com.example.exercisethree.response.BookResponse;

public interface BookService {
	
	AuthorResponse createAuthor(Author authorRequest) throws RecordNotFoundException;
	Page<AuthorResponse> getAllAuthors(Pageable page);
	AuthorResponse getAuthorById(Long id) throws RecordNotFoundException;
	BookResponse createBook(Long id, Book book) throws RecordNotFoundException;
	Page<BookResponse> getAllBooks(Long id, Pageable page) throws RecordNotFoundException;
	Page<BookResponse> getBookById(Long authorId, Long bookId, Pageable page) throws RecordNotFoundException;
	Page<BookResponse> getBookByTitle(String title, Pageable page);
	BookResponse updateBook(Long authorId, Long bookId, Book book) throws RecordNotFoundException;
	Book deleteByBookId(Long authorId, Long bookId) throws RecordNotFoundException;
	
}
