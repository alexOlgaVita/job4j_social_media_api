package ru.job4j.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.job4j.model.Photo;
import ru.job4j.model.User;

import java.time.LocalDateTime;
import java.util.Set;

import static java.time.LocalDateTime.now;

@Data
@NoArgsConstructor
public class PostDto {

    private long id;

    @NotBlank(message = "name не может быть пустым")
    @Length(min = 4,
            max = 20,
            message = "name должно быть не менее 4 и не более 20 символов")
    private String name;

    @NotBlank(message = "description не может быть пустым")
    @Length(min = 15,
            max = 120,
            message = "description должно быть не менее 15 и не более 120 символов")
    private String description;

    private LocalDateTime created = now();
    private User user;
    private String zonedDateTime;
    private Set<Photo> photos;

    public PostDto(long id,
                   String name,
                   String description,
                   LocalDateTime created,
                   User user,
                   String zonedDateTime,
                   Set<Photo> photos) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
        this.user = user;
        this.zonedDateTime = zonedDateTime;
        this.photos = photos;
    }
}
