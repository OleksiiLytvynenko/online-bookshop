package me.krumka.onlinebookshop.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.krumka.onlinebookshop.dto.book.BookDto;
import me.krumka.onlinebookshop.dto.book.BookSearchParametersDto;
import me.krumka.onlinebookshop.dto.book.CreateBookRequestDto;
import me.krumka.onlinebookshop.dto.book.UpdateBookRequestDto;
import me.krumka.onlinebookshop.exception.EntityNotFoundException;
import me.krumka.onlinebookshop.mapper.BookMapper;
import me.krumka.onlinebookshop.model.Book;
import me.krumka.onlinebookshop.model.Category;
import me.krumka.onlinebookshop.repository.book.BookRepository;
import me.krumka.onlinebookshop.repository.book.BookSpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

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
        List<Category> newCategories = requestDto.getCategoryIds().stream()
                .map(Category::new)
                .toList();
        book.getCategories().clear();
        book.getCategories().addAll(newCategories);
        return bookMapper.toBookDto(bookRepository.save(book));
    }

    @Override
    public Page<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::toBookDto);
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

    @Override
    public Page<BookDto> search(
            BookSearchParametersDto bookSearchParametersDto,
            Pageable pageable) {
        Specification<Book> bookSpecification = bookSpecificationBuilder
                .build(bookSearchParametersDto);
        return bookRepository.findAll(bookSpecification, pageable)
                .map(bookMapper::toBookDto);
    }
}
