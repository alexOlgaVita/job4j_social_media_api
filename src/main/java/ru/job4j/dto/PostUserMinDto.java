package ru.job4j.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.job4j.model.Photo;
import ru.job4j.model.Post;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
public class PostUserMinDto {

    private Long userId;
    private String userName;
    long id;
    String description;
    Date created;
    private Set<Photo> photos;

    public PostUserMinDto(long userId,
                          String userName,
                          Post post) {
        this.userId = userId;
        this.userName = userName;
        this.description = post.getDescription();
        this.created = post.getCreated();
        this.photos = post.getPhotos();
        this.id = post.getId();
    }
}