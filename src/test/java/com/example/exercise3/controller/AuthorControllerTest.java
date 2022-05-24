package com.example.exercise3.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.exercise3.model.Author;
import com.example.exercise3.model.dto.AuthorDto;
import com.example.exercise3.service.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
@AutoConfigureMockMvc
public class AuthorControllerTest {

	@Autowired
	private MockMvc mockMvc;
	 
	@MockBean
	private AuthorService authorService;

	private ObjectMapper objectMapper = new ObjectMapper();
	 
	 
	@Test
	@DisplayName("Given a successful save, response should give http status 200")
	public void shouldSaveAuthor() throws Exception {
		String name = "J. K. Rowling";
		
		AuthorDto expectedResponse = new AuthorDto();
		expectedResponse.setId(1L);
		expectedResponse.setName(name);
		
		Date today = new Date();
		expectedResponse.setCreatedDate(today);		
		expectedResponse.setUpdatedDate(today);

		when(authorService.createAuthor(name))
	    	.thenReturn(expectedResponse);
		
		Map<String, String> request = new HashMap();
		request.put("name", name);
	
		this.mockMvc.perform(post("/authors").content(
		            	objectMapper.writeValueAsString(request)
		    		).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
			.andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.updatedDate").exists());

		verify(authorService).createAuthor(name);
	}
	
	@Test
	@DisplayName("Should return author with correct details")
	public void shouldReturnAuthor() throws Exception {
		String name = "J. K. Rowling";
		
		AuthorDto expectedResponse = new AuthorDto();
		expectedResponse.setId(1L);
		expectedResponse.setName(name);
		
		Date today = new Date();
		expectedResponse.setCreatedDate(today);		
		expectedResponse.setUpdatedDate(today);

		when(authorService.getAuthor(1L))
	    	.thenReturn(expectedResponse);
		
		this.mockMvc.perform(get("/authors/1"))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
			.andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.updatedDate").exists());

		verify(authorService).getAuthor(1L);
	}
	
	@Test
	@DisplayName("Should return all authors with paging and sorting")
	public void shouldReturnAllAuthors() throws Exception {
		String name = "J. K. Rowling";
		
		AuthorDto expectedResponse = new AuthorDto();
		expectedResponse.setId(1L);
		expectedResponse.setName(name);
		
		Date today = new Date();
		expectedResponse.setCreatedDate(today);		
		expectedResponse.setUpdatedDate(today);

		Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "createdDate"));

		Page<AuthorDto> pagedAuthors = new PageImpl(Arrays.asList(expectedResponse));
		
		when(authorService.findAll(pageable))
	    	.thenReturn(pagedAuthors);
		
		this.mockMvc.perform(get("/authors?page=0&size=2&sort=createdDate,desc"))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.content.[0].id").value("1"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.content.[0].name").value("J. K. Rowling"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.content.[0].createdDate").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.content.[0].updatedDate").exists());

		verify(authorService).findAll(pageable);
	}
		
	   
}
