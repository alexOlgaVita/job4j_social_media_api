package ru.job4j;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.job4j.repository.PostRepository;
import ru.job4j.repository.UserRepository;

@SpringBootApplication
@AllArgsConstructor
public class RestApplication {

    private UserRepository userRepository;
    private PostRepository postRepository;

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }
}
