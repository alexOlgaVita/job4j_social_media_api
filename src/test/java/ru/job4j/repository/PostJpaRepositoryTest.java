package ru.job4j.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.job4j.model.Post;
import ru.job4j.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PostJpaRepositoryTest {
    @Autowired
    PostJpaRepository postJpaRepository;

    @Autowired
    UserJpaRepository userJpaRepository;

    @Test
    public void whenFindByUser() throws ParseException {
        postJpaRepository.deleteAll();
        userJpaRepository.deleteAll();

        var user1 = new User();
        user1.setName("Olga");
        user1.setEmail("Olga@email.com");
        user1.setPassword("olgaPassword");
        userJpaRepository.save(user1);

        var user2 = new User();
        user2.setName("Sveta");
        user2.setEmail("Sveta@email.com");
        user2.setPassword("svetaPassword");
        userJpaRepository.save(user2);

        var post1 = new Post();
        post1.setName("Продажа книг");
        post1.setDescription("Продаются книги...");
        post1.setParticipates(Set.of());
        post1.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2025-03-11"));
        post1.setUser(user1);
        postJpaRepository.save(post1);

        var post2 = new Post();
        post2.setName("Рецепт печенья");
        post2.setDescription("Инградиенты...");
        post2.setParticipates(Set.of());
        post2.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2025-03-10"));
        post2.setUser(user2);
        postJpaRepository.save(post2);

        var post3 = new Post();
        post3.setName("Разбор на гитаре");
        post3.setDescription("Бой, аккорды...");
        post3.setParticipates(Set.of());
        post3.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2025-03-15"));
        post3.setUser(user1);
        postJpaRepository.save(post3);

        assertThat(
                postJpaRepository.findByUser(user1).size())
                .isEqualTo(2);
        assertThat(
                postJpaRepository.findByUser(user1).get(0))
                .isEqualTo(post1);
        assertThat(
                postJpaRepository.findByUser(user1).get(1))
                .isEqualTo(post3);
    }

    @Test
    public void whenFindByCreatedBetween() throws ParseException {
        postJpaRepository.deleteAll();
        var post1 = new Post();
        post1.setName("Продажа книг");
        post1.setDescription("Продаются книги...");
        post1.setParticipates(Set.of());
        post1.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2025-03-11"));
        postJpaRepository.save(post1);

        var post2 = new Post();
        post2.setName("Рецепт печенья");
        post2.setDescription("Инградиенты...");
        post2.setParticipates(Set.of());
        post2.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2025-03-10"));
        postJpaRepository.save(post2);

        var post3 = new Post();
        post3.setName("Разбор на гитаре");
        post3.setDescription("Бой, аккорды...");
        post3.setParticipates(Set.of());
        post3.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2025-03-15"));
        postJpaRepository.save(post3);

        assertThat(
                postJpaRepository.findByCreatedBetween(
                        new SimpleDateFormat("yyyy-MM-dd").parse("2025-03-10"),
                        new SimpleDateFormat("yyyy-MM-dd").parse("2025-03-14")).size())
                .isEqualTo(2);
        assertThat(
                postJpaRepository.findByCreatedBetween(
                        new SimpleDateFormat("yyyy-MM-dd").parse("2025-03-10"),
                        new SimpleDateFormat("yyyy-MM-dd").parse("2025-03-14")).get(0))
                .isEqualTo(post1);
        assertThat(
                postJpaRepository.findByCreatedBetween(
                        new SimpleDateFormat("yyyy-MM-dd").parse("2025-03-10"),
                        new SimpleDateFormat("yyyy-MM-dd").parse("2025-03-14")).get(1))
                .isEqualTo(post2);
    }

    @Test
    public void whenFindByOrderByCreatedAsc() throws ParseException {
        postJpaRepository.deleteAll();
        var post1 = new Post();
        post1.setName("Продажа книг");
        post1.setDescription("Продаются книги...");
        post1.setParticipates(Set.of());
        post1.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2025-03-11"));
        postJpaRepository.save(post1);

        var post2 = new Post();
        post2.setName("Рецепт печенья");
        post2.setDescription("Инградиенты...");
        post2.setParticipates(Set.of());
        post2.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2025-03-10"));
        postJpaRepository.save(post2);

        var post3 = new Post();
        post3.setName("Разбор на гитаре");
        post3.setDescription("Бой, аккорды...");
        post3.setParticipates(Set.of());
        post3.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2025-03-15"));
        postJpaRepository.save(post3);

        assertThat(
                postJpaRepository.findByOrderByCreatedAsc().get(0))
                .isEqualTo(post2);
        assertThat(
                postJpaRepository.findByOrderByCreatedAsc().get(1))
                .isEqualTo(post1);
        assertThat(
                postJpaRepository.findByOrderByCreatedAsc().get(2))
                .isEqualTo(post3);
    }

    @Test
    public void whenFindByOrderByCreatedDesc() throws ParseException {
        postJpaRepository.deleteAll();
        var post1 = new Post();
        post1.setName("Продажа книг");
        post1.setDescription("Продаются книги...");
        post1.setParticipates(Set.of());
        post1.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2025-03-11"));
        postJpaRepository.save(post1);

        var post2 = new Post();
        post2.setName("Рецепт печенья");
        post2.setDescription("Инградиенты...");
        post2.setParticipates(Set.of());
        post2.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2025-03-10"));
        postJpaRepository.save(post2);

        var post3 = new Post();
        post3.setName("Разбор на гитаре");
        post3.setDescription("Бой, аккорды...");
        post3.setParticipates(Set.of());
        post3.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2025-03-15"));
        postJpaRepository.save(post3);

        assertThat(
                postJpaRepository.findByOrderByCreatedDesc().get(0))
                .isEqualTo(post3);
        assertThat(
                postJpaRepository.findByOrderByCreatedDesc().get(1))
                .isEqualTo(post1);
        assertThat(
                postJpaRepository.findByOrderByCreatedDesc().get(2))
                .isEqualTo(post2);
    }

    @Test
    public void whenFindByName() throws ParseException {
        postJpaRepository.deleteAll();
        var post = new Post();
        post.setName("Продажа книг");
        post.setDescription("Продаются книги...");
        post.setParticipates(Set.of());
        post.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-11"));
        postJpaRepository.save(post);
        assertThat(
                postJpaRepository.findByName("Продажа книг")
                        .size())
                .isEqualTo(1);
    }

    @Test
    public void whenPage() throws ParseException {
        postJpaRepository.deleteAll();
        var post1 = new Post();
        post1.setName("Продажа книг");
        post1.setDescription("Продаются книги...");
        post1.setParticipates(Set.of());
        post1.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2025-03-11"));
        postJpaRepository.save(post1);

        var post2 = new Post();
        post2.setName("Рецепт печенья");
        post2.setDescription("Инградиенты...");
        post2.setParticipates(Set.of());
        post2.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2025-03-12"));
        postJpaRepository.save(post2);

        var post3 = new Post();
        post3.setName("Разбор на гитаре");
        post3.setDescription("Бой, аккорды...");
        post3.setParticipates(Set.of());
        post3.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2025-03-15"));
        postJpaRepository.save(post3);

        assertThat(
                postJpaRepository.findAll(Pageable.ofSize(1))
                        .getTotalPages())
                .isEqualTo(3);
    }
}