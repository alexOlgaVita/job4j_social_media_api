package ru.job4j.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.Job4jSocialMediaApiApplication;
import ru.job4j.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
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
}