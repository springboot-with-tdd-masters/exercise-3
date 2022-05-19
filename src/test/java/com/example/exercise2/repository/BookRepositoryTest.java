package com.example.exercise2.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.exercise2.repository.entity.BookEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class BookRepositoryTest {

  @Autowired
  private BookRepository bookRepository;

  @Test
  @DisplayName("Save - should accept entity and save details")
  void saveBooks() {

    //setup
    BookEntity book = new BookEntity("Librong Itim", "MR A");

    //act
    BookEntity savedBook = bookRepository.save(book);

    //verify
    assertThat(savedBook).extracting("title", "author")
        .containsExactly(book.getTitle(), book.getAuthor());
  }
}

