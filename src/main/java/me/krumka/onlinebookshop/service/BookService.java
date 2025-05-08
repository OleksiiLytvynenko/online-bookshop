package me.krumka.onlinebookshop.service;

import java.util.List;
import me.krumka.onlinebookshop.model.Book;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
