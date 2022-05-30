package com.example.bookTDD.service;

import com.example.bookTDD.model.Book;
import com.example.bookTDD.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    BookRepository bookRepository;
    @InjectMocks
    BookService bookService = new BookService();

    @Test
    @DisplayName("Should create a Book")
    public void shouldCreateBook() {

        Book book = new Book();
        book.setTitle("Test title book");
        book.setAuthor("Zaldy D");

        Book expectedBook = new Book();
        book.setId(1L);
        book.setTitle("Test title book");
        book.setAuthor("Zaldy D");

        when(bookRepository.save(book)).thenReturn(expectedBook);

        Book actualResponse = bookService.createOrUpdateBook(book);

        verify(bookRepository).save(book);
        assertEquals(expectedBook, actualResponse);
    }

    @Test
    @DisplayName("Should get a list of books")
    public void shouldGetAllBooks() {

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test title book");
        book.setAuthor("Zaldy D");

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Test title book2");
        book2.setAuthor("Zaldy D");

        List<Book> books = Arrays.asList(book, book2);

        when(bookRepository.findAll()).thenReturn(books);

        assertThat(bookService.getAllBooks())
                .extracting(Book::getTitle)
                .containsExactly("Test title book", "Test title book2");

        verify(bookRepository).findAll();
    }
}
