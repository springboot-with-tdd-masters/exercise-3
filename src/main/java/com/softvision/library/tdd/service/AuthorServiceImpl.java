package com.softvision.library.tdd.service;

import com.softvision.library.tdd.model.Author;
import com.softvision.library.tdd.model.Book;
import com.softvision.library.tdd.model.RecordNotFoundException;
import com.softvision.library.tdd.repository.AuthorRepository;
import com.softvision.library.tdd.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    BookRepository bookRepository;

    public Author create(Author author){
        return authorRepository.save(author);
    }

    @Override
    public Page<Author> getAll(Pageable page) {
        return authorRepository.findAll(page);
    }

    @Override
    public Author getById(long id) {
        return authorRepository.findById(id).orElseThrow(RecordNotFoundException::new);
    }

    @Override
    public Book createBook(long id, Book book) {
        return authorRepository.findById(id).map(author -> {
            book.setAuthor(author);
            return bookRepository.save(book);
        }).orElseThrow(RecordNotFoundException::new);
    }
}
