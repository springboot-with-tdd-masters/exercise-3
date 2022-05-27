package com.example.demo.controller;

import com.example.demo.dto.DTOResponse;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1")
public class AuthorController {

    final static Logger logger = Logger.getLogger("AuthorController");

    @Autowired
    BookService bookService;

    @PostMapping("/authors")

    public ResponseEntity<DTOResponse> addAuthor(@RequestBody Author author){
        logger.info("Adding Author...");

        Long id = bookService.addAuthor(author).getId();
        Optional<Author> newAuthor = bookService.getAuthor(id);
        if (newAuthor.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(new DTOResponse(1,newAuthor));
        }
        throw bookService.NotFoundResponse();

    }

    @GetMapping("/authors/{id}")
    public Optional<Author> getAuthor(@PathVariable Long id){
        logger.info("Getting Author...");
        return bookService.getAuthor(id);
    }


    @GetMapping("/authors")
    public List<Author> getAuthors(){
        logger.info("Getting All Authors...");

        return bookService.getAuthors();
    }

    @GetMapping("/authors/{offset}/{pageSize}/{field}")
    private DTOResponse<Page<Author>> getAuthorsWithPaginationAndSort(@PathVariable int offset, @PathVariable int pageSize,
                                                                      @PathVariable String field,@RequestParam String order) {
        logger.info("getAuthorsWithPaginationAndSort...");

        Page<Author> authorsWithPagination = bookService.findAuthorsWithPaginationAndSorting(offset, pageSize, field,order);
        return new DTOResponse<>(authorsWithPagination.getSize(), authorsWithPagination);
    }


    @DeleteMapping("/books/{id}")
    public String deleteBook(@PathVariable Long id){
        logger.info("Deleting Author...");
        bookService.deleteAuthor(id);
        return "Author Deleted with reference Id: "+id;
    }

}
