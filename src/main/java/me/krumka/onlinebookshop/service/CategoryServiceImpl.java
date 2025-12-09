package me.krumka.onlinebookshop.service;

import lombok.RequiredArgsConstructor;
import me.krumka.onlinebookshop.dto.book.BookDtoWithoutCategoryIds;
import me.krumka.onlinebookshop.dto.category.CategoryResponseDto;
import me.krumka.onlinebookshop.dto.category.CreateCategoryRequestDto;
import me.krumka.onlinebookshop.dto.category.UpdateCategoryRequestDto;
import me.krumka.onlinebookshop.exception.EntityNotFoundException;
import me.krumka.onlinebookshop.mapper.BookMapper;
import me.krumka.onlinebookshop.mapper.CategoryMapper;
import me.krumka.onlinebookshop.model.Book;
import me.krumka.onlinebookshop.model.Category;
import me.krumka.onlinebookshop.repository.book.BookRepository;
import me.krumka.onlinebookshop.repository.category.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public Page<CategoryResponseDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toDto);
    }

    @Override
    public CategoryResponseDto getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Category not found by id: " + id)
        );
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryResponseDto save(CreateCategoryRequestDto requestDto) {
        Category category = categoryMapper.toEntity(requestDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryResponseDto update(Long id, UpdateCategoryRequestDto requestDto) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Category not found by id: " + id)
        );
        categoryMapper.updateCategoryFromDto(requestDto, category);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Page<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long id, Pageable pageable) {
        categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Category not found by id: " + id)
        );
        Page<Book> books = bookRepository.findAllByCategoriesId(id, pageable);
        return books.map(bookMapper::toDtoWithoutCategories);
    }
}
