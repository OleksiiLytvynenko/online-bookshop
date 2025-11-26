package me.krumka.onlinebookshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import me.krumka.onlinebookshop.dto.BookDto;
import me.krumka.onlinebookshop.dto.BookSearchParametersDto;
import me.krumka.onlinebookshop.dto.CreateBookRequestDto;
import me.krumka.onlinebookshop.dto.UpdateBookRequestDto;
import me.krumka.onlinebookshop.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
@Tag(name = "Book API", description = "Endpoints for managing books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get all products", description = "Returns all products")
    public Page<BookDto> getAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get a book by id", description = "Returns a book by id")
    public BookDto getBookById(
            @PathVariable @Min(value = 1, message = "ID must be greater than or equal to 1") Long id
    ) {
        return bookService.findBookById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new book", description = "Creates a new book")
    public BookDto createBook(@RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a book by id", description = "Updates a book by id")
    public BookDto updateBook(
            @PathVariable @Min(
                    value = 1,
                    message = "ID must be greater than or equal to 1"
            ) Long id,
            @RequestBody @Valid UpdateBookRequestDto requestDto
    ) {
        return bookService.updateBookById(id, requestDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a book by id", description = "Deletes a book by id")
    public void delete(
            @PathVariable @Min(value = 1, message = "ID must be greater than or equal to 1") Long id
    ) {
        bookService.deleteById(id);
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER')")
    @Operation(
            summary = "Search books by parameters",
            description = "Searches books by parameters and returns a list of books")
    public Page<BookDto> search(
            BookSearchParametersDto bookSearchParametersDto,
            Pageable pageable) {
        return bookService.search(bookSearchParametersDto, pageable);
    }
}
