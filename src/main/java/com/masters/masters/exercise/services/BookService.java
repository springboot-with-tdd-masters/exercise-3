package com.masters.masters.exercise.services;

import com.masters.masters.exercise.exception.RecordNotFoundException;
import com.masters.masters.exercise.model.BookDtoRequest;
import com.masters.masters.exercise.model.BookDtoResponse;
import com.masters.masters.exercise.model.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {

    BookEntity save(BookDtoRequest bookDto,Long id) throws RecordNotFoundException;
    Page<BookEntity> findAllBooks(Pageable pageable);

    BookEntity findById(Long id) throws RecordNotFoundException;
}
