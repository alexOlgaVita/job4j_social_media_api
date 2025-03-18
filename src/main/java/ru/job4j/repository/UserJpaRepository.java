package ru.job4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.model.User;

public interface UserJpaRepository extends JpaRepository<User, Long> {

}