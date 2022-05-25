package com.masters.masters.exercise.controller;

import com.masters.masters.exercise.exception.RecordNotFoundException;
import com.masters.masters.exercise.model.Author;
import com.masters.masters.exercise.model.AuthorDtoRequest;
import com.masters.masters.exercise.model.BookDtoRequest;
import com.masters.masters.exercise.model.BookDtoResponse;
import com.masters.masters.exercise.services.AuthorService;
import com.masters.masters.exercise.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping
    public ResponseEntity<Author> saveAuthor(@RequestBody AuthorDtoRequest request){
        Author response = authorService.saveAuthor(request);
        return new ResponseEntity<Author>(response, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Author> findAuthorById(@PathVariable Long id) throws RecordNotFoundException {
        Author response = authorService.findById(id);
        return new ResponseEntity<Author>(response, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Author>> findAuthorByAllAuthors(Pageable pageRequest) throws RecordNotFoundException {
        Page<Author>list = authorService.findAllAuthors(pageRequest);
        return new ResponseEntity<Page<Author>>(list, new HttpHeaders(), HttpStatus.OK);
    }
}
