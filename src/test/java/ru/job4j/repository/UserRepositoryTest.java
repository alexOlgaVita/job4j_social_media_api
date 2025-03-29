package ru.job4j.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.model.Post;
import ru.job4j.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    public void setUp() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void whenSaveUserThenFindById() {
        var user = new User();
        user.setName("просто Вася");
        user.setEmail("vasya@mail.com");
        user.setPassword("password");
        userRepository.save(user);
        var foundUser = userRepository.findById(user.getId());
        Assertions.assertThat(foundUser).isPresent();
        Assertions.assertThat(foundUser.get().getName()).isEqualTo("просто Вася");
    }

    @Test
    public void whenSomeUsersThenFindAllReturnAllUsers() {
        var user1 = new User();
        user1.setName("John Doe");
        user1.setEmail("john.doe@example.com");
        user1.setPassword("password");
        var user2 = new User();
        user2.setName("Jane Doe");
        user2.setEmail("jane.doe@example.com");
        user2.setPassword("password");
        userRepository.save(user1);
        userRepository.save(user2);
        var users = userRepository.findAll();
        Assertions.assertThat(users).hasSize(2);
        Assertions.assertThat(users).extracting(User::getName).contains("John Doe", "Jane Doe");
    }

    @Test
    public void whenNotUsersThenFindAllReturnNull() {
        var users = userRepository.findAll();
        Assertions.assertThat(users).hasSize(0);
    }

    @Test
    public void whenFindByIdThenReturnOne() {
        var user1 = new User();
        user1.setName("John Doe");
        user1.setEmail("john.doe@example.com");
        user1.setPassword("password");
        /* user1.setPosts(Set.of()); */
        var user2 = new User();
        user2.setName("Jane Doe");
        user2.setEmail("jane.doe@example.com");
        user2.setPassword("password");
        /* user2.setPosts(Set.of());*/
        userRepository.save(user1);
        userRepository.save(user2);
        Assertions.assertThat(userRepository.findById(user1.getId()).get().getName()).isEqualTo(user1.getName());
        Assertions.assertThat(userRepository.findById(user1.getId()).get().getEmail()).isEqualTo(user1.getEmail());
        Assertions.assertThat(userRepository.findById(user1.getId()).get().getPassword()).isEqualTo(user1.getPassword());
    }

    @Test
    public void whenNotUserIdFindByIdThenReturnNull() {
        Assertions.assertThat(userRepository.findById(1L)).isEmpty();
    }

    @Test
    public void when2UsersThenCountReturn2() {
        var user1 = new User();
        user1.setName("John Doe");
        user1.setEmail("john.doe@example.com");
        user1.setPassword("password");
        var user2 = new User();
        user2.setName("Jane Doe");
        user2.setEmail("jane.doe@example.com");
        user2.setPassword("password");
        userRepository.save(user1);
        userRepository.save(user2);
        assertThat(userRepository.count()).isEqualTo(2);
    }

    @Test
    public void whenSaveAllUsersThenFindAllReturnAllUsers() {
        var user1 = new User();
        user1.setName("John Doe");
        user1.setEmail("john.doe@example.com");
        user1.setPassword("password");
        var user2 = new User();
        user2.setName("Jane Doe");
        user2.setEmail("jane.doe@example.com");
        user2.setPassword("password");
        userRepository.saveAll(List.of(user1, user2));
        var users = userRepository.findAll();
        Assertions.assertThat(users).hasSize(2);
        Assertions.assertThat(users).extracting(User::getName).contains("John Doe", "Jane Doe");
    }

    @Test
    public void whenSaveUsersThenFindAllReturnTrue() {
        assertThat(userRepository.existsById(1L)).isFalse();
        var user1 = new User();
        user1.setName("John Doe");
        user1.setEmail("john.doe@example.com");
        user1.setPassword("password");
        var user2 = new User();
        user2.setName("Jane Doe");
        user2.setEmail("jane.doe@example.com");
        user2.setPassword("password");
        userRepository.save(user1);
        userRepository.save(user2);
        assertThat(userRepository.existsById(user1.getId())).isTrue();
        assertThat(userRepository.existsById(user2.getId())).isTrue();
    }

    @Test
    public void whenIsUserThenDeleteByIdOk() {
        var user1 = new User();
        user1.setName("John Doe");
        user1.setEmail("john.doe@example.com");
        user1.setPassword("password");
        var user2 = new User();
        user2.setName("Jane Doe");
        user2.setEmail("jane.doe@example.com");
        user2.setPassword("password");
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.deleteById(user1.getId());
        assertThat(userRepository.existsById(user1.getId())).isFalse();
        assertThat(userRepository.existsById(user2.getId())).isTrue();
        userRepository.deleteById(user2.getId());
        assertThat(userRepository.existsById(user2.getId())).isFalse();
    }

    @Test
    public void whenAreSubscribersThenFindAllSubscribersOk() {
        var user1 = new User();
        user1.setName("John Doe");
        user1.setEmail("john.doe@example.com");
        user1.setPassword("password");
        var user2 = new User();
        user2.setName("Jane Doe");
        user2.setEmail("jane.doe@example.com");
        user2.setPassword("password");
        var user3 = new User();
        user3.setName("Olga");
        user3.setEmail("olga@example.com");
        user3.setPassword("olgaPassword");
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        var user4 = new User();
        user4.setName("Sveta");
        user4.setEmail("sveta@example.com");
        user4.setPassword("svetaPassword");
        user4.setFriends(Set.of(user1));
        user4.setPotentialFriends(Set.of(user2, user3));
        userRepository.save(user4);

        var subscribers = userRepository.findAllSubscribers(user4.getName(), user4.getPassword());
        assertThat(subscribers.size()).isEqualTo(2);
        assertThat(subscribers.get(0).getName()).isEqualTo("Jane Doe");
        assertThat(subscribers.get(1).getName()).isEqualTo("Olga");
    }

    @Test
    public void whenAreFriendsThenFindAllFriendsOk() {
        var user1 = new User();
        user1.setName("John Doe");
        user1.setEmail("john.doe@example.com");
        user1.setPassword("password");
        var user2 = new User();
        user2.setName("Jane Doe");
        user2.setEmail("jane.doe@example.com");
        user2.setPassword("password");
        var user3 = new User();
        user3.setName("Olga");
        user3.setEmail("olga@example.com");
        user3.setPassword("olgaPassword");
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        var user4 = new User();
        user4.setName("Sveta");
        user4.setEmail("sveta@example.com");
        user4.setPassword("svetaPassword");
        user4.setFriends(Set.of(user1, user3));
        user4.setPotentialFriends(Set.of(user2, user3));
        userRepository.save(user4);

        var subscribers = userRepository.findAllFriends(user4.getName(), user4.getPassword());
        assertThat(subscribers.size()).isEqualTo(2);
        assertThat(subscribers.get(0).getName()).isEqualTo("John Doe");
        assertThat(subscribers.get(1).getName()).isEqualTo("Olga");
    }

    @Test
    public void whenAreSubscribersWithPostsThenFindAllSubscribersPostsOk() throws ParseException {
        var user1 = new User();
        user1.setName("John Doe");
        user1.setEmail("john.doe@example.com");
        user1.setPassword("password");

        var user2 = new User();
        user2.setName("Jane Doe");
        user2.setEmail("jane.doe@example.com");
        user2.setPassword("password");
        userRepository.save(user2);

        var user3 = new User();
        user3.setName("Olga");
        user3.setEmail("olga@example.com");
        user3.setPassword("olgaPassword");

        userRepository.save(user1);
        var post1 = new Post();
        post1.setUser(user1);
        post1.setName("Продажа книг");
        post1.setDescription("Продаются книги...");
        post1.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-11"));
        postRepository.save(post1);

        var post11 = new Post();
        post11.setUser(user1);
        post11.setName("Полезные советы");
        post11.setDescription("Полезные советы для студентов...");
        post11.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-12"));
        postRepository.save(post11);
        user1.setPosts(Set.of(post1, post11));

        userRepository.save(user3);

        var post2 = new Post();
        post2.setUser(user2);
        post2.setName("Творческий вечер");
        post2.setDescription("Приглашаем на творческий вечер...");
        post2.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-11"));
        postRepository.save(post2);
        user2.setPosts(Set.of(post2));
        userRepository.save(user2);

        user1.setPotentialFriends(Set.of(user2, user3));
        userRepository.save(user1);

        var user4 = new User();
        user4.setName("Sveta");
        user4.setEmail("sveta@example.com");
        user4.setPassword("svetaPassword");
        user4.setFriends(Set.of(user1, user3));
        user4.setPotentialFriends(Set.of(user1, user3));
        userRepository.save(user4);

        var posts1 = postRepository.findAllSubSubscribersPosts(user4.getName(), user4.getPassword());
        assertThat(posts1.size()).isEqualTo(2);
        assertThat(posts1.get(0).getName()).isEqualTo("Продажа книг");
        assertThat(posts1.get(1).getName()).isEqualTo("Полезные советы");
        var posts2 = postRepository.findAllSubSubscribersPosts(user1.getName(), user1.getPassword());
        assertThat(posts2.size()).isEqualTo(1);
        assertThat(posts2.get(0).getName()).isEqualTo("Творческий вечер");
    }
}