package com.masters.masters.exercise.services;

import com.masters.masters.exercise.exception.RecordNotFoundException;
import com.masters.masters.exercise.model.*;
import com.masters.masters.exercise.repository.AuthorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthorServiceTest {

    @Mock
    AuthorRepository repo;

    @InjectMocks
    AuthorServiceImpl authorService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveHappyPath(){
        AuthorDtoRequest authorDto = new AuthorDtoRequest();
        authorDto.setName("author1");
        Author repoResponse = new Author();
        repoResponse.setName("author1");
        repoResponse.setId(1L);
        when(repo.save(Mockito.any(Author.class))).thenReturn(repoResponse);
        Author author = authorService.saveAuthor(authorDto);
        verify(repo).save(Mockito.any(Author.class));
        Assertions.assertEquals("author1",author.getName());
        Assertions.assertNotNull(author.getId());
    }

    @Test
    public void findById() throws RecordNotFoundException {
        Author repoResponse = new Author();
        repoResponse.setName("author1");
        repoResponse.setId(Long.parseLong("1"));
        when(repo.findById(Mockito.anyLong())).thenReturn(Optional.of(repoResponse));
        Author author = authorService.findById(Long.parseLong("1"));
        verify(repo).findById(Mockito.anyLong());
        Assertions.assertEquals("author1",author.getName());
        Assertions.assertNotNull(author.getId());
    }

    @Test
    public void findByIdNoRecordFound(){
        when(repo.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        RecordNotFoundException thrown = Assertions.assertThrows(RecordNotFoundException.class, () -> {
            authorService.findById(Long.parseLong("99"));
        });
        Assertions.assertEquals("Author not found", thrown.getMessage());;

    }

}
