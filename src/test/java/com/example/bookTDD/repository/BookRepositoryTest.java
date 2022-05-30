package com.example.bookTDD.repository;

import com.example.bookTDD.model.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;

    @Test
    @DisplayName("Should save book with correct details")
    public void saveBook() {
        Book book = new Book();
        book.setTitle("Test title book");
        book.setAuthor("Zaldy D");

        Book savedBook = bookRepository.save(book);

        assertThat(savedBook)
                .extracting("title", "author")
                .containsExactly("Test title book", "Zaldy D");
    }

    @Test
    @DisplayName("Should return all books with correct details")
    public void getAllBooks() {
        Book book = new Book();
        book.setTitle("Test title book");
        book.setAuthor("Zaldy D");

        Book book2 = new Book();
        book.setTitle("Test title book2");
        book.setAuthor("Zaldy D");

        Book savedBook1 = bookRepository.save(book);
        Book savedBook2 = bookRepository.save(book2);

        assertThat(bookRepository.findAll())
                .hasSize(2)
                .contains(savedBook1, savedBook2);

    }

}
