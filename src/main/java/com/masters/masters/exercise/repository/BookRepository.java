package com.masters.masters.exercise.repository;

import com.masters.masters.exercise.model.BookEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends PagingAndSortingRepository<BookEntity,Long> {
}
