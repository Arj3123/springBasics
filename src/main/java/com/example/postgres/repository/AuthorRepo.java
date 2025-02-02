package com.example.postgres.repository;

import com.example.postgres.domain.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepo extends CrudRepository<Author,Long> {
    Iterable<Author>ageLessThan(int age);
@Query("SELECT a from Author a where a.age > ?1")
    Iterable<Author> GreaterThan(int age);
}
