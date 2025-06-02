package me.krumka.onlinebookshop.repository;

import java.util.List;
import java.util.Optional;
import me.krumka.onlinebookshop.model.Book;

public interface BookRepository {
    Book save(Book book);

    Optional<Book> findById(Long id);

    List<Book> findAll();
}
