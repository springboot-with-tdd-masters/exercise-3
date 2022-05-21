package com.softvision.books.services;

import com.softvision.books.repositories.BookRepository;
import com.softvision.books.repositories.entities.BookEntity;
import com.softvision.books.services.converters.BookConverter;
import com.softvision.books.services.domain.Book;
import com.softvision.books.services.impl.BookServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @InjectMocks
    private BookService bookService = new BookServiceImpl();

    @Mock
    private BookConverter bookConverter;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("save_shouldAcceptBookAndSaveToRepository")
    void save_shouldAcceptBookAndSaveToRepository() {

        // Arrange
        final Book book = new Book("Sample Title", "Sample Author");

        final BookEntity convertedBookToBookEntity = mock(BookEntity.class);
        when(bookConverter.convert(book))
                .thenReturn(convertedBookToBookEntity);

        final BookEntity savedBookFromRepository = mock(BookEntity.class);
        when(bookRepository.save(convertedBookToBookEntity))
                .thenReturn(savedBookFromRepository);

        final Book convertedBookEntityToBook = mock(Book.class);
        when(bookConverter.convert(savedBookFromRepository))
                .thenReturn(convertedBookEntityToBook);

        // Act
        final Book savedBookFromService = bookService.save(book);

        // Assert
        verify(bookConverter)
                .convert(book);

        verify(bookRepository)
                .save(convertedBookToBookEntity);

        verify(bookConverter)
                .convert(savedBookFromRepository);

        assertThat(savedBookFromService)
                .isEqualTo(convertedBookEntityToBook);
    }

    @Test
    @DisplayName("findAll_shouldReturnEmptyListIfThereIsNoItemOnRepository")
    void findAll_shouldReturnEmptyListIfThereIsNoItemOnRepository() {
        // Arrange

        // Act
        final List<Book> books = bookService.findAll();

        // Assert
        verify(bookRepository)
                .findAll();

        assertThat(books)
                .isEmpty();
    }

    @Test
    @DisplayName("findAll_shouldReturnAllBookEntitiesAsBooksFromRepository")
    void findAll_shouldReturnAllBookEntitiesAsBooksFromRepository() {

        // Arrange
        final BookEntity firstBookEntity = mock(BookEntity.class);
        when(bookRepository.findAll())
                .thenReturn(Arrays.asList(firstBookEntity));

        final Book firstBook = mock(Book.class);
        when(bookConverter.convert(firstBookEntity))
                .thenReturn(firstBook);

        // Act
        final List<Book> books = bookService.findAll();

        // Assert
        verify(bookRepository)
                .findAll();

        verify(bookConverter)
                .convert(firstBookEntity);

        assertThat(books)
                .contains(firstBook);
    }
}
