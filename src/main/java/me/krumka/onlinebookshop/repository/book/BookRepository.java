package me.krumka.onlinebookshop.repository.book;

import java.util.Optional;
import me.krumka.onlinebookshop.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    Page<Book> findAllByCategoriesId(Long categoryId, Pageable pageable);

    //@Query("select b from Book b LEFT JOIN FETCH b.categories")
    @EntityGraph(attributePaths = "categories")
    Page<Book> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "categories")
    Page<Book> findAll(Specification<Book> spec, Pageable pageable);

    //@Query("select b from Book b LEFT JOIN FETCH b.categories where b.id = :id")
    @EntityGraph(attributePaths = "categories")
    Optional<Book> findById(Long id);
}
