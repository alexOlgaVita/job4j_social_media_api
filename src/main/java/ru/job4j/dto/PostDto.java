package ru.job4j.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.job4j.model.Photo;
import ru.job4j.model.User;

import java.time.LocalDateTime;
import java.util.Set;

import static java.time.LocalDateTime.now;

@Data
@NoArgsConstructor
public class PostDto {

    private long id;
    private String description;
    private boolean done = false;
    private LocalDateTime created = now();
    private User user;
    private String zonedDateTime;
    private Set<Photo> photos;

    public PostDto(long id,
                   String description,
                   LocalDateTime created,
                   boolean done,
                   User user,
                   String zonedDateTime,
                   Set<Photo> photos) {
        this.id = id;
        this.description = description;
        this.created = created;
        this.done = done;
        this.user = user;
        this.zonedDateTime = zonedDateTime;
        this.photos = photos;
    }
}
