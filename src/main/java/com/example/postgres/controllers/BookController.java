package com.example.postgres.controllers;

import com.example.postgres.Mappers.Mapper;
import com.example.postgres.domain.BookEntity;
import com.example.postgres.domain.dto.BookDto;
import com.example.postgres.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private Mapper<BookEntity,BookDto> mapper;
     private BookService bookService;
    public BookController(Mapper<BookEntity, BookDto> mapper,BookService bookService) {
        this.mapper = mapper;
        this.bookService=bookService;
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(@PathVariable("isbn")String isbn, @RequestBody BookDto bookDto ){
        BookEntity bookEntity= mapper.mapfrom(bookDto);
        boolean bookexist=bookService.isExist(isbn);
        BookEntity savedbookEntity=bookService.createUpdatebook(isbn,bookEntity);
        BookDto savedBookdto=mapper.mapto(savedbookEntity);
        if(bookexist){
            return new ResponseEntity<>(savedBookdto,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(savedBookdto,HttpStatus.CREATED);
        }
    }
    @PatchMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto>partialUpdate(@PathVariable("isbn")String isbn, @RequestBody BookDto bookDto ){
        if(!bookService.isExist(isbn)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BookEntity bookEntity=mapper.mapfrom(bookDto);
        BookEntity updatedBookeEntity=  bookService.partialUpdate(isbn,bookEntity);
       return new ResponseEntity<BookDto>(mapper.mapto(updatedBookeEntity),HttpStatus.OK);
    }
    @GetMapping(path = "/books")
    public Page<BookDto> listBook(Pageable pageable){
        Page<BookEntity> books=bookService.findAll(pageable);
        return books.map(mapper::mapto);
   }

   @GetMapping(path = "/books/{isbn}")
   public ResponseEntity<BookDto> findOneBook(@PathVariable("isbn")String isbn){
      Optional<BookEntity> foundbook=bookService.findOne(isbn);
      return foundbook.map(bookEntity ->{
          BookDto bookDto=mapper.mapto(bookEntity);
       return new ResponseEntity<>(bookDto,HttpStatus.OK);
      }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND))
      ;

   }
   @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity deleteBook(@PathVariable("isbn")String isbn){
        bookService.delete(isbn);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
   }
}
