package com.example.demo.service;

import com.example.demo.dto.DTOResponse;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.repository.AuthorRepo;
import com.example.demo.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepo bookRepo;

    @Autowired
    AuthorRepo authorRepo;

    public Author addAuthor(Author author){
        //retrieve book list
        List<Book> booksRepo = bookRepo.findAll();
        //books container
        List<Book> books = new ArrayList<>();
        //stream booksRepo
        books.addAll(booksRepo);
        //finally
        author.setBooks(books);

        return authorRepo.save(author);
    }

    public Optional<Author> getAuthor(Long id){
        if (authorRepo.findById(id).isPresent()){
            return authorRepo.findById(id);
        }
        throw NotFoundResponse();
    }

    public List<Author> getAuthors(){
        if (authorRepo.findAll().isEmpty()){
            throw NotFoundResponse();
        }
        return authorRepo.findAll();
    }

    public void deleteAuthor(Long id){
        authorRepo.deleteById(id);
    }

    public Page<Author> findAuthorsWithPaginationAndSorting(int offset,int pageSize,String field,String order){
        Sort sOrder = order.equalsIgnoreCase(
                Sort.Direction.ASC.name()) ? Sort.by(field).ascending() : Sort.by(field).descending();
        Page<Author> authors = authorRepo.findAll(PageRequest.of(offset, pageSize, sOrder));

        return  authors;
    }


    /*
    *Book Section
     */
    //create book
    public DTOResponse addBook(Book book){
        List<Author> author = authorRepo.findAll();
        if (author.isEmpty()){
            book.setAuthor_id(1L);
        }else{
            book.setAuthor_id(author.get(0).getId());
        }

        Book bookfromRepo = bookRepo.save(book);

        Optional<Book> newBook = getBook(bookfromRepo.getId());
        if (newBook.isPresent()) {
            return new DTOResponse<Book>(1, newBook.get());
        }
        throw NotFoundResponse();
    }

    //get a book
    public Optional<Book> getBook(Long id){
        if (bookRepo.findById(id).isPresent()){
            return bookRepo.findById(id);
        }
        throw NotFoundResponse();
    }

    //get all books
    public List<Book> getBooks(){
        if (bookRepo.findAll().isEmpty()){
            throw NotFoundResponse();
        }
        return bookRepo.findAll();
    }

    //delete a book
    public void deleteBook(Long id){
         bookRepo.deleteById(id);
    }


    public Page<Book> findBooksWithPaginationAndSorting(int offset,int pageSize,String field,String order){

        Sort sOrder = order.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(field).ascending() : Sort.by(field).descending();
        Page<Book> books = bookRepo.findAll(PageRequest.of(offset, pageSize,sOrder));
        return  books;
    }

    public ResponseStatusException NotFoundResponse(){
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND");
    }

}
