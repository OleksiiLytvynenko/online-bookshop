package me.krumka.onlinebookshop.mapper;

import me.krumka.onlinebookshop.config.MapperConfig;
import me.krumka.onlinebookshop.dto.BookDto;
import me.krumka.onlinebookshop.dto.CreateBookRequestDto;
import me.krumka.onlinebookshop.dto.UpdateBookRequestDto;
import me.krumka.onlinebookshop.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toBookDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);

    void updateBookFromDto(UpdateBookRequestDto requestDto, @MappingTarget Book book);
}
