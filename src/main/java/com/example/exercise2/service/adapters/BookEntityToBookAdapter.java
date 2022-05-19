package com.example.exercise2.service.adapters;

import com.example.exercise2.repository.entity.BookEntity;
import com.example.exercise2.service.model.Book;
import java.util.List;

public interface BookEntityToBookAdapter {

  Book convert(BookEntity bookEntity);
  List<Book> convert(List<BookEntity> bookEntity);
}
