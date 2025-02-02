package com.example.postgres.services.impl;

import com.example.postgres.domain.BookEntity;
import com.example.postgres.repository.BookRepo;
import com.example.postgres.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {

    private BookRepo bookRepo;

    public BookServiceImpl(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public BookEntity createUpdatebook(String isbn, BookEntity book) {
       book.setIsbn(isbn);
        return bookRepo.save(book);
    }

    @Override
    public List<BookEntity> findAll() {
         return StreamSupport.stream(
                 bookRepo.findAll().spliterator(),false
         ).collect(Collectors.toList());
    }

    @Override
    public Page<BookEntity> findAll(Pageable pageable) {
       return  bookRepo.findAll(pageable);
    }

    @Override
    public Optional<BookEntity> findOne(String isbn) {
        return bookRepo.findById(isbn);
    }

    @Override
    public boolean isExist(String isbn) {
        return bookRepo.existsById(isbn);
    }

    @Override
    public BookEntity partialUpdate(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);
      return   bookRepo.findById(isbn).map(existingBook ->{
            Optional.ofNullable(bookEntity.getAuthor()).ifPresent(existingBook::setAuthor);
            Optional.ofNullable(bookEntity.getTitle()).ifPresent(existingBook::setTitle);
            return bookRepo.save(existingBook);
        }).orElseThrow(()->new RuntimeException("book does not exist"));
    }

    @Override
    public void delete(String isbn) {
        bookRepo.deleteById(isbn);
    }

}
