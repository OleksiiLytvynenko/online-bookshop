package me.krumka.onlinebookshop.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.krumka.onlinebookshop.exception.SpecificationNotFoundException;
import me.krumka.onlinebookshop.model.Book;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationProviderManagerImpl implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> bookSpecificationProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return bookSpecificationProviders.stream()
                .filter(p -> p.getKey().equals(key))
                .findFirst()
                .orElseThrow(
                        () -> new SpecificationNotFoundException(
                                "No specification provider found for key: " + key
                        )
                );
    }
}
