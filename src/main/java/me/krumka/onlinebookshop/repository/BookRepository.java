package me.krumka.onlinebookshop.repository;

import java.util.List;
import me.krumka.onlinebookshop.model.Book;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}
