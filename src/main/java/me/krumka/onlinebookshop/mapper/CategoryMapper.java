package me.krumka.onlinebookshop.mapper;

import me.krumka.onlinebookshop.config.MapperConfig;
import me.krumka.onlinebookshop.dto.category.CategoryResponseDto;
import me.krumka.onlinebookshop.dto.category.CreateCategoryRequestDto;
import me.krumka.onlinebookshop.dto.category.UpdateCategoryRequestDto;
import me.krumka.onlinebookshop.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {

    CategoryResponseDto toDto(Category category);

    Category toEntity(CreateCategoryRequestDto createCategoryRequestDto);

    void updateCategoryFromDto(
            UpdateCategoryRequestDto updateCategoryRequestDto,
            @MappingTarget Category category
            );
}
