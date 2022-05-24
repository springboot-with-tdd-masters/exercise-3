package com.example.demo.controller;

import com.example.demo.exception.BookNotFoundException;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.model.BookRequest;
import com.example.demo.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    private final String bookTitle1 = "Harry Potter and the Sorcerer's Stone";
    private final String bookTitle2 = "Harry Potter and the Chamber of Secrets";
    private final String bookTitle3 = "Othello";
    private final String bookTitle4 = "ForOneMoreDay";
    private final String author1 = "J. K. Rowling";
    private final String author3 = "William Shakespeare";
    private final String author4 = "Mitch Albom";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @Captor
    private ArgumentCaptor<BookRequest> bookRequestArgumentCaptor;

    @Test
    public void shouldCreateBookSuccess() throws Exception {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setAuthor(author1);
        bookRequest.setTitle(bookTitle1);

        when(bookService.createNewBook(bookRequestArgumentCaptor.capture())).thenReturn(1L);

        this.mockMvc
                .perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/api/books/1"));

        assertThat(bookRequestArgumentCaptor.getValue().getAuthor(), is(author1));
        assertThat(bookRequestArgumentCaptor.getValue().getTitle(), is(bookTitle1));
    }

    @Test
    public void shouldReadAllBooksSuccess() throws Exception {
        when(bookService.getAllBooks()).thenReturn(List.of(
                createBook(1L, bookTitle1, author1),
                createBook(2L, bookTitle2, author1)));

        this.mockMvc
                .perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is(bookTitle1)))
                .andExpect(jsonPath("$[1].title", is(bookTitle2)));
    }

    @Test
    public void shouldUpdateBookSuccess() throws Exception {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setAuthor(author1);
        bookRequest.setTitle(bookTitle1);

        when(bookService.updateBook(eq(1L), bookRequestArgumentCaptor.capture()))
                .thenReturn(createBook(1L, bookTitle1, author1));


        this.mockMvc
                .perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.title", is(bookTitle1)));

        assertThat(bookRequestArgumentCaptor.getValue().getAuthor(), is(author1));
        assertThat(bookRequestArgumentCaptor.getValue().getTitle(), is(bookTitle1));
    }

    @Test
    public void shouldReturn404BookNotFoundGet() throws Exception {
        Long bookId = 42L;
        when(bookService.getBookById(bookId)).thenThrow(new BookNotFoundException(String.format("Book with id %s not found", bookId)));
        this.mockMvc
                .perform(get("/api/books/42"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn404BookNotFoundUpdate() throws Exception {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setAuthor(author1);
        bookRequest.setTitle(bookTitle1);

        Long bookId = 42L;
        when(bookService.updateBook(eq(bookId), bookRequestArgumentCaptor.capture()))
                .thenThrow(new BookNotFoundException(String.format("Book with id %s not found", bookId)));

        this.mockMvc
                .perform(put("/api/books/42")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReadBookByIdSuccess() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(
                createBook(1L, bookTitle1, author1));


        this.mockMvc
                .perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.title", is(bookTitle1)));
    }

    @Test
    public void shouldReadBooksSorted() throws Exception {
        when(bookService.findBooksWithSorting("title")).thenReturn(List.of(
                createBook(3L, bookTitle4, author4),
                createBook(1L, bookTitle1, author1),
                createBook(2L, bookTitle3, author3)));

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("field", "title");

        this.mockMvc
                .perform(get("/api/books/sort").params(requestParams))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].title", is(bookTitle4)))
                .andExpect(jsonPath("$[1].title", is(bookTitle1)))
                .andExpect(jsonPath("$[2].title", is(bookTitle3)));
    }

    @Test
    public void shouldReadBooksWithPagination() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("field", "authorName");

        this.mockMvc
                .perform(get("/api/books/pagination/1/3").params(requestParams))
                .andExpect(status().isOk());

        ArgumentCaptor<Integer> offsetCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> pageSizeCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(bookService).findBooksWithPagination(offsetCaptor.capture(), pageSizeCaptor.capture());

        assertThat(offsetCaptor.getValue(), is(1));
        assertThat(pageSizeCaptor.getValue(), is(3));
    }

    @Test
    public void shouldReadBooksWithPaginationAndSort() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("field", "title");
        requestParams.add("order", "desc");

        this.mockMvc
                .perform(get("/api/books/paginationAndSort/0/2").params(requestParams))
                .andExpect(status().isOk());

        ArgumentCaptor<Integer> offsetCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> pageSizeCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> fieldCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> orderCaptor = ArgumentCaptor.forClass(String.class);
        verify(bookService).findBooksWithPaginationAndSorting(offsetCaptor.capture(), pageSizeCaptor.capture(), fieldCaptor.capture(), orderCaptor.capture());

        assertThat(offsetCaptor.getValue(), is(0));
        assertThat(pageSizeCaptor.getValue(), is(2));
        assertThat(fieldCaptor.getValue(), is("title"));
        assertThat(orderCaptor.getValue(), is("desc"));
    }

    private BookRequest buildBookRequest(String bookTitle, String author) {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle(bookTitle);
        bookRequest.setAuthor(author);
        return bookRequest;
    }

    private Book createBook(Long id, String title, String author) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(Author.builder().name(author).build());
        return book;
    }

}