package com.example.postgres.repository;

import com.example.postgres.domain.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepo extends CrudRepository<BookEntity,String>,
        PagingAndSortingRepository<BookEntity,String> {

}
