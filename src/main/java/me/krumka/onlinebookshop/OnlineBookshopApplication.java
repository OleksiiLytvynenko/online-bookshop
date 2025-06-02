package me.krumka.onlinebookshop;

import lombok.RequiredArgsConstructor;
import me.krumka.onlinebookshop.service.BookService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class OnlineBookshopApplication {
    private final BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(OnlineBookshopApplication.class, args);
    }
}
