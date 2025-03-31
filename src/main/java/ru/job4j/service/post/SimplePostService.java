package ru.job4j.service.post;

import org.springframework.stereotype.Service;
import ru.job4j.dto.PhotoDto;
import ru.job4j.dto.PostDto;
import ru.job4j.mapper.PostMapper;
import ru.job4j.model.Photo;
import ru.job4j.model.Post;
import ru.job4j.repository.PostRepository;
import ru.job4j.service.photo.PhotoService;

import java.util.*;

@Service
public class SimplePostService implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final PhotoService photoService;

    public SimplePostService(PostRepository postRepository, PostMapper postMapper, PhotoService photoService) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.photoService = photoService;
    }

    @Override
    public PostDto create(PostDto postDto, List<PhotoDto> images) {
        Post post = postMapper.getEntityFromModelCustom(postDto);
        saveNewFile(post, images);
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
    public Post update(PostDto postDto) {
        return postRepository.save(postMapper.getEntityFromModelCustom(postDto));
    }

    @Override
    public int delete(long id) {
        return postRepository.deletePostById(id);
    }

    @Override
    public Collection<PostDto> findAll() {
        List<PostDto> result = new ArrayList<>();
        if (postRepository.findAll() != null) {
            result = postRepository.findAll().stream()
                    .map(e -> postMapper.getModelFromEntityCustom(e))
                    .map(e -> new PostDto(e.getId(),
                            e.getDescription(),
                            e.getCreated(),
                            e.isDone(),
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
}