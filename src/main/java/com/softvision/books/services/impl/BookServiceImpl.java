package com.softvision.books.services.impl;

import com.softvision.books.repositories.BookRepository;
import com.softvision.books.repositories.entities.BookEntity;
import com.softvision.books.services.BookService;
import com.softvision.books.services.converters.BookConverter;
import com.softvision.books.services.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    private BookConverter bookConverter;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    public void setBookConverter(BookConverter bookConverter) {
        this.bookConverter = bookConverter;
    }

    @Override
    public Book save(Book book) {

        final BookEntity bookEntity = bookConverter.convert(book);

        final BookEntity savedEntity = bookRepository.save(bookEntity);

        return bookConverter.convert(savedEntity);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookConverter::convert)
                .collect(Collectors.toList());
    }
}
