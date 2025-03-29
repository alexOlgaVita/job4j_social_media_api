package ru.job4j.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "photos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String name;
    private String path;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "posts_photos", joinColumns = {
            @JoinColumn(name = "photo_id", nullable = false, updatable = false, insertable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "post_id", nullable = false, updatable = false, insertable = false)})
    private Set<Photo> posts = new HashSet<>();

    public Photo(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Photo{"
                + "id=" + id
                + ", name='" + name + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Photo photo = (Photo) obj;
        return (id == photo.id);
    }
}