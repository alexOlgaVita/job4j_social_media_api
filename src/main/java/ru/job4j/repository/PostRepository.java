package ru.job4j.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.model.Post;

public interface PostRepository extends CrudRepository<Post, Long> {
}
