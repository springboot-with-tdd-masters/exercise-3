package com.masters.masters.exercise.services;

import com.masters.masters.exercise.exception.RecordNotFoundException;
import com.masters.masters.exercise.model.Author;
import com.masters.masters.exercise.model.BookDtoRequest;
import com.masters.masters.exercise.model.BookDtoResponse;
import com.masters.masters.exercise.model.BookEntity;
import com.masters.masters.exercise.repository.AuthorRepository;
import com.masters.masters.exercise.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository repo;

    @Autowired
    private AuthorRepository authorRepository;

    public BookEntity save(BookDtoRequest bookDto,Long id) throws RecordNotFoundException {
        Optional<Author> author = authorRepository.findById(id);
        if(author.isPresent()){
            BookEntity entity = new BookEntity();
            entity.setTitle(bookDto.getTitle());
            entity.setDescription(bookDto.getDescription());
            entity.setAuthor(author.get());
            return repo.save(entity);
        }else{
            throw new RecordNotFoundException("Author not found");
        }
    }

    public Page<BookEntity> findAllBooks(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public BookEntity findById(Long id) throws RecordNotFoundException {
        Optional<BookEntity> entity = repo.findById(id);
        if(entity.isPresent()){
            return entity.get();
        }else{
            throw new RecordNotFoundException("Book not found");
        }
    }
}
