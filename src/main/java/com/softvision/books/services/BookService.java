package com.softvision.books.services;

import com.softvision.books.services.domain.Book;

import java.util.List;

public interface BookService {

    Book save(Book book);

    List<Book> findAll();
}
