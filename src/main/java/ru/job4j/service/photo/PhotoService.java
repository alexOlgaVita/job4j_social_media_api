package ru.job4j.service.photo;

import ru.job4j.dto.PhotoDto;
import ru.job4j.model.Photo;

import java.util.Optional;

public interface PhotoService {

    Photo save(PhotoDto photoDto);

    Photo update(PhotoDto photoDto);

    Optional<PhotoDto> getFileById(long id);

    void deleteById(long id);

    Optional<Photo> findById(long id);
}
