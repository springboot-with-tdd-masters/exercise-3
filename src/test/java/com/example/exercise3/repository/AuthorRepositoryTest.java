package com.example.exercise3.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.example.exercise3.model.Author;
import com.example.exercise3.model.Book;

@EnableJpaAuditing
@DataJpaTest
public class AuthorRepositoryTest {
	
	@Autowired
	private AuthorRepository authorRepository;

	@Test
	@DisplayName("Should save author entity")
	public void saveAuthor() {
	     Author author = new Author();
	     author.setName("J.K. Rowling");

	     Author savedAuthor = authorRepository.save(author);

	     assertEquals(author.getName(), savedAuthor.getName());
	     assertNotNull(savedAuthor.getCreatedDate());
	     assertNotNull(savedAuthor.getUpdateDate());
	}
	
	@Test
	@DisplayName("Should save author with books")
	public void saveAuthorWithBooks() {
	     Author newAuthor = new Author();
	     newAuthor.setName("J.K. Rowling");
	     
	     Book book1 = new Book();
	     book1.setTitle("Harry Potter and the Sorcerer's Stone");
	     book1.setAuthor(newAuthor);

	     Book book2 = new Book();
	     book2.setTitle("Harry Potter and the Chamber of Secrets");
	     book2.setAuthor(newAuthor);
	     
	     newAuthor.addBooks(book1, book2);
	     
	     Author savedAuthor = authorRepository.save(newAuthor);

	     assertEquals(newAuthor.getName(), savedAuthor.getName());
	     assertThat(savedAuthor.getBooks())
	     	.hasSize(2)
	     	.contains(book1, book2);
	}
	
	@Test
	@DisplayName("Get author with books with correct details")
	public void getAuthorWithBooks() {
	     Author newAuthor = new Author();
	     newAuthor.setName("J.K. Rowling");
	     
	     Book book1 = new Book();
	     book1.setTitle("Harry Potter and the Sorcerer's Stone");
	     book1.setAuthor(newAuthor);

	     Book book2 = new Book();
	     book2.setTitle("Harry Potter and the Chamber of Secrets");
	     book2.setAuthor(newAuthor);
	     
	     newAuthor.addBooks(book1, book2);
	     
	     Author savedAuthor = authorRepository.save(newAuthor);
	     
	     Author author = authorRepository.findById(savedAuthor.getId()).get();
	     
	     assertEquals(newAuthor.getName(), author.getName());
	     assertThat(author.getBooks())
	     	.hasSize(2)
	     	.contains(book1, book2);
	}
	
	@Test
	@DisplayName("Get paginated list of authors with correct sort")
	public void getPaginatedAuthorListSortedByName() {
		Author author1 = new Author();
	    author1.setName("J. K. Rowling");
	     
	    Author author2 = new Author();
	    author2.setName("J. R. R. Tolkien");
	     
	    Author author3 = new Author();
	    author3.setName("George Orwell");
	    	     
	    authorRepository.save(author1);	
	    authorRepository.save(author2);	
	    authorRepository.save(author3);	
	     
	 	int page = 0;
		int size = 3;
		String sort = "name";
	
	 	Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort));

	    Page<Author> pagedAuthors = authorRepository.findAll(pageable);
	    
	 	assertAll(
			    () -> assertEquals(1, pagedAuthors.getTotalPages()),
			    () -> assertEquals(3, pagedAuthors.getNumberOfElements()),
			    () -> assertEquals("J. R. R. Tolkien", pagedAuthors.getContent().get(0).getName()),			    
			    () -> assertEquals("J. K. Rowling", pagedAuthors.getContent().get(1).getName()),
			    () -> assertEquals("George Orwell", pagedAuthors.getContent().get(2).getName())			   
	 		);		
	}	
}
