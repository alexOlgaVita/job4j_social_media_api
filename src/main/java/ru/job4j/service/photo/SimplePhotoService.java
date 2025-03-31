package ru.job4j.service.photo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.dto.PhotoDto;
import ru.job4j.model.Photo;
import ru.job4j.repository.PhotoRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Service
public class SimplePhotoService implements PhotoService {

    private final PhotoRepository photoRepository;

    private final String storageDirectory;

    public SimplePhotoService(PhotoRepository photoRepository,
                              @Value("${file.directory}") String storageDirectory) {
        this.photoRepository = photoRepository;
        this.storageDirectory = storageDirectory;
        createStorageDirectory(storageDirectory);
    }

    private void createStorageDirectory(String path) {
        try {
            Files.createDirectories(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Photo save(PhotoDto photoDto) {
        var path = getNewFilePath(photoDto.getName());
        writeFileBytes(path, photoDto.getContent());
        return photoRepository.save(new Photo(null, photoDto.getName(), path, null));
    }

    @Override
    public Photo update(PhotoDto photoDto) {
        var path = getNewFilePath(photoDto.getName());
        writeFileBytes(path, photoDto.getContent());
        return photoRepository.save(new Photo(photoDto.getId(), photoDto.getName(), path, null));
    }

    private String getNewFilePath(String sourceName) {
        return storageDirectory + java.io.File.separator + UUID.randomUUID() + sourceName;
    }

    private void writeFileBytes(String path, byte[] content) {
        try {
            Files.write(Path.of(path), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<PhotoDto> getFileById(long id) {
        var fileOptional = photoRepository.findById(id);
        if (fileOptional.isEmpty()) {
            return Optional.empty();
        }
        var content = readFileAsBytes(fileOptional.get().getPath());
        return Optional.of(new PhotoDto(fileOptional.get().getName(), content));
    }

    private byte[] readFileAsBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(long id) {
        var fileOptional = photoRepository.findById(id);
        if (fileOptional.isPresent()) {
            deleteFile(fileOptional.get().getPath());
            photoRepository.deletePhotoByPhotoId(id);
        }
    }

    private void deleteFile(String path) {
        try {
            Files.deleteIfExists(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Photo> findById(long id) {
        return (photoRepository.findById(id).isPresent())
                ? Optional.ofNullable(photoRepository.findById(id).get()) : Optional.empty();
    }
}