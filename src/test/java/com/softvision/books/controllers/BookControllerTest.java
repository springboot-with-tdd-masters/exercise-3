package com.softvision.books.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softvision.books.services.BookService;
import com.softvision.books.services.domain.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // Meaning replace bookService bean in the container with this mock.
    private BookService bookService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("saveEndpoint_shouldReturn201Status")
    void saveEndpoint_shouldReturn201Status() throws Exception {

        // Arrange
        final Book bookAsRequest = new Book("Sample Title", "Sample Author");

        final Book savedBook = new Book(1L, "Sample Title", "Sample Author");
        when(bookService.save(bookAsRequest))
                .thenReturn(savedBook);

        // Act
        mockMvc.perform(post("/api/rest/books")
                .content(objectMapper.writeValueAsString(bookAsRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))
                .andExpect(content().json(objectMapper.writeValueAsString(savedBook)));

        // Assert
        verify(bookService)
                .save(bookAsRequest);
    }

    @Test
    @DisplayName("saveEndpoint_shouldReturn500StatusWhenServiceThrowAnyError")
    void saveEndpoint_shouldReturn500StatusWhenServiceThrowAnyError() throws Exception {

        // Arrange
        final Book bookAsRequest = new Book("Sample Title", "Sample Author");

        when(bookService.save(bookAsRequest))
                .thenThrow(new RuntimeException());

        // Act
        mockMvc.perform(post("/api/rest/books")
                .content(objectMapper.writeValueAsString(bookAsRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());

        // Assert
        verify(bookService)
                .save(bookAsRequest);
    }

    @Test
    @DisplayName("getAllEndpoint_shouldReturnEmptyJsonArrayIfServiceReturnsEmpty")
    void getAllEndpoint_shouldReturnEmptyJsonArrayIfServiceReturnsEmpty() throws Exception {
        // Arrange

        // Act
        mockMvc.perform(get("/api/rest/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));

        // Assert
        verify(bookService)
                .findAll();
    }

    @Test
    @DisplayName("getAllEndpoint_shouldReturnBookItemsFromTheService")
    void getAllEndpoint_shouldReturnBookItemsFromTheService() throws Exception {

        // Arrange
        final Book firstBook = new Book(1L, "Sample Title", "Sample Author");
        final List<Book> books = Arrays.asList(firstBook);

        when(bookService.findAll())
                .thenReturn(books);

        // Act
        mockMvc.perform(get("/api/rest/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(books)));

        // Assert
        verify(bookService)
                .findAll();
    }
}
