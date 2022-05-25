package com.masters.masters.exercise.services;

import com.masters.masters.exercise.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

public class BookServiceTest {

    @Mock
    BookRepository repo;

    @InjectMocks
    BookServiceImpl bookService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void saveHappyPath(){
//        BookDtoRequest bookDto = new BookDtoRequest();
//        bookDto.setTitle("title1");
//        BookEntity repoResponse = new BookEntity();
//        repoResponse.setTitle("title1");
//        when(repo.save(Mockito.any(BookEntity.class))).thenReturn(repoResponse);
//        BookDtoResponse bookDtoResponse = bookService.save(bookDto);
//        verify(repo).save(Mockito.any(BookEntity.class));
//        Assertions.assertEquals("title1",bookDtoResponse.getTitle());
//    }
//
//    @Test
//    public void findAllHappyPath(){
//        BookEntity book1 = new BookEntity();
//        book1.setId(1L);
//        book1.setTitle("title1");
//        BookEntity book2 = new BookEntity();
//        book1.setId(2L);
//        book2.setTitle("title2");
//        when(repo.findAll()).thenReturn(List.of(book1,book2));
//        List<BookEntity> bookList = bookService.findAllBooks();
//        verify(repo).findAll();
//        assertThat(bookList).hasSize(2);
//    }
//
//    @Test
//    public void findAllNoRecord(){
//        when(repo.findAll()).thenReturn(List.of());
//        List<BookEntity> bookList = bookService.findAllBooks();
//        verify(repo).findAll();
//        assertThat(bookList).isEmpty();
//    }
}
