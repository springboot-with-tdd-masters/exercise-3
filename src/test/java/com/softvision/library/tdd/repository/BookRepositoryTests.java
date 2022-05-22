package com.softvision.library.tdd.repository;

import com.softvision.library.tdd.model.Author;
import com.softvision.library.tdd.model.Book;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static com.softvision.library.tdd.LibraryMocks.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@DataJpaTest
public class BookRepositoryTests {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    static final String[] PROPERTIES_TO_EXTRACT = {"title", "author.name"};

    @Test
    @DisplayName("Save - should accept different books and save the correct respective details")
    void test_save() {

        Author savedAuthor1 = authorRepository.save(getMockAuthor1());
        Book savedBook1 = getMockBook1();
        savedBook1.setAuthor(savedAuthor1);
        savedBook1 = bookRepository.save(savedBook1);
        assertThat(savedBook1)
                .extracting(PROPERTIES_TO_EXTRACT)
                .containsExactly(MOCK_TITLE_1, MOCK_AUTHOR_1);

        Author savedAuthor2 = authorRepository.save(getMockAuthor2());
        Book savedBook2 = getMockBook2();
        savedBook2.setAuthor(savedAuthor2);
        savedBook2 = bookRepository.save(savedBook2);
        assertThat(savedBook2)
                .extracting(PROPERTIES_TO_EXTRACT)
                .containsExactly(MOCK_TITLE_2, MOCK_AUTHOR_2);

    }

    @Test
    @DisplayName("Find All - should be able to retrieve all saved books")
    void test_findAll() {
        Author savedAuthor1 = authorRepository.save(getMockAuthor1());
        Book book1 = getMockBook1();
        book1.setAuthor(savedAuthor1);
        bookRepository.save(book1);

        Author savedAuthor2 = authorRepository.save(getMockAuthor2());
        Book book2 = getMockBook2();
        book2.setAuthor(savedAuthor2);
        bookRepository.save(book2);

        assertThat(bookRepository.findAll())
                .extracting(PROPERTIES_TO_EXTRACT)
                .contains(tuple(MOCK_TITLE_1, MOCK_AUTHOR_1), tuple(MOCK_TITLE_2, MOCK_AUTHOR_2));
    }

    @Test
    @DisplayName("Find By Author ID - should be able to retrieve all books written by the author")
    void test_findByAuthorId() {
        Author savedAuthor1 = authorRepository.save(getMockAuthor1());
        Book book1 = getMockBook1();
        book1.setAuthor(savedAuthor1);
        bookRepository.save(book1);

        assertThat(bookRepository.findByAuthorId(savedAuthor1.getId(), null))
                .extracting("title")
                .contains(MOCK_TITLE_1);
    }

    @AfterEach
    void cleanup() {
        bookRepository.deleteAll();
        assertThat(bookRepository.findAll()).isEmpty();
    }
}
