package com.masters.masters.exercise.repository;

import com.masters.masters.exercise.model.BookEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends PagingAndSortingRepository<BookEntity,Long> {

    List<BookEntity> findByTitleContaining(String title);
}
