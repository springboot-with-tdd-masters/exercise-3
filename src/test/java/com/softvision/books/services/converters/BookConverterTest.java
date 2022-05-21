package com.softvision.books.services.converters;

import com.softvision.books.repositories.entities.BookEntity;
import com.softvision.books.services.converters.impl.BookConverterImpl;
import com.softvision.books.services.domain.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BookConverterTest {

    private BookConverter bookConverter = new BookConverterImpl();

    @Test
    @DisplayName("shouldAcceptBookEntityAndConvertToBook")
    void shouldAcceptBookEntityAndConvertToBook() {

        // Arrange
        final String expectedTitle = "Expected Title";
        final String expectedAuthor = "Expected Author";
        final Long expectedId = 1L;
        final BookEntity bookEntity = new BookEntity(expectedTitle, expectedAuthor);
        bookEntity.setId(expectedId);

        // Act
        final Book convertedBook = bookConverter.convert(bookEntity);

        // Assert
        assertThat(convertedBook)
                .extracting("id", "title", "author")
                .contains(expectedId, expectedTitle, expectedAuthor);
    }

    @Test
    @DisplayName("shouldAcceptBookAndConvertToBookEntity")
    void shouldAcceptBookAndConvertToBookEntity() {

        // Arrange
        final String expectedTitle = "Expected Title";
        final String expectedAuthor = "Expected Author";
        final Long expectedId = 1L;
        final Book book = new Book(expectedTitle, expectedAuthor);
        book.setId(expectedId);

        // Act
        final BookEntity convertedBookEntity = bookConverter.convert(book);

        // Assert
        assertThat(convertedBookEntity)
                .extracting("id", "title", "author")
                .contains(expectedId, expectedTitle, expectedAuthor);
    }
}