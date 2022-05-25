package com.masters.masters.exercise.repository;

import com.masters.masters.exercise.model.Author;
import com.masters.masters.exercise.model.BookEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    BookRepository repository;

    @Test
    public void saveHappyPath(){
        BookEntity entity = new BookEntity();
        entity.setTitle("title");
        BookEntity savedEntity = repository.save(entity);
        org.junit.jupiter.api.Assertions.assertEquals("title",savedEntity.getTitle());
    }

    @Test
    public void findAllHappyPath(){
        BookEntity entity1 = new BookEntity();
        BookEntity entity2 = new BookEntity();
        entity1.setTitle("title1");
        entity2.setTitle("title2");
        Iterable<BookEntity> entityList = repository.saveAll(List.of(entity1,entity2));
        Assertions.assertThat(repository.findAll()).containsAll(entityList);
    }

    @Test
    public void findAllNoRecords(){
        Assertions.assertThat(repository.findAll()).isEmpty();
    }

    @Test
    public void findbyId(){
        BookEntity entity = new BookEntity();
        entity.setDescription("description");
        entity.setTitle("title");
        entity.setAuthor(new Author());
        BookEntity savedEntity = repository.save(entity);
        Optional<BookEntity> existingBook = repository.findById(savedEntity.getId());
        org.junit.jupiter.api.Assertions.assertTrue(existingBook.isPresent());
        org.junit.jupiter.api.Assertions.assertEquals("title",existingBook.get().getTitle());
    }

}
