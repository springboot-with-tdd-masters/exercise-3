package com.softvision.books.repositories;

import com.softvision.books.repositories.entities.BookEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    @DisplayName("save_shouldAcceptBookEntityAndSaveToDatabase")
    void save_shouldAcceptBookEntityAndSaveToDatabase() {

        // arrange
        final String expectedTitle = "Sample Title";
        final String expectedAuthor = "Sample Author";
        final BookEntity bookEntity = new BookEntity(expectedTitle, expectedAuthor);

        // act
        final BookEntity savedBookEntity = bookRepository.save(bookEntity);

        // assert
        assertThat(savedBookEntity)
                .extracting("title", "author")
                .contains(expectedTitle, expectedAuthor);

        assertThat(testEntityManager.find(BookEntity.class, bookEntity.getId()))
                .isNotNull()
                .extracting("title", "author")
                .contains(expectedTitle, expectedAuthor);
    }

    @Test
    @DisplayName("findAll_shouldFindAllItemsOnTheDatabase")
    void findAll_shouldFindAllItemsOnTheDatabase() {

        // Arrange
        final BookEntity firstBookEntity = new BookEntity("Sample Title", "Sample Author");
        testEntityManager.persist(firstBookEntity);

        // Act
        final List<BookEntity> bookEntities = bookRepository.findAll();

        // Assert
        assertThat(bookEntities).hasSize(1);
        assertThat(bookEntities).contains(firstBookEntity);
    }
}
