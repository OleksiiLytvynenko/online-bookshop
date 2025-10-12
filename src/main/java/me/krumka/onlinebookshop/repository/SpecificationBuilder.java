package me.krumka.onlinebookshop.repository;

import me.krumka.onlinebookshop.dto.BookSearchParametersDto;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {

    Specification<T> build(BookSearchParametersDto bookSearchParametersDto);
}
