package me.krumka.onlinebookshop.repository.book.spec;

import java.util.Arrays;
import me.krumka.onlinebookshop.model.Book;
import me.krumka.onlinebookshop.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class IsbnSpecificationProvider implements SpecificationProvider<Book> {
    private static final String ISBN_FIELD = "isbn";

    @Override
    public String getKey() {
        return ISBN_FIELD;
    }

    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> root.get(ISBN_FIELD)
              .in(Arrays.stream(params).toArray());
    }
}
