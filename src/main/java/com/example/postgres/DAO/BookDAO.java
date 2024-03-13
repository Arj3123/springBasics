package com.example.postgres.DAO;

import com.example.postgres.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDAO {
    void crete(Book book);
    Optional<Book> find(String isbn);
    List<Book> find();
    void update(String isbn,Book book);

    void delete(String isbn);
}
