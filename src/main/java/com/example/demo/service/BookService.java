package com.example.demo.service;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.repository.AuthorRepo;
import com.example.demo.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepo bookRepo;

    @Autowired
    AuthorRepo authorRepo;

    /*
    * Author Section
     */
    //create author
    public Author addAuthor(Author author){ return authorRepo.save(author); }

    public Optional<Author> getAuthor(Long id){
        return authorRepo.findById(id);
    }

    public Page<Author> findAuthorsWithPaginationAndSorting(int offset,int pageSize,String field){
        Page<Author> authors = authorRepo.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
        return  authors;
    }


    public List<Author> getAuthors(){
        return authorRepo.findAll();
    }


    public void deleteAuthor(Long id){
        authorRepo.deleteById(id);
    }

    /*
    *Book Section
     */
    //create book
    public Book addBook(Book book){ return bookRepo.save(book); }


    //get a book
    public Optional<Book> getBook(Long id){
        return bookRepo.findById(id);
    }

    //get all books
    public List<Book> getBooks(){
        return bookRepo.findAll();
    }

    //delete a book
    public void deleteBook(Long id){
         bookRepo.deleteById(id);
    }

}
