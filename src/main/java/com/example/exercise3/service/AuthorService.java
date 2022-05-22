package com.example.exercise3.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.exercise3.model.dto.AuthorDto;

public interface AuthorService {
	AuthorDto createAuthor(String authorName);
	AuthorDto getAuthor(Long id);
	Page<AuthorDto> findAll(Pageable pageable);
}
