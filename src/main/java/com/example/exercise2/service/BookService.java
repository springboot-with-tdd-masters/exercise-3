package com.example.exercise2.service;

import com.example.exercise2.service.model.Book;
import java.util.List;

public interface BookService {

  Book save(Book book);

  List<Book> findAll();
}
