package com.java.master.exercise3.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.java.master.exercise3.model.Author;

public interface AuthorService {

	Author create(Author author);
	
	Page<Author> getAuthors(Pageable pageable);
	
	Author getAuthorById(Long id);
}
