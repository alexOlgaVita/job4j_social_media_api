package ru.job4j;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.job4j.model.Post;
import ru.job4j.model.User;
import ru.job4j.repository.PostRepository;
import ru.job4j.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

/*
@SpringBootApplication
@AllArgsConstructor
 */
public class Job4jSocialMediaApiApplication implements CommandLineRunner {

    private UserRepository userRepository;
    private PostRepository postRepository;
    /*    private PhotoRepository photoRepository;*/

    public static void main(String[] args) {

        SpringApplication.run(Job4jSocialMediaApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello");
        userRepository.deleteAll();
        Set<Post> posts = new HashSet<>();
/*
        Set<User> friends = new HashSet<>();
        Set<User> potentialFriends = new HashSet<>();
 */
        postRepository.saveAll(posts);
        User user = new User(0L, "Alex", "email", "password",
                posts, new HashSet<>(), new HashSet<>(), TimeZone.getDefault().getDisplayName());

        /*        userRepository.save(user); */
        System.out.println(user);
        System.out.println(userRepository.findAll().iterator().hasNext());
        /* System.out.println(userRepository.findAll().iterator().next()); */
    }
}