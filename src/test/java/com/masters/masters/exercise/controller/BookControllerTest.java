package com.masters.masters.exercise.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masters.masters.exercise.model.BookDtoRequest;
import com.masters.masters.exercise.model.BookDtoResponse;
import com.masters.masters.exercise.model.BookEntity;
import com.masters.masters.exercise.services.AuthorService;
import com.masters.masters.exercise.services.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

@WebMvcTest
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @Test
    public void saveBookHappyPath() throws Exception {
        BookDtoRequest request = new BookDtoRequest();
        request.setAuthor("author1");
        request.setTitle("title1");
        BookDtoResponse response = new BookDtoResponse();
        response.setAuthor("author1");
        response.setTitle("title1");
        when(bookService.save(Mockito.any(BookDtoRequest.class))).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.post("/book")
                .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("author1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("title1"));

    }

    @Test
    public void getAllBooksHappyPath() throws Exception {
        BookEntity book1 = new BookEntity();
        book1.setTitle("title1");
        BookEntity book2 = new BookEntity();
        book2.setTitle("title2");
        when(bookService.findAllBooks()).thenReturn(new PageImpl<>(List.of(book1,book2)));
        mockMvc.perform(MockMvcRequestBuilders.get("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].title").value("title1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].title").value("title2"));
    }

    @Test
    public void getAllBooksEmptyResult() throws Exception {
        when(bookService.findAllBooks()).thenReturn(new PageImpl<>(new ArrayList<>()));
        mockMvc.perform(MockMvcRequestBuilders.get("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isEmpty());
    }
}
