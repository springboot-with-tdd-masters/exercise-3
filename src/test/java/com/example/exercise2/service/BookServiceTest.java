package com.example.exercise2.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.exercise2.repository.BookRepository;
import com.example.exercise2.repository.entity.BookEntity;
import com.example.exercise2.service.adapters.BookEntityToBookAdapter;
import com.example.exercise2.service.adapters.BookToBookEntityAdapter;
import com.example.exercise2.service.model.Book;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BookServiceTest {


  @InjectMocks
  BookService bookService = new BookServiceImpl();

  @Mock
  BookRepository bookRepository;

  @Mock
  BookToBookEntityAdapter bookToBookEntityAdapter;

  @Mock
  BookEntityToBookAdapter bookEntityToBookAdapter;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Save")
  void saveBook() {

    //setup
    Book book = new Book();
    book.setTitle("BOOK1");
    book.setAuthor("AUTHOR1");

    BookEntity mockedBookEntityFromBookDomain = mock(BookEntity.class);
    when(bookToBookEntityAdapter.convert(book))
        .thenReturn(mockedBookEntityFromBookDomain);

    BookEntity mockedBookEntityFromRepository = mock(BookEntity.class);
    when(bookRepository.save(mockedBookEntityFromBookDomain))
        .thenReturn(mockedBookEntityFromRepository);

    Book mappedSavedBook = mock(Book.class);
    when(bookEntityToBookAdapter.convert(mockedBookEntityFromRepository))
        .thenReturn(mappedSavedBook);

    //invoke
    Book actualSavedBook = bookService.save(book);

    //verify
    verify(bookToBookEntityAdapter).convert(book);
    verify(bookRepository).save(mockedBookEntityFromBookDomain);
    verify(bookEntityToBookAdapter).convert(mockedBookEntityFromRepository);

    assertThat(actualSavedBook).isEqualTo(mappedSavedBook);
  }

  @Test
  @DisplayName("List all books")
  void displayAllbooks() {

    List<BookEntity> bookEntityList = new ArrayList<>();
    List<BookEntity> spyBookEntityListFromRepository = spy(bookEntityList);
    when(bookRepository.findAll()).thenReturn(spyBookEntityListFromRepository);

    List<Book> bookList = new ArrayList<>();
    List<Book> spyMappedBookList = spy(bookList);
    when(bookEntityToBookAdapter.convert(spyBookEntityListFromRepository)).thenReturn(
        spyMappedBookList);

    List<Book> actualBookListResult = bookService.findAll();

    verify(bookRepository).findAll();
    verify(bookEntityToBookAdapter).convert(spyBookEntityListFromRepository);

    assertThat(actualBookListResult).isEqualTo(spyMappedBookList);
  }
}
