package ru.job4j.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.model.Post;
import ru.job4j.model.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PostJpaRepository extends JpaRepository<Post, Long> {

  List<Post> findByUser(User user);

  List<Post> findByCreatedBetween(Date start, Date end);

  List<Post> findByOrderByCreatedDesc();

  List<Post> findByOrderByCreatedAsc();

  List<Post> findAll();

  Optional<Post> findById(long id);

  List<Post> findByNameOrDescription(String name, String description);
  
  List<Post> findByNameLike(String name);
  
  List<Post> findByNameStartingWith(String name);
  
  List<Post> findByNameEndingWith(String name);
  
  List<Post> findByNameContaining(String name);
  
  List<Post> findByNameContainingIgnoreCase(String name);
  
  List<Post> findByNameContainingOrDescriptionContaining(String name, String description);
  
  List<Post> findByCreatedGreaterThanEqual(Date date);
  
  List<Post> findByCreatedAfter(Date date);

  List<Post> findByNameContaining(String name, Sort sort);
  
  Page<Post> findAll(Pageable pageable);
  
  Page<Post> findByNameContaining(String name, Pageable pageable);

  @Transactional
  void deleteAllByCreatedBefore(Date date);

  List<Post> findByName(String name);

}