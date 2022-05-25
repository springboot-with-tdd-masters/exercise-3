package com.masters.masters.exercise.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.masters.masters.exercise.model.Author;
import com.masters.masters.exercise.model.AuthorDtoRequest;
import com.masters.masters.exercise.services.AuthorService;
import com.masters.masters.exercise.services.AuthorServiceImpl;
import com.masters.masters.exercise.services.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private BookService bookService;


    @Test
    public void saveAuthorHappyPath() throws Exception {
        AuthorDtoRequest request = new AuthorDtoRequest();
        request.setName("name");
        Author response = new Author();
        response.setName("name");
        response.setId(Long.parseLong("2"));
        when(authorService.saveAuthor(Mockito.any(AuthorDtoRequest.class))).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("2"));

    }

    @Test
    public void findAuthorByIdHappyPath() throws Exception {
        Author response = new Author();
        response.setName("name");
        response.setId(Long.parseLong("1"));
        when(authorService.findById(Mockito.anyLong())).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));

    }

    @Test
    public void findAllAuthor() throws Exception {
        Author response = new Author();
        response.setName("name");
        response.setId(Long.parseLong("1"));
        Author response2 = new Author();
        response2.setName("name2");
        response2.setId(Long.parseLong("2"));
        Pageable pageable = PageRequest.of(0,5, Sort.by("title").ascending());
        when(authorService.findAllAuthors(pageable)).thenReturn(new PageImpl<>(List.of(response,response2)));
        mockMvc.perform(MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].name").value("name2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].id").value("2"));

    }
}
