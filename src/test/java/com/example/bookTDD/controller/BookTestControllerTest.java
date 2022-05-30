package com.example.bookTDD.controller;

import com.example.bookTDD.model.Book;
import com.example.bookTDD.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest
@AutoConfigureMockMvc
public class BookTestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private BookService bookService;

    Book bookTest1 = new Book(1L,"First","Zaldy");
    Book bookTest2 = new Book(2L,"Second","Zaldy");



    @Test
    @DisplayName("Get all books, response should give http status 200")
    public void shouldReturnAllBooks() throws Exception {

        List<Book> books = new ArrayList<>(Arrays.asList(bookTest1, bookTest2));

        when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("First")));

    }

    @Test
    @DisplayName("Given a successful result from createOrUpdateBook, response should give http status 201.")
    public void shouldCreateNewBook() throws Exception {

        Book bookTest = new Book();
        bookTest.setId(1L);
        bookTest.setTitle("Test");
        bookTest.setAuthor("Zaldy");

        when(bookService.createOrUpdateBook(argThat(b -> b.getTitle().equals("Test"))))
                .thenReturn(bookTest);

        mockMvc.perform(post("/books")
                .content(mapper.writeValueAsBytes(bookTest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("title", Matchers.is("Test")))
                .andExpect(jsonPath("author", Matchers.is("Zaldy")));
    }

}
