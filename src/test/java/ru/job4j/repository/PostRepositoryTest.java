package ru.job4j.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.Job4jSocialMediaApiApplication;
import ru.job4j.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    public void setUp() {
        postRepository.deleteAll();
    }

    @Test
    public void whenSavePostThenFindById() {
        var post = new Post();
        post.setName("Продажа книг");
        post.setDescription("Продаются книги...");
        post.setParticipates(Set.of());
        post.setCreated(LocalDateTime.now());
        postRepository.save(post);
        var foundPost = postRepository.findById(post.getId());
        assertThat(foundPost).isPresent();
        assertThat(foundPost.get().getName()).isEqualTo("Продажа книг");
    }

    @Test
    public void whenSomePostsThenFindAllReturnAllPosts() {
        var post1 = new Post();
        post1.setName("Продажа книг");
        post1.setDescription("Продаются книги...");
        post1.setParticipates(Set.of());
        post1.setCreated(LocalDateTime.now());
        var post2 = new Post();
        post2.setName("Рецепт сахарного печенья");
        post2.setDescription("Инградиенты...");
        post2.setParticipates(Set.of());
        post2.setCreated(LocalDateTime.now());
        postRepository.save(post1);
        postRepository.save(post2);
        var posts = postRepository.findAll();
        assertThat(posts).hasSize(2);
        assertThat(posts).extracting(Post::getName).contains("Продажа книг", "Рецепт сахарного печенья");
    }

    @Test
    public void whenNotPostsThenFindAllReturnNull() {
        var posts = postRepository.findAll();
        assertThat(posts).hasSize(0);
    }

    @Test
    public void whenFindByIdThenReturnOne() {
        var post1 = new Post();
        post1.setName("Продажа книг");
        post1.setDescription("Продаются книги...");
        post1.setParticipates(Set.of());
        post1.setCreated(LocalDateTime.now());
        var post2 = new Post();
        post2.setName("Рецепт сахарного печенья");
        post2.setDescription("Инградиенты...");
        post2.setParticipates(Set.of());
        post2.setCreated(LocalDateTime.now());
        postRepository.save(post1);
        postRepository.save(post2);
        assertThat(postRepository.findById(post1.getId()).get())
                .usingRecursiveComparison().comparingOnlyFields("name", "description")
                .isEqualTo(post1);
    }

    @Test
    public void whenNotPostIdFindByIdThenReturnNull() {
        assertThat(postRepository.findById(1L)).isEmpty();
    }

    @Test
    public void when2PostsThenCountReturn2() {
        var post1 = new Post();
        post1.setName("Продажа книг");
        post1.setDescription("Продаются книги...");
        post1.setParticipates(Set.of());
        post1.setCreated(LocalDateTime.now());
        var post2 = new Post();
        post2.setName("Рецепт сахарного печенья");
        post2.setDescription("Инградиенты...");
        post2.setParticipates(Set.of());
        post2.setCreated(LocalDateTime.now());
        postRepository.save(post1);
        postRepository.save(post2);
        assertThat(postRepository.count()).isEqualTo(2);
    }

    @Test
    public void whenSaveAllPostsThenFindAllReturnAllPosts() {
        var post1 = new Post();
        post1.setName("Продажа книг");
        post1.setDescription("Продаются книги...");
        post1.setParticipates(Set.of());
        post1.setCreated(LocalDateTime.now());
        var post2 = new Post();
        post2.setName("Рецепт сахарного печенья");
        post2.setDescription("Инградиенты...");
        post2.setParticipates(Set.of());
        post2.setCreated(LocalDateTime.now());
        postRepository.saveAll(List.of(post1, post2));
        var posts = postRepository.findAll();
        assertThat(posts).hasSize(2);
        assertThat(posts).extracting(Post::getName).contains("Продажа книг", "Рецепт сахарного печенья");
    }

    @Test
    public void whenSavePostsThenFindAllReturnTrue() {
        var post1 = new Post();
        post1.setName("Продажа книг");
        post1.setDescription("Продаются книги...");
        post1.setParticipates(Set.of());
        post1.setCreated(LocalDateTime.now());
        var post2 = new Post();
        post2.setName("Рецепт сахарного печенья");
        post2.setDescription("Инградиенты...");
        post2.setParticipates(Set.of());
        post2.setCreated(LocalDateTime.now());
        postRepository.save(post1);
        postRepository.save(post2);
        assertThat(postRepository.existsById(post1.getId())).isTrue();
        assertThat(postRepository.existsById(post2.getId())).isTrue();
    }

    @Test
    public void whenIsPostThenDeleteByIdOk() {
        var post1 = new Post();
        post1.setName("Продажа книг");
        post1.setDescription("Продаются книги...");
        post1.setParticipates(Set.of());
        post1.setCreated(LocalDateTime.now());
        var post2 = new Post();
        post2.setName("Рецепт сахарного печенья");
        post2.setDescription("Инградиенты...");
        post2.setParticipates(Set.of());
        post2.setCreated(LocalDateTime.now());
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.deleteById(post1.getId());
        assertThat(postRepository.existsById(post1.getId())).isFalse();
        assertThat(postRepository.existsById(post2.getId())).isTrue();
        postRepository.deleteById(post2.getId());
        assertThat(postRepository.existsById(post2.getId())).isFalse();
    }
}