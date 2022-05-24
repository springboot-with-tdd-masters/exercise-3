package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.model.BookRequest;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Autowired
    private BookService bookService;

    @Test
    public void shouldGetAllBooks() {
        assertThat(bookService.getAllBooks().size(), is(9));
    }

    @Test
    public void shouldGetBookById() {
        Long existingId = bookService.getAllBooks().get(0).getId();
        Book fetchedBook = bookService.getBookById(existingId);
        assertThat(fetchedBook.getId(), is(existingId));
    }

    @Test
    public void shouldGetBooksWithSort() {
        List<String> ascOrderBooksByTitle = bookService.getAllBooks().stream().map(Book::getTitle).sorted().collect(Collectors.toList());
        List<Book> bookList = bookService.findBooksWithSorting("title");
        assertThat(bookList.get(0).getTitle(), is(ascOrderBooksByTitle.get(0)));
    }

    @Test
    public void shouldGetBooksWithPagination() {
        Page<Book> bookPage = bookService.findBooksWithPagination(0, 9);
        assertThat(bookPage.getTotalPages(), is(1));
        assertThat(bookPage.getPageable().getPageNumber(), is(0));
        assertThat(bookPage.getContent().get(0).getTitle(), is(bookService.getAllBooks().get(0).getTitle()));
    }

    @Test
    public void shouldGetBooksWithPaginationAndSortAsc() {
        List<String> ascOrderBooks = bookService.getAllBooks().stream().map(Book::getTitle).sorted().collect(Collectors.toList());
        Page<Book> bookPage = bookService.findBooksWithPaginationAndSorting(1, 5, "title", "asc");
        assertThat(bookPage.getTotalPages(), is(2));
        assertThat(bookPage.getPageable().getPageNumber(), is(1));
        assertThat(bookPage.getContent().get(0).getTitle(), is(ascOrderBooks.get(5)));
    }

    @Test
    public void shouldGetBooksWithPaginationAndSortDesc() {
        List<String> descOrderBooks = bookService.getAllBooks().stream().map(Book::getTitle).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        Page<Book> bookPage = bookService.findBooksWithPaginationAndSorting(0, 3, "title", "desc");
        assertThat(bookPage.getTotalPages(), is(3));
        assertThat(bookPage.getPageable().getPageNumber(), is(0));
        assertThat(bookPage.getContent().get(0).getTitle(), is(descOrderBooks.get(0)));
    }

}