package ru.job4j.mapper;

import org.mapstruct.Mapper;
import ru.job4j.dto.PostDto;
import ru.job4j.model.Post;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface PostMapper {

    default PostDto getModelFromEntityCustom(Post post) {
        PostDto postDto = new PostDto();
        if (post != null) {
            postDto.setId(post.getId() == null ? 0 : post.getId());
            postDto.setName(post.getName());
            postDto.setDescription(post.getDescription());
            postDto.setCreated(toLocalDateTime(post.getCreated()));
            postDto.setUser(post.getUser());
            postDto.setPhotos(post.getPhotos());
        } else {
            postDto = null;
        }
        return postDto;
    }

    default Post getEntityFromModelCustom(PostDto postDto) {
        Post post = new Post();
        if (postDto != null) {
            post.setId(postDto.getId() == 0 ? null : postDto.getId());
            post.setName(postDto.getName());
            post.setDescription(postDto.getDescription());
            post.setCreated(toDate(postDto.getCreated()));
            post.setUser(postDto.getUser());
            post.setPhotos(postDto.getPhotos());
        } else {
            post = null;
        }
        return post;
    }

    private Date toDate(LocalDateTime localDateTime) {
        var instant = Timestamp.valueOf(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).toInstant();
        var date = Date.from(instant);
        return date;
    }

    private LocalDateTime toLocalDateTime(Date localDateTime) {
        return Instant.ofEpochMilli(localDateTime.getTime()).atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}