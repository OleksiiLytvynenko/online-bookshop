package me.krumka.onlinebookshop.repository.book.spec;

import java.util.Arrays;
import me.krumka.onlinebookshop.model.Book;
import me.krumka.onlinebookshop.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AuthorSpecificationProvider implements SpecificationProvider<Book> {
    private static final String AUTHOR_FIELD = "author";

    @Override
    public String getKey() {
        return AUTHOR_FIELD;
    }

    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> root.get(AUTHOR_FIELD)
                .in(Arrays.stream(params).toArray());
    }
}
