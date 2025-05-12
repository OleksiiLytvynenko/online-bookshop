package me.krumka.onlinebookshop;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import me.krumka.onlinebookshop.model.Book;
import me.krumka.onlinebookshop.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class OnlineBookshopApplication {
    private final BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(OnlineBookshopApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book myBook = new Book();
            myBook.setTitle("I Am a Programmer");
            myBook.setAuthor("Krumka");
            myBook.setPrice(BigDecimal.valueOf(299));
            myBook.setIsbn("978-3-16-148410-0");

            bookService.save(myBook);

            System.out.println(bookService.findAll());
        };
    }
}
