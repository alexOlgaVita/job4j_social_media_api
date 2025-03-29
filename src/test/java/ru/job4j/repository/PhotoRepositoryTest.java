package ru.job4j.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.model.Photo;
import ru.job4j.model.Post;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/*@SpringBootTest(classes = Job4jSocialMediaApiApplication.class)*/
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PhotoRepositoryTest {
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    public void setUp() {
        photoRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    public void whenSavePhotoThenFindById() {
        var photo = new Photo();
        photo.setName("pic1.jpg");
        photo.setPath("path1");
        photoRepository.save(photo);
        var foundPost = photoRepository.findById(photo.getId());
        assertThat(foundPost).isPresent();
        assertThat(foundPost.get().getName()).isEqualTo("pic1.jpg");
    }

    @Test
    public void whenSomePhotosThenFindAllReturnAllPhotos() {
        var photo1 = new Photo();
        photo1.setName("pic1.jpg");
        photo1.setPath("path1");
        var photo2 = new Photo();
        photo2.setName("pic2.jpg");
        photo2.setPath("path2");
        photoRepository.save(photo1);
        photoRepository.save(photo2);
        var photos = photoRepository.findAll();
        assertThat(photos).hasSize(2);
        assertThat(photos).extracting(Photo::getName).contains("pic1.jpg", "pic1.jpg");
    }

    @Test
    public void whenNotPhotosThenFindAllReturnNull() {
        var photos = photoRepository.findAll();
        assertThat(photos).hasSize(0);
    }

    @Test
    public void whenFindByIdThenReturnOne() {
        var photo1 = new Photo();
        photo1.setName("pic1.jpg");
        photo1.setPath("path1");
        var photo2 = new Photo();
        photo2.setName("pic2.jpg");
        photo2.setPath("path2");
        photoRepository.save(photo1);
        photoRepository.save(photo2);
        assertThat(photoRepository.findById(photo1.getId()).get())
                .usingRecursiveComparison().ignoringFields("id", "posts")
                .isEqualTo(photo1);
    }

    @Test
    public void whenNotPhotoIdFindByIdThenReturnNull() {
        assertThat(photoRepository.findById(1L)).isEmpty();
    }

    @Test
    public void when2PhotosThenCountReturn2() {
        var photo1 = new Photo();
        photo1.setName("pic1.jpg");
        photo1.setPath("path1");
        var photo2 = new Photo();
        photo2.setName("pic2.jpg");
        photo2.setPath("path2");
        photoRepository.save(photo1);
        photoRepository.save(photo2);
        assertThat(photoRepository.count()).isEqualTo(2);
    }

    @Test
    public void whenSaveAllPhotosThenFindAllReturnAllPosts() {
        var photo1 = new Photo();
        photo1.setName("pic1.jpg");
        photo1.setPath("path1");
        var photo2 = new Photo();
        photo2.setName("pic2.jpg");
        photo2.setPath("path2");
        photoRepository.saveAll(List.of(photo1, photo2));
        var photos = photoRepository.findAll();
        assertThat(photos).hasSize(2);
        assertThat(photos).extracting(Photo::getName).contains("pic1.jpg", "pic2.jpg");
    }

    @Test
    public void whenSavePhotosThenFindAllReturnTrue() {
        var photo1 = new Photo();
        photo1.setName("pic1.jpg");
        photo1.setPath("path1");
        var photo2 = new Photo();
        photo2.setName("pic2.jpg");
        photo2.setPath("path2");
        photoRepository.save(photo1);
        photoRepository.save(photo2);
        assertThat(photoRepository.existsById(photo1.getId())).isTrue();
        assertThat(photoRepository.existsById(photo2.getId())).isTrue();
    }

    @Test
    public void whenIsPhotoThenDeleteByIdOk() {
        var photo1 = new Photo();
        photo1.setName("pic1.jpg");
        photo1.setPath("path1");
        var photo2 = new Photo();
        photo2.setName("pic2.jpg");
        photo2.setPath("path2");
        photoRepository.save(photo1);
        photoRepository.save(photo2);
        photoRepository.deleteById(photo1.getId());
        assertThat(photoRepository.existsById(photo1.getId())).isFalse();
        assertThat(photoRepository.existsById(photo2.getId())).isTrue();
        photoRepository.deleteById(photo2.getId());
        assertThat(photoRepository.existsById(photo2.getId())).isFalse();
    }

    @Test
    public void whenDeletePhotoOk() throws ParseException {
        Set<Photo> savedPhotos = new HashSet<>();

        var photo1 = new Photo();
        photo1.setName("pic1.jpg");
        photo1.setPath("path1");
        savedPhotos.add(photoRepository.save(photo1));

        var photo2 = new Photo();
        photo2.setName("pic2.jpg");
        photo2.setPath("path2");
        savedPhotos.add(photoRepository.save(photo2));

        var post1 = new Post();
        post1.setName("Продажа книг");
        post1.setDescription("Продаются книги...");
        post1.setParticipates(Set.of());
        post1.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-11"));
        post1.setPhotos(savedPhotos);
        postRepository.save(post1);

        assertThat(postRepository.findAllPhotosByPostId(post1.getId())).isEmpty();
        var deletedCount = photoRepository.deletePhotoByPhotoId(photo1.getId());
        assertThat(deletedCount).isEqualTo(1);
        assertThat(photoRepository.existsById(photo1.getId())).isFalse();
        assertThat(photoRepository.existsById(photo2.getId())).isTrue();
    }
}