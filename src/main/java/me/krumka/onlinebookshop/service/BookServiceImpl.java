package me.krumka.onlinebookshop.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.krumka.onlinebookshop.dto.BookDto;
import me.krumka.onlinebookshop.dto.CreateBookRequestDto;
import me.krumka.onlinebookshop.dto.UpdateBookRequestDto;
import me.krumka.onlinebookshop.exception.EntityNotFoundException;
import me.krumka.onlinebookshop.mapper.BookMapper;
import me.krumka.onlinebookshop.model.Book;
import me.krumka.onlinebookshop.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toBookDto(bookRepository.save(book));
    }

    @Override
    public BookDto updateBookById(Long id, UpdateBookRequestDto requestDto) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Book not found with id " + id)
        );
        bookMapper.updateBookFromDto(requestDto, book);
        return bookMapper.toBookDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toBookDto)
                .toList();
    }

    @Override
    public BookDto findBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Cannot find book by id " + id)
        );
        return bookMapper.toBookDto(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
