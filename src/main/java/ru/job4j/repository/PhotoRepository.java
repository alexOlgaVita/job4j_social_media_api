package ru.job4j.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.model.Photo;

public interface PhotoRepository extends CrudRepository<Photo, Long> {
}
