package com.example.exercise3.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.exercise3.model.dto.AuthorDto;

public interface AccountService {
	AuthorDto createAuthor(String authorName);
	Page<AuthorDto> findAll(Pageable pageable);
	
}
