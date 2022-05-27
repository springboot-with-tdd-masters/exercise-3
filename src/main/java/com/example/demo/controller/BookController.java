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
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1")
public class BookController {

    final static Logger logger = Logger.getLogger("BookController");

    @Autowired
    BookService bookService;

    @PostMapping("/books")
    public DTOResponse<Book> addBook(@RequestBody Book book){
        logger.info("Adding Book...");
        return bookService.addBook(book);
    }

    @GetMapping("/books/{id}")
    public Optional<Book> getBook(@PathVariable Long id){
        logger.info("Getting Book...");

        return bookService.getBook(id);
    }

    @GetMapping("/books")
    public List<Book> getBooks(){
        logger.info("Getting All Books...");

        return bookService.getBooks();
    }


    @PutMapping("/books")
    public DTOResponse<Book> updateBook(@RequestBody Book book){
        logger.info("Updating Book...");
        return bookService.addBook(book);
    }

    @GetMapping("/books/{offset}/{pageSize}/{field}")
    private DTOResponse<Page<Book>> getBooksWithPaginationAndSort(@PathVariable int offset, @PathVariable int pageSize,
                                                                  @PathVariable String field,@RequestParam String order) {
        Page<Book> booksWithPagination = bookService.findBooksWithPaginationAndSorting(offset, pageSize, field,order);
        return new DTOResponse<>(booksWithPagination.getSize(), booksWithPagination);
    }

    @DeleteMapping("/books/{id}")
    public String deleteBook(@PathVariable Long id){
        logger.info("Deleting Book...");
        bookService.deleteBook(id);
        return "Book Deleted with reference Id: "+id;
    }

}
