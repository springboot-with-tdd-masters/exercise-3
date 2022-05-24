package com.example.demo.repository;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
class BookRepositoryTest {

    private final String bookTitle1 = "Othello";
    private final String bookTitle2 = "ForOneMoreDay";
    private final String author1 = "William Shakespeare";
    private final String author2 = "Mitch Albom";
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Test
    public void testFindAllBooks(){
        bookRepository.save(Book.builder().title(bookTitle1).author(createAuthor(author1)).build());
        bookRepository.save(Book.builder().title(bookTitle2).author(createAuthor(author2)).build());

        List<Book> retrievedBooks = bookRepository.findAll();
        assertEquals(2, retrievedBooks.size());
    }

    private Author createAuthor(String author) {
        return authorRepository.save(Author.builder().name(author).build());
    }

    @Test
    public void testFindById(){
        Book savedBook = bookRepository.save(Book.builder().title("title1").author(Author.builder().name("author1").build()).build());
        Book savedBook2 = bookRepository.save(Book.builder().title("title2").author(Author.builder().name("author2").build()).build());
        Optional<Book> retrievedBook = bookRepository.findById(savedBook.getId());
        assertEquals(savedBook.getTitle(), retrievedBook.get().getTitle());
        assertNotEquals(savedBook2.getAuthor().getName(), retrievedBook.get().getAuthor().getName());
    }

    @Test
    public void testDeleteById(){
        Book savedBook1 = bookRepository.save(Book.builder().title(bookTitle1).author(createAuthor(author1)).build());
        bookRepository.save(Book.builder().title(bookTitle2).author(createAuthor(author2)).build());

        Book savedBook = bookRepository.findById(savedBook1.getId()).get();
        bookRepository.deleteById(savedBook.getId());
        List<Book> retrievedBooks = bookRepository.findAll();
        assertEquals(1, retrievedBooks.size());
    }

    @Test
    public void testUpdateBook(){
        Book savedBook = bookRepository.save(Book.builder().title("title1").author(Author.builder().name("author1").build()).build());
        Optional<Book> retrievedBook = bookRepository.findById(savedBook.getId());
        retrievedBook.get().setAuthor(Author.builder().name("newAuthor").build());
        Book updatedBook = bookRepository.save(retrievedBook.get());
        assertEquals("newAuthor", updatedBook.getAuthor().getName());
    }
}