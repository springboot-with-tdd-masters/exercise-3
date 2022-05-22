package com.softvision.library.tdd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softvision.library.tdd.model.Book;
import com.softvision.library.tdd.service.BookService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static com.softvision.library.tdd.LibraryMocks.*;

import java.util.Arrays;
import java.util.Collections;

@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookService bookService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Given a successful service getAll, response should give http status 200 with the list.")
    void test_getAll_success() throws Exception {
        when(bookService.getAll()).thenReturn(Arrays.asList(getMockBook1(), getMockBook2()));

        mockMvc.perform(get("/books")).andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].title", Matchers.is(MOCK_TITLE_1)))
                .andExpect(jsonPath("$[1].author", Matchers.is(MOCK_AUTHOR_2)));

        verify(bookService, atMostOnce()).getAll();
    }

    @Test
    @DisplayName("Given a blank result from service getAll, response should give http status 404 (not found).")
    void test_getAll_fail() throws Exception {
        when(bookService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/books"))
                .andExpect(status().isNotFound());

        verify(bookService, atMostOnce()).getAll();
    }

    @Test
    @DisplayName("Given a successful result from createOrUpdate, response should give http status 201 (created).")
    void test_create() throws Exception {
        Book book = getMockBook1();
        when(bookService.createOrUpdate(argThat(b -> b.getTitle().equals(MOCK_TITLE_1))))
                .thenReturn(book);

        mockMvc.perform(post("/books").content(objectMapper.writeValueAsBytes(book))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("title", Matchers.is(MOCK_TITLE_1)))
                .andExpect(jsonPath("author", Matchers.is(MOCK_AUTHOR_1)));
    }

    @Test
    @DisplayName("Given a failure result from createOrUpdate, response should give http status 5xx (server error).")
    void test_create_fail() throws Exception {
        Book book = getMockBook1();
        when(bookService.createOrUpdate(argThat(b -> b.getTitle().equals(MOCK_TITLE_1))))
                .thenThrow(new IllegalArgumentException());

        mockMvc.perform(post("/books").content(objectMapper.writeValueAsBytes(book))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }
}
