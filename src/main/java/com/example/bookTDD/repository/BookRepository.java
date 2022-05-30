package com.example.bookTDD.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookTDD.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
