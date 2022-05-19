package com.example.exercise2.controller;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.exercise2.service.BookService;
import com.example.exercise2.service.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc
public class BookControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BookService bookService;

  private ObjectMapper objectMapper = new ObjectMapper();

  @Test
  @DisplayName("Given a successful save, response should give http status 200")
  void success() throws Exception {

    //setup
    Book bookRequest = new Book();
    bookRequest.setId(1L);
    bookRequest.setAuthor("Mr Z");
    bookRequest.setTitle("Science 3");

    Book expectedResponse = new Book();
    expectedResponse.setId(1L);
    expectedResponse.setAuthor("Mr Z");
    expectedResponse.setTitle("Science 3");

    when(bookService.save(any())).thenReturn(expectedResponse);

    this.mockMvc.perform(post("/books").content(
            objectMapper.writeValueAsString(bookRequest)
        ).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));

    verify(bookService)
        .save(any());
  }

  @Test
  @DisplayName("Display all books")
  void getAllBooks() throws Exception {

    List<Book> expectedResponse = Arrays.asList(
        new Book(1l, "Science 1", "Mr. A"),
        new Book(2l, "Science 2", "Mr. Z"),
        new Book(3l, "Science 3", "Mr. G"),
        new Book(4l, "Science 4", "Mr. D")
    );

    when(bookService.findAll()).thenReturn(expectedResponse);

    this.mockMvc.perform(get("/books"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));

    verify(bookService)
        .findAll();
  }



  @Test
  @DisplayName("Dummy string test")
  void getTest() throws Exception {
    this.mockMvc.perform(get("/books/test"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("SUCCESS")));
  }

  @Test
  @DisplayName("Given an error unknown error in save, response should give http status 500")
  public void unknownError() throws Exception {

    Book bookResponse = new Book();
    bookResponse.setId(1L);
    bookResponse.setAuthor("Mr Z");
    bookResponse.setTitle("Science 3");

    when(bookService.save(any()))
        .thenThrow(new RuntimeException());

    this.mockMvc.perform(post("/books").content(
            objectMapper.writeValueAsString(bookResponse)
        ).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is5xxServerError());

    verify(bookService)
        .save(any());

  }
}
