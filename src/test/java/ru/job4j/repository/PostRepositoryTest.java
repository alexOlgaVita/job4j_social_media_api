package ru.job4j.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.model.Post;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public void whenSavePostThenFindById() throws ParseException {
        var post = new Post();
        post.setName("Продажа книг");
        post.setDescription("Продаются книги...");
        post.setParticipates(Set.of());
        post.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-11"));
        postRepository.save(post);
        var foundPost = postRepository.findById(post.getId());
        assertThat(foundPost).isPresent();
        assertThat(foundPost.get().getName()).isEqualTo("Продажа книг");
    }

    @Test
    public void whenSomePostsThenFindAllReturnAllPosts() throws ParseException {
        var post1 = new Post();
        post1.setName("Продажа книг");
        post1.setDescription("Продаются книги...");
        post1.setParticipates(Set.of());
        post1.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-11"));
        var post2 = new Post();
        post2.setName("Рецепт сахарного печенья");
        post2.setDescription("Инградиенты...");
        post2.setParticipates(Set.of());
        post2.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-11"));
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
    public void whenFindByIdThenReturnOne() throws ParseException {
        var post1 = new Post();
        post1.setName("Продажа книг");
        post1.setDescription("Продаются книги...");
        post1.setParticipates(Set.of());
        post1.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-11"));
        var post2 = new Post();
        post2.setName("Рецепт сахарного печенья");
        post2.setDescription("Инградиенты...");
        post2.setParticipates(Set.of());
        post2.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-11"));
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
    public void when2PostsThenCountReturn2() throws ParseException {
        var post1 = new Post();
        post1.setName("Продажа книг");
        post1.setDescription("Продаются книги...");
        post1.setParticipates(Set.of());
        post1.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-11"));
        var post2 = new Post();
        post2.setName("Рецепт сахарного печенья");
        post2.setDescription("Инградиенты...");
        post2.setParticipates(Set.of());
        post2.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-11"));
        postRepository.save(post1);
        postRepository.save(post2);
        assertThat(postRepository.count()).isEqualTo(2);
    }

    @Test
    public void whenSaveAllPostsThenFindAllReturnAllPosts() throws ParseException {
        var post1 = new Post();
        post1.setName("Продажа книг");
        post1.setDescription("Продаются книги...");
        post1.setParticipates(Set.of());
        post1.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-11"));
        var post2 = new Post();
        post2.setName("Рецепт сахарного печенья");
        post2.setDescription("Инградиенты...");
        post2.setParticipates(Set.of());
        post2.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-11"));
        postRepository.saveAll(List.of(post1, post2));
        var posts = postRepository.findAll();
        assertThat(posts).hasSize(2);
        assertThat(posts).extracting(Post::getName).contains("Продажа книг", "Рецепт сахарного печенья");
    }

    @Test
    public void whenSavePostsThenFindAllReturnTrue() throws ParseException {
        var post1 = new Post();
        post1.setName("Продажа книг");
        post1.setDescription("Продаются книги...");
        post1.setParticipates(Set.of());
        post1.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-11"));
        var post2 = new Post();
        post2.setName("Рецепт сахарного печенья");
        post2.setDescription("Инградиенты...");
        post2.setParticipates(Set.of());
        post2.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-11"));
        postRepository.save(post1);
        postRepository.save(post2);
        assertThat(postRepository.existsById(post1.getId())).isTrue();
        assertThat(postRepository.existsById(post2.getId())).isTrue();
    }

    @Test
    public void whenIsPostThenDeleteByIdOk() throws ParseException {
        var post1 = new Post();
        post1.setName("Продажа книг");
        post1.setDescription("Продаются книги...");
        post1.setParticipates(Set.of());
        post1.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-11"));
        var post2 = new Post();
        post2.setName("Рецепт сахарного печенья");
        post2.setDescription("Инградиенты...");
        post2.setParticipates(Set.of());
        post2.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-11"));
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.deleteById(post1.getId());
        assertThat(postRepository.existsById(post1.getId())).isFalse();
        assertThat(postRepository.existsById(post2.getId())).isTrue();
        postRepository.deleteById(post2.getId());
        assertThat(postRepository.existsById(post2.getId())).isFalse();
    }

    @Test
    public void whenUpdateNameAndDescriptionOk() throws ParseException {
        var post1 = new Post();
        post1.setName("Продажа книг");
        post1.setDescription("Продаются книги...");
        post1.setParticipates(Set.of());
        post1.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-11"));
        postRepository.save(post1);
        String newName = "Продажа книг НОВАЯ";
        String newDescription = "Продаются книги... новая распродажа";
        int rowCountUpdated = postRepository.updatePostByNameAndDescription(post1.getId(),
                newName,
                newDescription);

        assertThat(rowCountUpdated).isEqualTo(1);
        assertThat(postRepository.findById(post1.getId()).get().getName()).isEqualTo(newName);
        assertThat(postRepository.findById(post1.getId()).get().getDescription()).isEqualTo(newDescription);
    }

    @Test
    public void whenDeletePostOk() throws ParseException {
        var post1 = new Post();
        post1.setName("Продажа книг");
        post1.setDescription("Продаются книги...");
        post1.setParticipates(Set.of());
        post1.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-11"));
        postRepository.save(post1);
        var post2 = new Post();
        post2.setName("Рецепт сахарного печенья");
        post2.setDescription("Инградиенты...");
        post2.setParticipates(Set.of());
        post2.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-11"));
        postRepository.save(post2);
        int rowCountDeleted = postRepository.delete(post1.getId());

        assertThat(rowCountDeleted).isEqualTo(1);

        assertThat(postRepository.count()).isEqualTo(1);

        assertThat(postRepository.findById(post2.getId()).get())
                .usingRecursiveComparison().comparingOnlyFields("name", "description")
                .isEqualTo(post2);

        assertThat(postRepository.findById(post1.getId()))
                .isEmpty();
    }
}