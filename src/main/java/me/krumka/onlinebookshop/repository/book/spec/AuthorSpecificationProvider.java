package me.krumka.onlinebookshop.repository.book;

import java.util.Arrays;
import me.krumka.onlinebookshop.model.Book;
import me.krumka.onlinebookshop.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AuthorSpecificationProvider implements SpecificationProvider<Book> {

    @Override
    public String getKey() {
        return "author";
    }

    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> root.get("author")
                .in(Arrays.stream(params).toArray());
    }
}
