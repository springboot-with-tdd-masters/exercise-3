package com.example.bookTDD.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.bookTDD.exception.RecordNotFoundException;
import com.example.bookTDD.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookTDD.model.Book;

@Service
public class BookService {

	@Autowired
	BookRepository bookRepository;

	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}
	public Book createOrUpdateBook(Book entity) {
		Optional<Book> book = bookRepository.findById(entity.getId());


		if (book.isPresent()) {
			Book newEntity = book.get();
			newEntity.setAuthor(entity.getAuthor());
			newEntity.setTitle(entity.getTitle());

			newEntity = bookRepository.save(newEntity);

			return newEntity;
		} else {
			entity = bookRepository.save(entity);

			return entity;
		}
	}

}
