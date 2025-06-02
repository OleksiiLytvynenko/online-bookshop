package me.krumka.onlinebookshop.service;

import java.util.List;
import me.krumka.onlinebookshop.dto.BookDto;
import me.krumka.onlinebookshop.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto book);

    List<BookDto> findAll();

    BookDto findBookById(Long id);
}
