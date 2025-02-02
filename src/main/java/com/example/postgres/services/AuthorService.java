package com.example.postgres.services;

import com.example.postgres.domain.Author;
import com.example.postgres.domain.dto.AuthorDto;
import com.example.postgres.repository.AuthorRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Author createAuthor(Author author);

    List<Author> finadAll();

    Optional<Author> findOne(Long id);

    boolean isExist(Long id);

    Author partialUpdate(Long id, Author authorDto);

    void delete(Long id);

}
