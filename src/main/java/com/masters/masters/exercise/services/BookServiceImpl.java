package com.masters.masters.exercise.services;

import com.masters.masters.exercise.model.BookDtoRequest;
import com.masters.masters.exercise.model.BookDtoResponse;
import com.masters.masters.exercise.model.BookEntity;
import com.masters.masters.exercise.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository repo;

    public BookDtoResponse save(BookDtoRequest bookDto) {
        BookEntity entity = new BookEntity();
        entity.setTitle(bookDto.getTitle());
        BookEntity savedEntity = repo.save(entity);
        BookDtoResponse response = new BookDtoResponse();
        response.setTitle(savedEntity.getTitle());
        return response;

    }

    public Page<BookEntity> findAllBooks() {
        Pageable pageable = PageRequest.of(0,5, Sort.by("title").ascending());
        return repo.findAll(pageable);

    }
}
