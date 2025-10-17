package me.krumka.onlinebookshop.repository;

import me.krumka.onlinebookshop.model.Book;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationProvider<T> {

    String getKey();

    Specification<Book> getSpecification(String[] params);
}
