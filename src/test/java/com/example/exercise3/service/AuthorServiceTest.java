package com.example.exercise3.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.exercise3.exception.AuthorNotFoundException;
import com.example.exercise3.model.Author;
import com.example.exercise3.model.dto.AuthorDto;
import com.example.exercise3.repository.AuthorRepository;

public class AuthorServiceTest {

	@Mock
	private AuthorRepository authorRepository;
	
	@InjectMocks
	private AuthorServiceImpl authorService;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	@DisplayName("Should save Author and return details")
	public void shouldSaveAuthor() {
		String authorName = "JK Rowling";
		
		Author newAuthor = new Author();
		newAuthor.setId(1L);
		newAuthor.setName(authorName);
		
		Date today = new Date();
		newAuthor.setCreatedDate(today);		
		newAuthor.setUpdateDate(today);

		when(authorRepository.save(any(Author.class)))
			.thenReturn(newAuthor);
		
		AuthorDto author = authorService.createAuthor(authorName);
		
		assertAll(
			    () -> assertEquals(newAuthor.getCreatedDate(), author.getCreateDate()),
	            () -> assertEquals(newAuthor.getCreatedDate(), author.getCreateDate()),	            
				() -> assertEquals(newAuthor.getId(), author.getId()),
	            () -> assertEquals(newAuthor.getName(), author.getName())
	        );		
	}
	
	@Test
	@DisplayName("Should return author with correct details")
	public void shouldReturnAuthorWithCorrectDetails() {
		Author author = new Author();
		author.setId(1L);
		author.setName("JK Rowling");
		
		Date today = new Date();
		author.setCreatedDate(today);		
		author.setUpdateDate(today);

		when(authorRepository.findById(1L))
			.thenReturn(Optional.of(author));
		
		AuthorDto actualResponse = authorService.getAuthor(1L);
		
		assertAll(
			    () -> assertEquals(author.getCreatedDate(), actualResponse.getCreateDate()),
	            () -> assertEquals(author.getCreatedDate(), actualResponse.getCreateDate()),	            
				() -> assertEquals(author.getId(), actualResponse.getId()),
	            () -> assertEquals(author.getName(), actualResponse.getName())
	        );		
	}
	
	@Test
	@DisplayName("Should throw AuthorNotFoundException when record does not exist")
	public void shouldThrowExceptionWhenAuthorNotExist() {
		when(authorRepository.findById(1L))
			.thenReturn(Optional.empty());
		
		assertThrows(AuthorNotFoundException.class, () -> authorService.getAuthor(1L));
	}
	
	@Test
	@DisplayName("Should return all authors with pagination")
	public void shouldReturnAllAuthorsWithPagination() {
		int page = 0;
		int size = 3;
		String sort = "name";
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

		Author author1 = new Author();
		author1.setId(1L);
		author1.setName("J. K. Rowling");
		
		Author author2 = new Author();
		author2.setId(2L);
		author2.setName("J. R. R. Tolkien");
				
		Page<Author> pagedAuthors = new PageImpl(Arrays.asList(author1, author2));
		
		when(authorRepository.findAll(pageable))
			.thenReturn(pagedAuthors);
		
		Page<AuthorDto> pagedAuthorList = authorService.findAll(pageable);
		
		assertAll(
			    () -> assertEquals(2, pagedAuthorList.getNumberOfElements()),
			    () -> assertEquals(1L, pagedAuthorList.getContent().get(0).getId()),
			    () -> assertEquals("J. K. Rowling", pagedAuthorList.getContent().get(0).getName()),
			    () -> assertEquals(2L, pagedAuthorList.getContent().get(1).getId()),
			    () -> assertEquals("J. R. R. Tolkien", pagedAuthorList.getContent().get(1).getName())			    
	        );		
	}
	
}
