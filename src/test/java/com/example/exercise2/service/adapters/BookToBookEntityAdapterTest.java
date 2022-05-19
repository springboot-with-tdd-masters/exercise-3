package com.example.exercise2.service.adapters;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.exercise2.repository.entity.BookEntity;
import com.example.exercise2.service.model.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

public class BookToBookEntityAdapterTest {

  @InjectMocks
  BookToBookEntityAdapter bookToBookEntityAdapter = new BookToBookEntityAdapterImpl();


  @Test
  @DisplayName("Convert BookEntity object to Book object")
  void convert_Book_to_BookEntity_test() {

    Book book = new Book(1l, "Science 1", "Mr. A");
    BookEntity expectedBookEntityObject = new BookEntity(1l, "Science 1", "Mr. A");

    BookEntity actualBookEntityObject = bookToBookEntityAdapter.convert(book);

    assertThat(actualBookEntityObject.toString()).isEqualTo(expectedBookEntityObject.toString());
  }

}
