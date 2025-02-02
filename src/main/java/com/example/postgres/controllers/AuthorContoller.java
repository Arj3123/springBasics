package com.example.postgres.controllers;

import com.example.postgres.Mappers.Mapper;
import com.example.postgres.domain.Author;
import com.example.postgres.domain.dto.AuthorDto;
import com.example.postgres.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AuthorContoller {
    private AuthorService authorService;
    private Mapper<Author, AuthorDto> authorMapper;

    public AuthorContoller(AuthorService authorService, Mapper<Author, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) {
        Author authorEntity = authorMapper.mapfrom(author);
        Author savedAuthorEntity = authorService.createAuthor(authorEntity);
        return new ResponseEntity<>(authorMapper.mapto(savedAuthorEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/authors")
    public List<AuthorDto> listAuhors() {
        List<Author> authors = authorService.finadAll();
        return authors.stream().map(authorMapper::mapto).collect(Collectors.toList());
    }

    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") Long id) {
        Optional<Author> foundAuthor = authorService.findOne(id);
        return foundAuthor.map(author -> {
            AuthorDto authorDto = authorMapper.mapto(author);
            return new ResponseEntity<>(authorDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> fullupdate(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto) {
        if (!authorService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        authorDto.setId(id);
        Author authorEntity = authorMapper.mapfrom(authorDto);
        Author savedaAthor = authorService.createAuthor(authorEntity);
        return new ResponseEntity<>(authorMapper.mapto(savedaAthor), HttpStatus.OK);
    }

    @PatchMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> partialUpdate(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto) {
        if (!authorService.isExist(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Author authorEntity = authorMapper.mapfrom(authorDto);
        Author updatedAuthor = authorService.partialUpdate(id, authorEntity);
        return new ResponseEntity<AuthorDto>(authorMapper.mapto(updatedAuthor), HttpStatus.OK);
    }
    @DeleteMapping(path =  "/authors/{id}")
    public ResponseEntity deleteAuthor(@PathVariable("id")Long id){
         authorService.delete(id);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
