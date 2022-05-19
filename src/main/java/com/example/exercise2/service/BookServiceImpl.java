package com.example.exercise2.service;

import com.example.exercise2.repository.BookRepository;
import com.example.exercise2.repository.entity.BookEntity;
import com.example.exercise2.service.adapters.BookEntityToBookAdapter;
import com.example.exercise2.service.adapters.BookToBookEntityAdapter;
import com.example.exercise2.service.model.Book;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private BookToBookEntityAdapter bookToBookEntityAdapter;

  @Autowired
  private BookEntityToBookAdapter bookEntityToBookAdapter;


  @Override
  public Book save(Book book) {
    BookEntity bookToSave = bookToBookEntityAdapter.convert(book);
    BookEntity savedBookEntity = bookRepository.save(bookToSave);
    return bookEntityToBookAdapter.convert(savedBookEntity);
  }

  @Override
  public List<Book> findAll() {
    return bookEntityToBookAdapter.convert(bookRepository.findAll());
  }
}
