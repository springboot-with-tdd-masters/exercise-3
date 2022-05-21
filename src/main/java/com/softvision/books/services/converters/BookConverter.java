package com.softvision.books.services.converters;

import com.softvision.books.repositories.entities.BookEntity;
import com.softvision.books.services.domain.Book;

public interface BookConverter {

    BookEntity convert(Book book);

    Book convert(BookEntity bookEntity);
}
