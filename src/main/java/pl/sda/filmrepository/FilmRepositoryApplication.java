package pl.sda.filmrepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FilmRepositoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilmRepositoryApplication.class, args);
    }

}
