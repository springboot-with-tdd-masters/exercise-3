package com.example.exercise3.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.exercise3.model.dto.BookDto;

public interface BookService {
	
	 Page<BookDto> getBook(Long bookId, Long authorId, Pageable pageable);
	 Page<BookDto> getBooks(String string);
	 Page<BookDto> getBooks(Long authorId, Pageable pageable);

}
