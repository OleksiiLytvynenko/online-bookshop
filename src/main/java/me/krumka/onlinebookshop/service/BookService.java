package me.krumka.onlinebookshop.service;

import java.util.List;
import me.krumka.onlinebookshop.dto.BookDto;
import me.krumka.onlinebookshop.dto.CreateBookRequestDto;
import me.krumka.onlinebookshop.dto.UpdateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto book);

    BookDto updateBookById(Long id, UpdateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto findBookById(Long id);

    void deleteById(Long id);
}
