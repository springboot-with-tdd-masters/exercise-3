package com.softvision.books.services.converters.impl;

import com.softvision.books.repositories.entities.BookEntity;
import com.softvision.books.services.converters.BookConverter;
import com.softvision.books.services.domain.Book;
import org.springframework.stereotype.Component;

@Component
public class BookConverterImpl implements BookConverter {

    @Override
    public BookEntity convert(Book book) {

        final BookEntity bookEntity = new BookEntity();

        bookEntity.setId(book.getId());
        bookEntity.setTitle(book.getTitle());
        bookEntity.setAuthor(book.getAuthor());

        return bookEntity;
    }

    @Override
    public Book convert(BookEntity bookEntity) {

        final Book book = new Book();

        book.setId(bookEntity.getId());
        book.setTitle(bookEntity.getTitle());
        book.setAuthor(bookEntity.getAuthor());

        return book;
    }
}
