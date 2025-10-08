package me.krumka.onlinebookshop.repository;

import me.krumka.onlinebookshop.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
