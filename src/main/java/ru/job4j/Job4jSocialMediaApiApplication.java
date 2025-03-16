package ru.job4j;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.job4j.model.Post;
import ru.job4j.model.User;
import ru.job4j.repository.PhotoRepository;
import ru.job4j.repository.PostRepository;
import ru.job4j.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@AllArgsConstructor
public class Job4jSocialMediaApiApplication implements CommandLineRunner {

    private UserRepository userRepository;
    private PostRepository postRepository;
    private PhotoRepository photoRepository;

    public static void main(String[] args) {
        SpringApplication.run(Job4jSocialMediaApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();
        List<Post> participates = new ArrayList<>();
        Set<User> friends = new HashSet<>();
        Set<User> friendLoans = new HashSet<>();

        User user = new User(0L, "Alex", "email", "password",
                participates, friends, friendLoans, null);
        userRepository.save(user);
        System.out.println(user);
        System.out.println(userRepository.findAll().iterator().hasNext());
        System.out.println(userRepository.findAll().iterator().next());
    }
}