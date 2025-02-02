package com.example.postgres.services.impl;

import com.example.postgres.domain.Author;
import com.example.postgres.domain.dto.AuthorDto;
import com.example.postgres.repository.AuthorRepo;
import com.example.postgres.services.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService {

        private AuthorRepo authorRepo;
        public AuthorServiceImpl(AuthorRepo authorRepo){
            this.authorRepo=authorRepo;
        }
        @Override
        public Author createAuthor(Author author) {
            return authorRepo.save(author) ;
        }

    @Override
    public List<Author> finadAll() {
        return StreamSupport.stream(authorRepo
                        .findAll()
                        .spliterator(),false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Author> findOne(Long id) {
        return  authorRepo.findById(id);
    }

    @Override
    public boolean isExist(Long id) {
        return authorRepo.existsById(id) ;
    }

    @Override
    public Author partialUpdate(Long id, Author authorEntity) {
        authorEntity.setId(id);
     return    authorRepo.findById(id).map(existingAuthor->{
            Optional.ofNullable(authorEntity.getName()).ifPresent(existingAuthor::setName);
            Optional.ofNullable(authorEntity.getAge()).ifPresent(existingAuthor::setAge);
           return  authorRepo.save(existingAuthor);
        }).orElseThrow(() -> new RuntimeException("author does not exist"));
    }

    @Override
    public void delete(Long id) {
        authorRepo.deleteById(id);
    }

}

