package me.krumka.onlinebookshop.service;

import me.krumka.onlinebookshop.dto.BookDto;
import me.krumka.onlinebookshop.dto.BookSearchParametersDto;
import me.krumka.onlinebookshop.dto.CreateBookRequestDto;
import me.krumka.onlinebookshop.dto.UpdateBookRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto book);

    BookDto updateBookById(Long id, UpdateBookRequestDto requestDto);

    Page<BookDto> findAll(Pageable pageable);

    BookDto findBookById(Long id);

    void deleteById(Long id);

    Page<BookDto> search(BookSearchParametersDto bookSearchParametersDto, Pageable pageable);
}
