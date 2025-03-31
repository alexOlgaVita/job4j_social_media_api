package ru.job4j.service.post;

import ru.job4j.dto.PhotoDto;
import ru.job4j.dto.PostDto;
import ru.job4j.model.Post;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PostService {

    PostDto create(PostDto post, List<PhotoDto> images);

    PostDto save(PostDto post);

    boolean update(Post post);

    int delete(long id);

    Collection<PostDto> findAll();

    Optional<PostDto> findById(long id);

    boolean deleteById(Long id);
}