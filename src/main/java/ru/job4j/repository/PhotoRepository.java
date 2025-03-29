package ru.job4j.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.model.Photo;

import java.util.List;

public interface PhotoRepository extends CrudRepository<Photo, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Photo p where p.id = :id")
    int deletePhotoByPhotoId(@Param("id") Long id);

    @Query("from Photo")
    List<Photo> findAll();
}
