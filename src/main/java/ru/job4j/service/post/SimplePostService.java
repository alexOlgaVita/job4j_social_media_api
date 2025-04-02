package ru.job4j.service.post;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.job4j.dto.PhotoDto;
import ru.job4j.dto.PostDto;
import ru.job4j.dto.PostUserMinDto;
import ru.job4j.mapper.PostMapper;
import ru.job4j.mapper.PostUserMinMapper;
import ru.job4j.model.Photo;
import ru.job4j.model.Post;
import ru.job4j.repository.PostRepository;
import ru.job4j.service.photo.PhotoService;

import java.util.*;

@Validated
@Service
public class SimplePostService implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final PhotoService photoService;
    private final PostUserMinMapper postUserMinMapper;

    public SimplePostService(PostRepository postRepository, PostMapper postMapper, PhotoService photoService, PostUserMinMapper postUserMinMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.photoService = photoService;
        this.postUserMinMapper = postUserMinMapper;
    }

    @Override
    public PostDto create(PostDto postDto, List<PhotoDto> images) {
        Post post = postMapper.getEntityFromModelCustom(postDto);
        saveNewFile(post, images);
        return postMapper.getModelFromEntityCustom(postRepository.save(post));
    }

    @Override
    public PostDto save(@Valid PostDto postDto) {
        Post post = postMapper.getEntityFromModelCustom(postDto);
        return postMapper.getModelFromEntityCustom(postRepository.save(post));
    }

    private void saveNewFile(Post post, List<PhotoDto> images) {
        Set<Photo> savedPhotos = new HashSet<>();
        List<Photo> savedPhotosList = new ArrayList<>();
        for (PhotoDto image : images) {
            savedPhotos.add(photoService.save(image));
            savedPhotosList.add(photoService.save(image));
        }
        post.setPhotos(savedPhotos);
        /*       post.setPhotos(savedPhotosList); */
    }

    @Override
    public boolean update(Post post) {
        return postRepository.update(post) > 0L;
    }

    @Override
    public int delete(long id) {
        return postRepository.delete(id);
    }

    @Override
    public Collection<PostDto> findAll() {
        List<PostDto> result = new ArrayList<>();
        if (postRepository.findAll() != null) {
            result = postRepository.findAll().stream()
                    .map(e -> postMapper.getModelFromEntityCustom(e))
                    .map(e -> new PostDto(e.getId(),
                            e.getName(),
                            e.getDescription(),
                            e.getCreated(),
                            e.getUser(),
                            null,
                            e.getPhotos()
                    ))
                    .toList();
        }
        return result;
    }

    @Override
    public Optional<PostDto> findById(long id) {
        return (postRepository.findById(id).isPresent())
                ? Optional.ofNullable(postMapper.getModelFromEntityCustom(postRepository.findById(id).get())) : Optional.empty();
    }

    @Override
    public boolean deleteById(Long id) {
        return postRepository.delete(id) > 0L;
    }

    @Override
    public Collection<PostUserMinDto> findPostsByUserIds(List<Long> userIds) {
        return (!postRepository.findPostsByUserIds(userIds).isEmpty())
                ? (postRepository.findPostsByUserIds(userIds)
                .stream().map(e -> postUserMinMapper.getModelFromEntity(e)).toList()) : List.of();
    }
}