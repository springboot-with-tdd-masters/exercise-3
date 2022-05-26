package com.brownbag.exercise3.repository;

import com.brownbag.exercise3.entity.Author;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends PagingAndSortingRepository<Author, Integer> {
}
