package me.krumka.onlinebookshop.service;

import me.krumka.onlinebookshop.dto.book.BookDto;
import me.krumka.onlinebookshop.dto.book.BookSearchParametersDto;
import me.krumka.onlinebookshop.dto.book.CreateBookRequestDto;
import me.krumka.onlinebookshop.dto.book.UpdateBookRequestDto;
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
