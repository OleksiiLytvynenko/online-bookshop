package me.krumka.onlinebookshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import me.krumka.onlinebookshop.dto.book.BookDtoWithoutCategoryIds;
import me.krumka.onlinebookshop.dto.category.CategoryResponseDto;
import me.krumka.onlinebookshop.dto.category.CreateCategoryRequestDto;
import me.krumka.onlinebookshop.dto.category.UpdateCategoryRequestDto;
import me.krumka.onlinebookshop.service.BookService;
import me.krumka.onlinebookshop.service.CategoryService;
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

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Tag(name = "Category API", description = "Endpoints for managing categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new category", description = "Creates a new category")
    public CategoryResponseDto createCategory(
            @RequestBody @Valid CreateCategoryRequestDto createCategoryRequestDto) {
        return categoryService.save(createCategoryRequestDto);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get all categories", description = "Returns all categories")
    public Page<CategoryResponseDto> getAllCategories(Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get a category by id", description = "Returns a category by id")
    public CategoryResponseDto getCategoryById(
            @PathVariable
            @Min(value = 1, message = "ID must be greater than or equal to 1")
            Long id
    ) {
        return categoryService.getById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a category by id", description = "Updates a category by id")
    public CategoryResponseDto updateCategory(
            @PathVariable
            @Min(value = 1, message = "ID must be greater than or equal to 1") Long id,
            @RequestBody
            @Valid
            UpdateCategoryRequestDto updateCategoryRequestDto
    ) {
        return categoryService.update(id, updateCategoryRequestDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a category by id", description = "Deletes a category by id")
    public void deleteCategory(
            @PathVariable
            @Min(value = 1, message = "ID must be greater than or equal to 1")
            Long id
    ) {
        categoryService.deleteById(id);
    }

    @GetMapping("/{id}/books")
    @PreAuthorize("hasRole('USER')")
    @Operation(
            summary = "Get books by category id",
            description = "Returns books by category id"
    )
    public Page<BookDtoWithoutCategoryIds> getBooksByCategoryId(
            @PathVariable
            @Min(value = 1, message = "ID must be greater than or equal to 1")
            Long id,
            Pageable pageable
    ) {
        return categoryService.getBooksByCategoryId(id, pageable);
    }
}
