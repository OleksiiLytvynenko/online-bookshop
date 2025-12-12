package me.krumka.onlinebookshop.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import me.krumka.onlinebookshop.config.MapperConfig;
import me.krumka.onlinebookshop.dto.book.BookDto;
import me.krumka.onlinebookshop.dto.book.BookDtoWithoutCategoryIds;
import me.krumka.onlinebookshop.dto.book.CreateBookRequestDto;
import me.krumka.onlinebookshop.dto.book.UpdateBookRequestDto;
import me.krumka.onlinebookshop.model.Book;
import me.krumka.onlinebookshop.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    @Mapping(target = "categoryIds", ignore = true)
    BookDto toBookDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);

    @AfterMapping
    default void setCategories(CreateBookRequestDto requestDto, @MappingTarget Book book) {
        Set<Category> categories = requestDto.getCategoryIds().stream()
                .map(Category::new)
                .collect(Collectors.toSet());
        book.setCategories(categories);
    }

    @Mapping(target = "categories", ignore = true)
    void updateBookFromDto(UpdateBookRequestDto requestDto, @MappingTarget Book book);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        if (book.getCategories() != null) {
            List<Long> categoryIds = book.getCategories().stream()
                    .map(Category::getId)
                    .collect(Collectors.toList());
            bookDto.setCategoryIds(categoryIds);
        }
    }
}
