package com.example.exercise3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.exercise3.model.dto.BookDto;
import com.example.exercise3.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookRepository bookRepository;

	public Page<BookDto> getBooks(String title) {
	 	Pageable pageable = PageRequest.of(0, 20);
		return bookRepository.findByTitleContains(title, pageable).map(BookDto::convertToDto);
	}

	@Override
	public Page<BookDto> getBooks(Long authorId, Pageable pageable) {
		return bookRepository.findByAuthorId(authorId, pageable).map(BookDto::convertToDto);
	}

	public Page<BookDto> getBook(Long bookId, Long authorId, Pageable pageable) {
		return bookRepository.findByIdAndAuthorId(bookId, bookId, pageable).map(BookDto::convertToDto);
	}	
}
