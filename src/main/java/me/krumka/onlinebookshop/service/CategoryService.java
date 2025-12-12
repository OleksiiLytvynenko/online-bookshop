package me.krumka.onlinebookshop.service;

import me.krumka.onlinebookshop.dto.book.BookDtoWithoutCategoryIds;
import me.krumka.onlinebookshop.dto.category.CategoryResponseDto;
import me.krumka.onlinebookshop.dto.category.CreateCategoryRequestDto;
import me.krumka.onlinebookshop.dto.category.UpdateCategoryRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    Page<CategoryResponseDto> findAll(Pageable pageable);

    CategoryResponseDto getById(Long id);

    CategoryResponseDto save(CreateCategoryRequestDto requestDto);

    CategoryResponseDto update(Long id, UpdateCategoryRequestDto requestDto);

    void deleteById(Long id);

    Page<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long categoryId, Pageable pageable);
}
